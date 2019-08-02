package github.vege19.clubmarvel.controllers


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
        _title_comic_detail_txt.text = comic.title

    }


}
