package com.param.tinynews.model

import java.util.ArrayList
import java.util.HashMap


object NewsModelRecycler {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<NewsDataModel> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String?, NewsDataModel> = HashMap()


     fun addItem(item: NewsDataModel) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }




   /* *//**
     * A dummy item representing a piece of content.
     *//*
    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }*/


    data class NewsDataModel(var id:String?,
                             var author:String?,
                             var title:String?,
                             var description:String?,
                             var siteURL: String?,
                             var imgURL:String?,
                             var publishedAt:String?,
                             var content:String? )


}

