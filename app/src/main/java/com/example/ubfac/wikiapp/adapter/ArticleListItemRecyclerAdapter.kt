package com.example.ubfac.wikiapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.holders.ListItemHolder

import com.example.ubfac.wikiapp.model.WikiPage
import java.util.*


class ArticleListItemRecyclerAdapter: RecyclerView.Adapter<ListItemHolder>() {

   val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResults.size
    }


    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
       var page = currentResults[position]


        //update View within holder
        holder.updateWithPage(page)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item,parent, false)
        return ListItemHolder(cardItem)
    }




}