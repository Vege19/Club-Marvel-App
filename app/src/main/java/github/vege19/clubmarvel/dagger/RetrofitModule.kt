package github.vege19.clubmarvel.dagger

import dagger.Module
import dagger.Provides
import github.vege19.clubmarvel.api.ApiInterface
import github.vege19.clubmarvel.utils.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    //: Declare retrofit callbacks here://

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit) : ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}