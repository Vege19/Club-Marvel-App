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
import androidx.navigation.fragment.findNavController
import github.vege19.clubmarvel.App

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.utils.configureActionbar
import github.vege19.clubmarvel.viewmodels.ComicsFragmentViewModel
import github.vege19.clubmarvel.viewmodels.DashboardFragmentViewModel
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_comics.*
import javax.inject.Inject

class ComicsFragment : Fragment() {

    //Injecting view model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ComicsFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startFlow()
        configureActionBar()
        loadComics()

    }

    private fun loadComics() {
        viewModel.getComics().observe(this, Observer {
            if (it.isNotEmpty()) {
                for (i in it) {
                    Log.d("TAG", i.title)
                }
            } else {
                Log.d("TAG", "Empty")
            }
        })

        viewModel.generateComics()
    }

    private fun startFlow() {
        App.getComponent().inject(this)
    }

    private fun configureActionBar() {
        activity?.configureActionbar(requireContext(),
                _actionbar_comics,
                getString(R.string.comics_title_actionbar),
                true,
                fun(actionbar) {
                    actionbar._fragment_tb.setNavigationOnClickListener {
                        findNavController().popBackStack()
                    }
                })
    }

}
