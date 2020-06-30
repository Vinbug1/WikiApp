package com.example.ubfac.wikiapp.fragment


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.WikiApplication
import com.example.ubfac.wikiapp.adapter.ArticleCardRecyclerAdapter
import com.example.ubfac.wikiapp.manager.WikiManager
import com.example.ubfac.wikiapp.model.WikiPage
import org.jetbrains.anko.doAsync
import java.util.*

class FavoritesFragment: android.app.Fragment() {

    private lateinit var wikiManager: WikiManager
    lateinit var favoritesRecycler: RecyclerView
    private  val adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        favoritesRecycler = view.findViewById(R.id.favorites_article_recycler)
        favoritesRecycler.layoutManager = LinearLayoutManager(context)
        favoritesRecycler.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favoriteArticles = wikiManager.getFavorites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favoriteArticles as ArrayList<WikiPage>)
            activity!!.runOnUiThread{adapter.notifyDataSetChanged()}
        }
    }

 }