package com.param.tinynewsapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.param.tinynewsapp.ItemDetailActivity
import com.param.tinynewsapp.ItemDetailFragment
import com.param.tinynewsapp.ItemListActivity
import com.param.tinynewsapp.R
import com.param.tinynewsapp.dummy.DummyContent
import com.param.tinynewsapp.model.NewsModelRecycler
import com.squareup.picasso.Picasso

import java.util.ArrayList


class NewsAdapter(ctx: Context,
                  private val parentActivity: ItemListActivity,
                  private val twoPane: Boolean = false ,
                  private val dataModelArrayList: ArrayList<NewsModelRecycler.NewsModel>) :


RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private val onClickListener: View.OnClickListener

    init {
        inflater = LayoutInflater.from(ctx)
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsModelRecycler.NewsModel

            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.name)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.name)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.news_item_view_big, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.MyViewHolder, position: Int) {
        val item = dataModelArrayList[position]
        Picasso.get().load(dataModelArrayList[position].imgURL).into(holder.iv)
        holder.name.text = dataModelArrayList[position].name
        holder.country.text = dataModelArrayList[position].country
        holder.city.text = dataModelArrayList[position].city

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }


   inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var country: TextView
        var name: TextView
        var city: TextView
        var iv: ImageView

        init {
            country = itemView.findViewById<View>(R.id.country) as TextView
            name = itemView.findViewById<View>(R.id.name) as TextView
            city = itemView.findViewById<View>(R.id.city) as TextView
            iv = itemView.findViewById<View>(R.id.iv) as ImageView
        }
    }
}