package com.example.wardrobeapp.view.adapter

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.wardrobeapp.R
import com.example.wardrobeapp.model.Shirt
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class ShirtAdapter(private val context: Context) :
    RecyclerView.Adapter<ShirtAdapter.ShirtViewHolder>() {
    private var listofshirts = ArrayList<Shirt>()

    class ShirtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shirtImage = itemView.findViewById<ImageView>(R.id.shirt_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shirt, parent, false)
        return ShirtViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofshirts.size
    }

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        val postionoflist = listofshirts[position]
        val shirturi = Uri.fromFile(File(postionoflist.shirt_img))
        Log.i("akshmehta676", shirturi.path.toString())
        Glide.with(context).load(shirturi).centerCrop().into(holder.shirtImage)

    }

    fun updateCustomerList(newshirtList: kotlin.collections.ArrayList<Shirt>) {
        listofshirts.clear()
        listofshirts.addAll(newshirtList)
        notifyDataSetChanged()
    }


    private val differCallback = object : DiffUtil.ItemCallback<Shirt>() {
        override fun areItemsTheSame(oldItem: Shirt, newItem: Shirt): Boolean {
            return oldItem.shirt_id == newItem.shirt_id
        }

        override fun areContentsTheSame(oldItem: Shirt, newItem: Shirt): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

}