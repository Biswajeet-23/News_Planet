package com.androidproject.newsplanet1_0.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.androidproject.newsplanet1_0.model.Article
import com.androidproject.newsplanet1_0.model.NewsResponse
import com.androidproject.newsplanet1_0.repository.service.RetrofitClient
import com.androidproject.newsplanet1_0.utils.Constants
import com.androidproject.newsplanet1_0.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticleDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>(){
    val breakingNews : MutableLiveData<MutableList<Article>> = MutableLiveData()
    var brekingPageNumebr = 1
    var breakingNewsResponse : NewsResponse? = null
    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPageNumber = 1
    var searchNewsResponse : NewsResponse? = null
    //var articles : MutableLiveData<List<Article>> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getBreakingNews("in", 1, Constants.API_KEY)
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it, null, 2)
                        }
                    }
                }
            }catch (exception : Exception) {
                Log.e("DataSource :: ", exception.message.toString())
            }
        }
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch {
                val response = RetrofitClient.api.getBreakingNews("in", params.requestedLoadSize, Constants.API_KEY)
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it, params.key+1)
                        }
                    }
                }
            }
        } catch (exception : Exception) {
            Log.e("DataSource :: ", exception.message.toString())
        }
    }
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }
}