package com.example.ubfac.wikiapp.manager

import com.example.ubfac.wikiapp.model.WikiPage
import com.example.ubfac.wikiapp.model.WikiResult
import com.example.ubfac.wikiapp.providers.ArticleDataProvider
import com.example.ubfac.wikiapp.repositories.FavoritesRepository
import com.example.ubfac.wikiapp.repositories.HistoryRepository
import java.util.*


class WikiManager(
        private  val  provider: ArticleDataProvider,
        private  val favoritesRepository: FavoritesRepository,
        private  val historyRepository: HistoryRepository) {
    private lateinit var favoritesCache: ArrayList<WikiPage>
        private lateinit var historyCache: ArrayList<WikiPage>

    fun search (term: String,skip: Int, take: Int, handler:(result: WikiResult) -> Unit?){
        return provider.search(term, skip, take, handler)
    }
    fun getRandom(take: Int,handler: (result: WikiResult) -> Unit?){
        return provider.getRandom(take,handler)

    }
    fun getHistory():ArrayList<WikiPage>?{
        if (historyCache == null)
            historyCache = historyRepository.getAllHistory()
        return historyCache
    }
    fun getFavorites():ArrayList<WikiPage>?{
        if (favoritesCache == null)
            favoritesCache = favoritesRepository.getAllFavorites()
        return favoritesCache
    }
    fun addFavorite(page: WikiPage){
        favoritesCache.add(page)
        favoritesRepository.addFavorite(page)
    }
     fun removeFavorite(pageId:Int){
         favoritesRepository.removeFavoritesById(pageId)
         favoritesCache = favoritesCache.filter { it.pageid != pageId } as ArrayList<WikiPage>
     }
    fun getIsFavorite(pageId: Int): Boolean{
        return favoritesRepository.isArticleFavorite(pageId)
    }
    fun addHistory(page: WikiPage){
        historyCache.add(page)
        historyRepository.addHistory(page)
    }
     fun clearHistory(){
         historyCache.clear()
         val allHistory = historyRepository.getAllHistory()
         allHistory.forEach { historyRepository.removePageById(it.pageid) }
     }




}