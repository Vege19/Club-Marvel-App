package github.vege19.clubmarvel.controllers


import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import github.vege19.clubmarvel.App

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.utils.GenericAdapter
import github.vege19.clubmarvel.utils.configureActionbar
import github.vege19.clubmarvel.utils.navigateTo
import github.vege19.clubmarvel.utils.setGlideImage
import github.vege19.clubmarvel.viewmodels.DashboardFragmentViewModel
import github.vege19.clubmarvel.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.item_menu_option.view.*
import javax.inject.Inject

class DashboardFragment : Fragment() {

    //Injecting view model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[DashboardFragmentViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startFlow()
        activity?.configureActionbar(requireContext(),
                _actionbar_dashboard,
                getString(R.string.dash_title_actionbar),
                false, fun (_) {})
        loadDashboardOptions()
    }

    private fun startFlow() {
        (context?.applicationContext as App).getComponent().inject(this)
    }

    private fun loadDashboardOptions() {
        viewModel.getMenuOptions().observe(this, Observer {
            if (it.isNotEmpty()) {
                _menu_dashboard_rv.layoutManager = GridLayoutManager(activity, 1)
                _menu_dashboard_rv.adapter = GenericAdapter(R.layout.item_menu_option, it,
                        fun(viewHolder, view, option, _) {
                            if (option.id % 2 == 0) {
                                view._name_dashboard_txt.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                            }
                            view._name_dashboard_txt.text = option.name
                            view._image_dashboard_iv.setGlideImage(option.imageUrl, requireContext(), true, null, null)
                            viewHolder.itemView.setOnClickListener {
                                activity?.navigateTo(requireView(), R.id.action_dashboardFragment_to_comicsFragment, null)
                            }
                        })

            } else {
                Log.d("TAG", "Empty list.")

            }
        })

        viewModel.generateMenuOptions()
    }

}
