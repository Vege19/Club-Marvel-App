package github.vege19.clubmarvel

import android.app.Application
import github.vege19.clubmarvel.dagger.AppComponent
import github.vege19.clubmarvel.dagger.DaggerAppComponent

class App: Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

       appComponent = DaggerAppComponent.builder().build()

    }

    fun getComponent() = appComponent // To return app component

}