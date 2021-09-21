package com.hilt.crazyprogramming.userList

import android.R.attr
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.roomDB.model.LoginUser
import kotlinx.android.synthetic.main.user_list_row.view.*
import android.graphics.Bitmap

import com.bumptech.glide.request.target.SimpleTarget

import android.R.attr.data
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.transition.Transition


class UserListAdapter (private var context: Context, private var userList: ArrayList<LoginUser>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_row, parent, false)
        return UserListViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        var user = userList[position]
        holder.itemView.tvUserName.text = "${user.userName}"
        holder.itemView.tvPassword.text = "${user.password}"

        Glide.with(context)
            .asBitmap()
            .load(user.userPic)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                   holder.itemView.imgUser.setImageBitmap(user.userPic)
                }
            })

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(user)
        }

        /*holder.itemView.btnDelete.setOnClickListener {
            onItemClickListener.onItemClick(user)
        }*/
    }
    class UserListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(user: LoginUser?)
    }
}