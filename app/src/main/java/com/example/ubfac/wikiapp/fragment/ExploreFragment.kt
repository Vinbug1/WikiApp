package com.example.ubfac.wikiapp.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.WikiApplication
import com.example.ubfac.wikiapp.activities.SearchActivity
import com.example.ubfac.wikiapp.adapter.ArticleCardRecyclerAdapter
import com.example.ubfac.wikiapp.manager.WikiManager

class ExploreFragment: Fragment() {

    private var wikiManager: WikiManager? = null
    private var searchCardView: CardView? = null
    private  var exploreRecycler: RecyclerView? = null
    private var refresher:SwipeRefreshLayout? = null
    private var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById(R.id.search_card_view)
        exploreRecycler = view.findViewById(R.id.explore_article_recycler)
        refresher = view.findViewById(R.id.refresher)

      searchCardView!!.setOnClickListener {
          val searchIntent = Intent(context, SearchActivity::class.java)
          context!!.startActivity(searchIntent)
      }

        exploreRecycler!!.layoutManager =StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
              refresher?.isRefreshing = true

        }

    getRandomArticles()
        return view
    }


     fun getRandomArticles(){
       refresher!!.isRefreshing = true

        try {
            wikiManager!!.getRandom (10){ wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query.pages)
                activity!!.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher!!.isRefreshing = false
                }
            }
        } catch (ex: Exception){
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message).setTitle("oops!")
            var dialog = builder.create()
            dialog.show()
        }
    }
}