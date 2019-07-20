package github.vege19.clubmarvel.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.clubmarvel.api.ApiInterface
import github.vege19.clubmarvel.models.DashboardOptionModel
import github.vege19.clubmarvel.utils.makeRetrofitCallback
import retrofit2.await
import javax.inject.Inject

class DashboardFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    private val menuOptions = MutableLiveData<List<DashboardOptionModel>>()
    fun getMenuOptions() : LiveData<List<DashboardOptionModel>> {
        return menuOptions
    }

    fun generateMenuOptions() {
        makeRetrofitCallback {
            try {
                menuOptions.postValue(apiInterface.getMenuOptions().await())
            } catch (e: Throwable) {
                Log.d("TAG", e.message)
            }
        }
    }

}