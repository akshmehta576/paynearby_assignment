package com.example.wardrobeapp.view.adapter


import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.wardrobeapp.R
import com.example.wardrobeapp.model.Pant
import com.example.wardrobeapp.model.Shirt
import java.io.File

class PantAdapter(private val context: Context) :
    RecyclerView.Adapter<PantAdapter.PantViewHolder>() {
    private val listofpants = ArrayList<Pant>()

    class PantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pantImage = itemView.findViewById<ImageView>(R.id.pant_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pant, parent, false)
        return PantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofpants.size
    }

    override fun onBindViewHolder(holder: PantViewHolder, position: Int) {
        val postionoflist = listofpants[position]
        val panturi = Uri.fromFile(File(postionoflist.pant_img))
        Glide.with(context).load(panturi).centerCrop().into(holder.pantImage)
    }

    fun updatePantList(newpantList: kotlin.collections.ArrayList<Pant>) {
        listofpants.clear()
        listofpants.addAll(newpantList)
        notifyDataSetChanged()
    }
}

