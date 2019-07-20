package github.vege19.clubmarvel.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.utils.launchActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            launchActivity<MainActivity>(true)
        }, 2000)

    }

    private fun hideStatusBar() = window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

}
