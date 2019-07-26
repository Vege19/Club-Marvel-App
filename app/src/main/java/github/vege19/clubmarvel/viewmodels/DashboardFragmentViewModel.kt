package github.vege19.clubmarvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import github.vege19.clubmarvel.App
import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.api.ApiInterface
import github.vege19.clubmarvel.models.DashboardOptionModel
import github.vege19.clubmarvel.utils.makeRetrofitCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject
import javax.inject.Named

class DashboardFragmentViewModel @Inject constructor(): ViewModel() {

    private val menuOptions = MutableLiveData<MutableList<DashboardOptionModel>>()
    fun getMenuOptions() : LiveData<MutableList<DashboardOptionModel>> {
        return menuOptions
    }

    fun generateMenuOptions() {
        GlobalScope.launch {
            val s = App.getContext().resources.openRawResource(R.raw.menu_options)
                .bufferedReader().use { it.readText() }
            val gSon = Gson()
            val itemType = object : TypeToken<MutableList<DashboardOptionModel>>() {}.type
            val tmpMenuOptions = gSon.fromJson<MutableList<DashboardOptionModel>>(s, itemType)
            menuOptions.postValue(tmpMenuOptions)
        }
    }

}