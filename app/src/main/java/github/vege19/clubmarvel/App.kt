package github.vege19.clubmarvel

import android.app.Application
import android.content.Context
import github.vege19.clubmarvel.dagger.AppComponent
import github.vege19.clubmarvel.dagger.DaggerAppComponent

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        private lateinit var appContext: App

        fun getComponent() = appComponent // To return app component
        fun getContext() = appContext // To return app component
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
        appContext = this

    }

}