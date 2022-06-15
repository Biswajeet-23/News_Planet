package com.androidproject.newsplanet1_0.repository

import com.androidproject.newsplanet1_0.model.Article
import com.androidproject.newsplanet1_0.repository.db.ArticleDatabase
import com.androidproject.newsplanet1_0.repository.service.RetrofitClient

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) =
        RetrofitClient.api.getBreakingNews(countryCode, pageNumber)
    suspend fun getSearchNews(q : String, pageNumber : Int) =
        RetrofitClient.api.getSearchNews(q, pageNumber)
    suspend fun upsert(article: Article) = db.getArticleDoa().insert(article)
    suspend fun delete(article: Article) = db.getArticleDoa().deleteArticle(article)
    fun getAllArticles() = db.getArticleDoa().getArticles()
}