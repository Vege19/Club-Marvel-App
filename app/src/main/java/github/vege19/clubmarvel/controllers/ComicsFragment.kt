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
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.vege19.clubmarvel.App

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.models.ComicModel
import github.vege19.clubmarvel.utils.Const
import github.vege19.clubmarvel.utils.GenericAdapter
import github.vege19.clubmarvel.utils.configureActionbar
import github.vege19.clubmarvel.utils.setGlideImage
import github.vege19.clubmarvel.viewmodels.ComicsFragmentViewModel
import github.vege19.clubmarvel.viewmodels.DashboardFragmentViewModel
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.android.synthetic.main.fragment_searchbar.view.*
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
    private var comicsList: MutableList<ComicModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startFlow()
        configureActionBar()
        layoutManager = GridLayoutManager(requireContext(), 1)
        loadComics()
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
                _comics_rv.layoutManager = layoutManager
                comicsList.addAll(it)
                Log.d("TAG", "Added new 15 items.")
                _comics_rv.adapter = getComicsAdapter(comicsList)
                getOnScrollListener(_comics_rv)

            } else {
                Log.d("TAG", "Empty")
            }
        })

        viewModel.generateComics(offset)
    }

    private fun getComicsAdapter(list: MutableList<ComicModel>): GenericAdapter<ComicModel> {
        val adapter = GenericAdapter(R.layout.item_comic, list, fun(_, view, comic, _) {
            val imageUrl = "${comic.thumbnail?.path}.${comic.thumbnail?.extension}"
            view._cover_comics_iv.setGlideImage(imageUrl, requireContext(), false, 210, 324)
            view._title_comic_txt.text = comic.title
            view._overview_comic_txt.text = comic.description
            view._writers_comic_txt.text = "Writers: "
            view._date_comic_txt.text = "Published date: ${comic.dates[0].date}"

        })

        adapter.notifyDataSetChanged()

        return adapter
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
                            Log.i("TAG", "End...")
                            Const.isLoading = false
                            offset += 15
                            viewModel.generateComics(offset)

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
