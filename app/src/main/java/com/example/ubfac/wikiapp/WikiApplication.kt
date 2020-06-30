package com.example.ubfac.wikiapp

import android.app.Application
import com.example.ubfac.wikiapp.manager.WikiManager
import com.example.ubfac.wikiapp.providers.ArticleDataProvider
import com.example.ubfac.wikiapp.repositories.ArticleDatabaseOpenHelper
import com.example.ubfac.wikiapp.repositories.FavoritesRepository
import com.example.ubfac.wikiapp.repositories.HistoryRepository

class WikiApplication: Application() {
    private lateinit var dbHelper: ArticleDatabaseOpenHelper
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var historyRepository: HistoryRepository
    private lateinit var wikiProvider: ArticleDataProvider
    lateinit var wikiManager: WikiManager

    override fun onCreate() {
        super.onCreate()
        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper)
        historyRepository = HistoryRepository(dbHelper)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider,favoritesRepository, historyRepository)
    }
}