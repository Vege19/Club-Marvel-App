package github.vege19.clubmarvel.dagger

import dagger.Component
import github.vege19.clubmarvel.controllers.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    //: Declare injections ://

    fun inject(mainActivity: MainActivity)

}