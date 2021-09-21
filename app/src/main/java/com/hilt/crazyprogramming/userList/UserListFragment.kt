package com.hilt.crazyprogramming.userList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.roomDB.model.LoginUser
import com.hilt.crazyprogramming.roomDB.vm.LoginViewModel
import com.hilt.crazyprogramming.utlis.showErrorToast
import kotlinx.android.synthetic.main.fragment_userlist.*

class UserListFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.getUserListsVM(requireContext())?.observe(viewLifecycleOwner, Observer { userList ->
            if (userList != null) {
                userListAdapter = UserListAdapter(requireContext(), userList as ArrayList<LoginUser>, object : UserListAdapter.OnItemClickListener {
                    override fun onItemClick(user: LoginUser?) {
                        showErrorToast(requireContext(), "${user!!.userName}")
                        Log.d("userData", userList.toString())
                    }
                })
                userRecycler.layoutManager = LinearLayoutManager(requireContext())
                userRecycler.adapter = userListAdapter
            } else {
                // No data found
            }
        })
    }
}