package com.example.ubfac.wikiapp.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.WikiApplication
import com.example.ubfac.wikiapp.adapter.ArticleListItemRecyclerAdapter
import com.example.ubfac.wikiapp.manager.WikiManager
import com.example.ubfac.wikiapp.model.WikiPage
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.util.*

class HistoryFragment: android.app.Fragment() {

    private lateinit var wikiManager: WikiManager
    lateinit var historyRecycler: RecyclerView
    init {
        setHasOptionsMenu(true);
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    private val adapter = ArticleListItemRecyclerAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecycler = view.findViewById<RecyclerView>(R.id.history_article_recycler)
        historyRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        historyRecycler.adapter = adapter

        return view
    }
    override fun onResume() {
        super.onResume()

        doAsync {
            val historyArticles = wikiManager.getFavorites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(historyArticles as ArrayList<WikiPage>)
            activity!!.runOnUiThread{adapter.notifyDataSetChanged()}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (inflater != null) {
            inflater.inflate(R.menu.history_menu,menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_clear_history) {

            activity!!.alert("are you sure you want to clear your history?", "Confirm") {
                yesButton {
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager.clearHistory()
                    }
                    activity!!.runOnUiThread { adapter.notifyDataSetChanged() }
                }
                noButton {

                }
            }.show()
        }
        return true
    }
}