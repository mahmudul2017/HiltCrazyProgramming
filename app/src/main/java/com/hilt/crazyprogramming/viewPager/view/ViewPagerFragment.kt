package com.hilt.crazyprogramming.viewPager.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.viewPager.adapter.ViewPagerAdapter

class ViewPagerFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_viewpager, container, false)

        setUpTabLayout(view)

        return view
    }

    private fun hideActionBar() {
        // requireActivity().actionBar!!.hide()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    private fun setUpTabLayout(view: View) {
        val adapter = ViewPagerAdapter(requireFragmentManager())
        adapter.addFragment(AndroidFragment(), "Android")
        adapter.addFragment(IosSwiftFragment(), "IOS")

        val tabLayout = view.findViewById<AppBarLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_dollar)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_dollar)
    }
}