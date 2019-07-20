package github.vege19.clubmarvel.controllers


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import github.vege19.clubmarvel.R
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureActionbar()

    }

    private fun loadDashboardOptions() {
        //TODO:
    }

    private fun configureActionbar() {
        _actionbar_dashboard._fragment_tb.title = getString(R.string.dash_title_actionbar)
        _actionbar_dashboard._fragment_tb.setTitleTextAppearance(requireContext(),
                R.style.ActionBarTitleAppearance)
    }

}
