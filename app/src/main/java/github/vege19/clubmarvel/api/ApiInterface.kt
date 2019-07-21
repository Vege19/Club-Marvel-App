package github.vege19.clubmarvel.api

import github.vege19.clubmarvel.models.MarvelResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    //: Declare retrofit callbacks here ://

    @GET("comics")
    fun getComics(@Query("apikey") apikey: String,
                  @Query("hash") hash: String,
                  @Query("ts") ts: Int) : Call<MarvelResponseModel>

}