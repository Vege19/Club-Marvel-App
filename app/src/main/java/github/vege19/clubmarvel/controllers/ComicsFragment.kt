package github.vege19.clubmarvel.controllers

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.vege19.clubmarvel.App

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.models.ComicModel
import github.vege19.clubmarvel.utils.*
import github.vege19.clubmarvel.viewmodels.ComicsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.android.synthetic.main.item_comic.view.*
import javax.inject.Inject

class ComicsFragment : Fragment() {

    //Injecting view model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ComicsFragmentViewModel::class.java]
    }

    private lateinit var layoutManager: GridLayoutManager
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItemCount = 0
    private var offset = 0
    private var load = true
    private val bundle = Bundle()
    private var mainView: View? = null

    //Rv
    private var comicsList: MutableList<ComicModel> = mutableListOf()
    private lateinit var comicAdapter: GenericAdapter<ComicModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_comics, container, false)

        }

        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startFlow()
        configureActionBar()
        layoutManager = GridLayoutManager(requireContext(), 1)
        _comics_rv.layoutManager = layoutManager
        if (load) {
            showSkeleton(_comics_rv, getComicsAdapter(viewModel.getEmptyItems()), R.layout.item_comic)
        }

        if (isDeviceOnline()) { //Load comics only if there is internet connection
            loadComics()
        } else {
            activity?.showAlertDialog(
                requireContext(),
                getString(R.string.dialog_offline_title),
                getString(R.string.dialog_offline_message)
            )
        }


        configureSearchView()

    }

    private fun startFlow() {
        App.getComponent().inject(this)
    }

    private fun configureActionBar() {
        activity?.configureActionbar(requireContext(),
            _actionbar_comics,
            getString(R.string.comics_title_actionbar),
            true,
            fun(actionbar) {
                actionbar._fragment_tb.setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            })
    }

    private fun loadComics() {
        viewModel.getComics().observe(this, Observer {
            if (it.isNotEmpty()) {
                if (isDeviceOnline()) {
                    comicsList.addAll(it)
                }
                if (load) { //Set adapter just the first time
                    _comics_rv.adapter = getComicsAdapter(comicsList)
                    getOnScrollListener(_comics_rv)
                    load = false
                }
                comicAdapter.notifyDataSetChanged()
                _comic_progress_bar.visibility = View.INVISIBLE //Hide progressbar
            } else {
                Log.d("TAG", "Empty")
            }
        })

        if (isDeviceOnline()) {
            viewModel.generateComics(offset)
        } else {
            activity?.showAlertDialog(
                requireContext(),
                getString(R.string.dialog_offline_title),
                getString(R.string.dialog_offline_message)
            )

        }

    }

    private fun getComicsAdapter(list: MutableList<ComicModel>): GenericAdapter<ComicModel> {
        comicAdapter = GenericAdapter(R.layout.item_comic, list, fun(viewHolder, view, comic, _) {

            val imageUrl = "${comic.thumbnail?.path}.${comic.thumbnail?.extension}"
            if (comic.thumbnail?.path == Const.NOT_AVAILABLE_IMAGE_URL) {
                view._cover_comics_iv.setGlideImage(
                    Const.NOT_AVAILABLE_IMAGE_URL_REPLACE,
                    requireContext(), false, 210, 324, false
                )
            } else {
                view._cover_comics_iv.setGlideImage(imageUrl, requireContext(), false, 210, 324, false)
            }
            view._title_comic_txt.text = comic.title
            if (comic.description.isNullOrEmpty()) {
                view._overview_comic_txt.text = "This comic hasn't overview."
            } else {
                view._overview_comic_txt.text = comic.description
            }
            view._writers_comic_txt.text = viewModel.getWriters(comic.creators!!)
            val i = comic.dates[0].date.indexOf("T")
            view._date_comic_txt.text = "Published date: ${comic.dates[0].date.substring(0, i)}"

            viewHolder.itemView.setOnClickListener {
                bundle.putSerializable(Const.COMIC_KEY, comic) //Saving comic in a bundle
                activity?.navigateTo(it, R.id.action_comicsFragment_to_comicDetailFragment, bundle)
            }


        })

        return comicAdapter
    }

    private fun getOnScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItemCount = layoutManager.findFirstVisibleItemPosition()

                    if (Const.isLoading) {
                        if ((visibleItemCount + pastVisibleItemCount) >= totalItemCount) {
                            _comic_progress_bar.visibility = View.VISIBLE //Show progressbar
                            Log.i("TAG", "End...")
                            Const.isLoading = false
                            offset += 15
                            if (isDeviceOnline()) {
                                viewModel.generateComics(offset) // Generate new comics
                            } else {
                                activity?.showAlertDialog(
                                    requireContext(),
                                    getString(R.string.dialog_offline_title),
                                    getString(R.string.dialog_offline_message)
                                )
                            }
                            comicAdapter.notifyDataSetChanged() // Update adapter
                        }
                    }
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                Log.d("TAG", "Current position: $newState")
            }
        })
    }

    private fun configureSearchView() {
        val searchTxt = _search_comics_sv.findViewById(androidx.appcompat.R.id.search_src_text) as TextView
        searchTxt.typeface = Typeface.createFromAsset(activity?.assets, "roboto_black.ttf")
        searchTxt.setTextColor(Color.WHITE)
    }

}
