package github.vege19.clubmarvel.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.clubmarvel.BuildConfig
import github.vege19.clubmarvel.api.ApiInterface
import github.vege19.clubmarvel.models.ComicModel
import github.vege19.clubmarvel.utils.Const
import github.vege19.clubmarvel.utils.makeRetrofitCallback
import retrofit2.await
import javax.inject.Inject
import javax.inject.Named

class ComicsFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    private var comics = MutableLiveData<MutableList<ComicModel>>()

    fun getComics(): LiveData<MutableList<ComicModel>> {
        return  comics
    }


    fun generateComics(offset: Int) {
        makeRetrofitCallback {
            try {
                val response = apiInterface.getComics(BuildConfig.MarvelPublicApiKey,
                    BuildConfig.MarvelHash,
                    1,
                    15,
                    offset,
                    "the amazing").await()
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

}