package com.hilt.crazyprogramming.userList

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hilt.crazyprogramming.R
import com.hilt.crazyprogramming.roomDB.model.LoginUser
import kotlinx.android.synthetic.main.user_list_row.view.*
import java.io.ByteArrayOutputStream

class UserListAdapter(
    private var context: Context,
    private var userList: ArrayList<LoginUser>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_row, parent, false)
        return UserListViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        var user = userList[position]
        holder.itemView.tvUserName.text = "${user.userName}"
        holder.itemView.tvPassword.text = "${user.password}"

        val byteArrayImage = user.userPic
        val stream = ByteArrayOutputStream()
        val bitmapImage = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage!!.size)
        val image = holder.itemView.findViewById<ImageView> (R.id.imgUser)
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 10, stream)
        image.setImageBitmap(
            Bitmap.createScaledBitmap(
                bitmapImage,
                bitmapImage.width,
                bitmapImage.height,
                true
            )
        )

        holder.itemView.tvImageSize.text = "Size - ${bitmapImage.allocationByteCount}"
        Log.d("adapter", "${bitmapImage.allocationByteCount}")

        /* Glide.with(context)
            .asBitmap()
            .load(user.userPic)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                   holder.itemView.imgUser.setImageBitmap(user.userPic)
                }
            }) */

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(user)
        }

        /*holder.itemView.btnDelete.setOnClickListener {
            onItemClickListener.onItemClick(user)
        }*/
    }

    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(user: LoginUser?)
    }
}