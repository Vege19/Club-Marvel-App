package github.vege19.clubmarvel.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.clubmarvel.App
import github.vege19.clubmarvel.BuildConfig
import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.api.ApiInterface
import github.vege19.clubmarvel.models.ComicModel
import github.vege19.clubmarvel.models.CreatorModel
import github.vege19.clubmarvel.models.CreatorsResponseModel
import github.vege19.clubmarvel.utils.Const
import github.vege19.clubmarvel.utils.makeRetrofitCallback
import retrofit2.await
import java.lang.StringBuilder
import javax.inject.Inject
import javax.inject.Named

class ComicsFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface) : ViewModel() {

    private var comics = MutableLiveData<MutableList<ComicModel>>()

    fun getComics(): LiveData<MutableList<ComicModel>> {
        return comics
    }


    fun generateComics(offset: Int) {
        makeRetrofitCallback {
            try {
                val response = apiInterface.getComics(
                    BuildConfig.MarvelPublicApiKey,
                    BuildConfig.MarvelHash,
                    1,
                    15,
                    offset,
                    "the amazing"
                ).await()
                Const.isLoading = true
                comics.postValue(response.data?.results)
            } catch (e: Throwable) {
                Const.isLoading = true
                Log.d("TAG", e.message)
            }

        }

    }

    fun getEmptyItems(): MutableList<ComicModel> {
        val list: MutableList<ComicModel> = mutableListOf()
        for (i in 1..15)
            list.add(ComicModel())
        return list
    }

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

        return "Writers: $writersString"

    }

}