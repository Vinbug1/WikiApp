package com.example.ubfac.wikiapp.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.WikiApplication
import com.example.ubfac.wikiapp.adapter.ArticleCardRecyclerAdapter
import com.example.ubfac.wikiapp.manager.WikiManager
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    var wikiManager: WikiManager? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()


      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        search_results_recycler.layoutManager = LinearLayoutManager(this)
        search_results_recycler.adapter = adapter

          //search()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

       if(item!!.itemId == android.R.id.home){
           finish()
       }
           return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =  searchItem!!.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()


        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                wikiManager?.search(query!!,0,20) { WikiResult ->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(WikiResult.query.pages)
                    runOnUiThread { adapter.notifyDataSetChanged() }
                }
                print("update search")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
     return super.onCreateOptionsMenu(menu)


    }
//    fun search(){
//        val searchItem = findViewById(R.id.action_search)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView =  searchItem!!.actionView as SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.setIconifiedByDefault(false)
//        searchView.requestFocus()
//
//
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                wikiManager?.search(query!!,0,20) { WikiResult ->
//                    adapter.currentResults.clear()
//                    adapter.currentResults.addAll(WikiResult.query!!.pages)
//                    runOnUiThread { adapter.notifyDataSetChanged() }
//                }
//                print("update search")
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })
//
//    }
}
