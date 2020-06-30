package com.example.ubfac.wikiapp.repositories

import com.example.ubfac.wikiapp.model.WikiPage
import com.example.ubfac.wikiapp.model.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class FavoritesRepository(val databaseHelper: ArticleDatabaseOpenHelper) {
    private  val Table_Name: String = "Favorites"

    fun addFavorite(page: WikiPage){
        databaseHelper.use {
            insert(Table_Name,
                    "id" to page.pageid,
                    "title" to page.title,
                    "url" to page.fullurl,
                    "thumbnailJson" to Gson().toJson(page.thumbnail))
        }
    }
    fun removeFavoritesById(pageId: Int){
        databaseHelper.use {
            delete(Table_Name,"id = {pageId}","pageId" to pageId)
        }

    }

    fun isArticleFavorite(pageId: Int): Boolean{
        // get favorites and filter
        var pages = getAllFavorites()
        return pages.any { page ->
            page.pageid == pageId
        }
    }

    fun getAllFavorites(): ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser { id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid =id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(
                    thumbnailJson,
                    WikiThumbnail::class.java
            ).toString()
        }

        databaseHelper.use {
            select(Table_Name).parseList(articleRowParser)
        }
         return pages
    }

}