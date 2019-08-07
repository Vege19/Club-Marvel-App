package github.vege19.clubmarvel.controllers


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.models.ComicModel
import github.vege19.clubmarvel.utils.Const
import github.vege19.clubmarvel.utils.setGlideImage
import kotlinx.android.synthetic.main.fragment_comic_detail.*


class ComicDetailFragment : Fragment() {

    private lateinit var comic: ComicModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureActionBar()
        configureTabLayout()
        loadComicContent()

    }

    private fun loadComicContent() {
        //Fetch comic
        comic = arguments?.getSerializable(Const.COMIC_KEY) as ComicModel
        val thumbnailUrl = comic.thumbnail?.path + "." + comic.thumbnail?.extension
        Log.d("TAG", "${comic.id}")

        //Set background and cover image
        _backdrop_comic_detail_iv.setGlideImage(thumbnailUrl, requireContext(), false, 210, 324, true)
        _cover_comic_detail_iv.setGlideImage(thumbnailUrl, requireContext(), false, 210, 324, false)

        //Set comic title
        _title_collapsing_ctl.title = comic.title

        //Set comic summary
        if (comic.description.isNullOrEmpty()) {
            _overview_comic_detail_txt.text = getString(R.string.null_case_overview)
        } else {
            _overview_comic_detail_txt.text = comic.description
        }

        //Set comic's published date
        val i = comic.dates[0].date.indexOf("T")
        _published_date_comic_detail_txt.text = comic.dates[0].date.substring(0, i)

    }

    private fun configureActionBar() {
        _toolbar_comic_detail.navigationIcon = activity?.getDrawable(R.drawable.ic_arrow_back_24dp)
        _toolbar_comic_detail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun configureTabLayout() {
        _tab_layout_comic_detail.addTab(_tab_layout_comic_detail.newTab()
                .setText(getString(R.string.comic_detail_overview_tab)), 0)
        _tab_layout_comic_detail.addTab(_tab_layout_comic_detail.newTab()
                .setText(getString(R.string.comic_detail_characters_tab)), 1)
        _tab_layout_comic_detail.addTab(_tab_layout_comic_detail.newTab()
                .setText(getString(R.string.comic_detail_series_tab)), 2)

    }


}
