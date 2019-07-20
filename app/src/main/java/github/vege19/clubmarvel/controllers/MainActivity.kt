package github.vege19.clubmarvel.controllers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import github.vege19.clubmarvel.App
import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.item_menu_option.*
import javax.inject.Inject

class MainActivity : FragmentActivity() {

    //Injecting view model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFlow()

        viewModel.getSomething().observe(this, Observer {
            if (it.isNotEmpty()) {
                for (i in it) {
                    Log.d("item", i)
                }
            }
        })

        viewModel.generateThings()

    }

    private fun startFlow() {
        (application as App).getComponent().inject(this)
    }

}
