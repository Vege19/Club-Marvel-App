package github.vege19.clubmarvel.api

import github.vege19.clubmarvel.models.DashboardOptionModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    //: Declare retrofit callbacks here ://

    @GET("menu_options.json")
    fun getMenuOptions() : Call<List<DashboardOptionModel>>

}