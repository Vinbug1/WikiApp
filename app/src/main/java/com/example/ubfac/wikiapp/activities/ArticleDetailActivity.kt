package com.example.ubfac.wikiapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.ubfac.wikiapp.R
import com.example.ubfac.wikiapp.manager.WikiManager
import com.example.ubfac.wikiapp.model.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var wikiManager: WikiManager
    private lateinit var currentPage:WikiPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)


        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val wikiPagJson =intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPagJson,WikiPage::class.java)

        article_detail_webview?.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return true
            }
        }
        article_detail_webview.loadUrl(currentPage.fullurl)
        wikiManager.addHistory(currentPage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == android.R.id.home){
            finish()
        }
        else if(item.itemId == R.id.action_favorite){
            try{

                if (wikiManager.getIsFavorite(currentPage.pageid )) {
                    wikiManager.removeFavorite(currentPage.pageid)
                    toast("Article removed from favorites")
                }
                else{
                    wikiManager.addFavorite(currentPage)
                    toast("Article added to favorites")
                }
            }
            catch (ex: Exception){
                toast("Unable to update this article.")
            }




        }
        return true
    }

}
