package com.param.tinynews.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.param.tinynews.ItemDetailActivity
import com.param.tinynews.ItemDetailFragment
import com.param.tinynews.ItemListActivity
import com.param.tinynews.R
import com.param.tinynews.model.NewsModelRecycler
import com.squareup.picasso.Picasso

import java.util.ArrayList


class NewsAdapter(ctx: Context,
                  private val parentActivity: ItemListActivity,
                  private val twoPane: Boolean = false ,
                  private val dataDataModelArrayList: ArrayList<NewsModelRecycler.NewsDataModel>) :


RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private val onClickListener: View.OnClickListener

    init {
        inflater = LayoutInflater.from(ctx)
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsModelRecycler.NewsDataModel

            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
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
        val item = dataDataModelArrayList[position]
        Picasso.get().load(dataDataModelArrayList[position].imgURL).into(holder.iv)
        holder.title.text = dataDataModelArrayList[position].title
        holder.publishDate.text = dataDataModelArrayList[position].publishedAt
        holder.author.text = dataDataModelArrayList[position].author

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return dataDataModelArrayList.size
    }


   inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var publishDate: TextView
        var title: TextView
        var author: TextView
        var iv: ImageView

        init {
            publishDate = itemView.findViewById<View>(R.id.published_at) as TextView
            title = itemView.findViewById<View>(R.id.title) as TextView
            author = itemView.findViewById<View>(R.id.author) as TextView
            iv = itemView.findViewById<View>(R.id.detail_image) as ImageView
        }
    }
}