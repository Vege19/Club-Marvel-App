package github.vege19.clubmarvel.controllers


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import github.vege19.clubmarvel.R
import github.vege19.clubmarvel.utils.configureActionbar
import kotlinx.android.synthetic.main.fragment_actionbar.view.*
import kotlinx.android.synthetic.main.fragment_comics.*

class ComicsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
