package com.hilt.crazyprogramming.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.utlis.showErrorToast
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashBoardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        view.btnLetsGo.setOnClickListener {
            val selectedItem = autoItemName.text.toString()
            Log.d("DashBoardFragment", "$selectedItem")

            when(selectedItem) {
                "Coroutines Fragment" ->
                    Navigation.findNavController(it).navigate(R.id.action_dashBoardFragment_to_coroutinesFragment)

                "UserList Fragment" ->
                    Navigation.findNavController(it).navigate(R.id.action_dashBoardFragment_to_userListFragment)

                "Upload Fragment" ->
                    Navigation.findNavController(it).navigate(R.id.action_dashBoardFragment_to_uploadFragment)

                "Progress Fragment" ->
                    Navigation.findNavController(it).navigate(R.id.action_dashBoardFragment_to_progressFragment)

                "ViewPager Fragment" ->
                    Navigation.findNavController(it).navigate(R.id.action_dashBoardFragment_to_viewPagerFragment)

                else ->
                    showErrorToast(requireContext(), "No Action Needed !")
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val dropDownItem = resources.getStringArray(R.array.dashTopics)
        val dropDownAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_view, dropDownItem)
        requireView().autoItemName.setAdapter(dropDownAdapter)
    }
}