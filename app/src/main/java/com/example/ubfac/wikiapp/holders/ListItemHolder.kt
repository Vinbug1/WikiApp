package com.example.ubfac.wikiapp.holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.activities.ArticleDetailActivity
import com.example.ubfac.wikiapp.model.WikiPage
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ListItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val articleImageView: ImageView = itemView.findViewById(R.id.result_icon)
    private  val titleTextView: TextView = itemView.findViewById(R.id.result_title)
    private lateinit var currentPage: WikiPage

        init {
            itemView.setOnClickListener {
                var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
                var pageJson = Gson().toJson(currentPage)
                detailPageIntent.putExtra("page", pageJson)
                itemView.context.startActivity(detailPageIntent)
            }

        }
    fun updateWithPage(page: WikiPage) {
        currentPage = page

        if (page.thumbnail != null)
            Picasso.get().load(page.thumbnail).into(articleImageView)

        titleTextView.text = page.title
        currentPage = page
    }
}