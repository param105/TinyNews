package com.param.tinynewsapp.model

import java.util.ArrayList
import java.util.HashMap


object NewsModelRecycler {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<NewsModel> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String?, NewsModel> = HashMap()


     fun addItem(item: NewsModel) {
        ITEMS.add(item)
        ITEM_MAP.put(item.name, item)
    }




   /* *//**
     * A dummy item representing a piece of content.
     *//*
    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }*/


    data class NewsModel(var name:String?,var country:String?,var city:String?,var imgURL:String?) {

    }
}

