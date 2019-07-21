package github.vege19.clubmarvel.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.clubmarvel.BuildConfig
import github.vege19.clubmarvel.api.ApiInterface
import github.vege19.clubmarvel.models.ComicModel
import github.vege19.clubmarvel.utils.makeRetrofitCallback
import retrofit2.await
import javax.inject.Inject
import javax.inject.Named

class ComicsFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    private val comics = MutableLiveData<List<ComicModel>>()
    fun getComics(): LiveData<List<ComicModel>> {
        return  comics
    }

    fun generateComics() {
        makeRetrofitCallback {
            try {
                val response = apiInterface.getComics(BuildConfig.MarvelPublicApiKey,
                    BuildConfig.MarvelHash,
                    1).await()

                comics.postValue(response.data?.results)
            } catch (e: Throwable) {
                Log.d("TAG", e.message)
            }

        }

    }

}