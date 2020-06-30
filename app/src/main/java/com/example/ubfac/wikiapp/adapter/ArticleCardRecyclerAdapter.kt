package com.example.ubfac.wikiapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.holders.CardHolder
import com.example.ubfac.wikiapp.model.WikiPage
import java.util.*

class ArticleCardRecyclerAdapter: RecyclerView.Adapter<CardHolder> (){

 val currentResults: ArrayList<WikiPage> = ArrayList()

     override fun getItemCount(): Int {
        return currentResults.size
    }




    override fun onBindViewHolder(holder: CardHolder, position: Int) {
     var page = currentResults[position]

        //update View within holder
        holder.updateWithPage(page)
     }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item,parent, false)
        return CardHolder(cardItem)
    }





}