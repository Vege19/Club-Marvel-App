package github.vege19.clubmarvel.viewmodels

import androidx.lifecycle.ViewModel
import github.vege19.clubmarvel.App
import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.models.CreatorModel
import github.vege19.clubmarvel.models.CreatorsResponseModel
import github.vege19.clubmarvel.utils.Const
import javax.inject.Inject

class ComicDetailFragmentViewModel @Inject constructor(): ViewModel() {

    fun getWriters(creators: CreatorsResponseModel): String {
        val writers: MutableList<CreatorModel> = mutableListOf()
        var writersString = ""
        val sb = StringBuilder()

        for (item in creators.items) {
            if (item.role == Const.ROLE_WRITER) {
                writers.add(item)
            }
        }

        if (writers.isEmpty()) {
            return App.getContext().getString(R.string.null_case_writers)
        } else {
            for (writer in writers) {
                if (writers.size <= 1) {
                    sb.append(writer.name)
                    writersString = sb.toString()
                } else {
                    sb.append(writer.name).append(", ")
                    writersString = sb.toString().substring(0, sb.length - 2)
                }
            }
        }

        return writersString

    }

    fun getCoverArtist(creators: CreatorsResponseModel): String {
        var coverArtist = ""

        for (item in creators.items) {
            if (item.role == Const.ROLE_COVER_ARTIST) {
                coverArtist = item.name
            }
        }

        return if (coverArtist.isNotEmpty()) {
            coverArtist
        } else {
            App.getContext().getString(R.string.null_case_cover_artist)
        }

    }

}