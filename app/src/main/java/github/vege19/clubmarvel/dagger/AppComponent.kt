package github.vege19.clubmarvel.dagger

import dagger.Component
import github.vege19.clubmarvel.controllers.DashboardFragment
import github.vege19.clubmarvel.controllers.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    ViewModelModule::class,
    RetrofitModule::class])
interface AppComponent {

    //: Declare injections ://

    fun inject(mainActivity: MainActivity)
    fun inject(dashboardFragment: DashboardFragment)

}