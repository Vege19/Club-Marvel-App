package github.vege19.clubmarvel.controllers


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import github.vege19.clubmarvel.App

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.viewmodels.DashboardFragmentViewModel
import github.vege19.clubmarvel.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class DashboardFragment : Fragment() {

    //Injecting view model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[DashboardFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureActionbar()

        startFlow()
        loadDashboardOptions()
    }

    private fun startFlow() {
        (context?.applicationContext as App).getComponent().inject(this)
    }

    private fun loadDashboardOptions() {
        viewModel.getMenuOptions().observe(this, Observer {
            if (it.isNotEmpty()) {
                for (i in it)
                    Log.d("TAG", "name: ${i.name}")
            } else {

            }
        })

        viewModel.generateMenuOptions()
    }

    private fun configureActionbar() {
        _actionbar_dashboard._fragment_tb.title = getString(R.string.dash_title_actionbar)
        _actionbar_dashboard._fragment_tb.setTitleTextAppearance(requireContext(),
                R.style.ActionBarTitleAppearance)
    }

}
