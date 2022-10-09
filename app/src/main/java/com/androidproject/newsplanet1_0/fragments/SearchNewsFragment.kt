package com.androidproject.newsplanet1_0.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidproject.newsplanet1_0.MainActivity
import com.androidproject.newsplanet1_0.R
import com.androidproject.newsplanet1_0.adapters.ArticleAdapter
import com.androidproject.newsplanet1_0.adapters.SavedNewsAdapter
import com.androidproject.newsplanet1_0.repository.NewsRepository
import com.androidproject.newsplanet1_0.repository.db.ArticleDatabase
import com.androidproject.newsplanet1_0.utils.Constants
import com.androidproject.newsplanet1_0.utils.Resource
import com.androidproject.newsplanet1_0.utils.shareNews
import com.androidproject.newsplanet1_0.viewModel.NewsViewModel
import com.androidproject.newsplanet1_0.viewModel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: ArticleAdapter
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository = NewsRepository(ArticleDatabase(requireActivity()))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)
        setupRecyclerView()
        newsAdapter.setOnItemCLickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
        newsAdapter.onSaveClickListener {
            viewModel.insertArticle(it)
            Snackbar.make(requireView(),"Saved", Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onDeleteClickListener {
            viewModel.deleteArticle(it)
            Snackbar.make(requireView(),"Removed", Snackbar.LENGTH_SHORT).show()
        }
        newsAdapter.onShareNewsClick {
            shareNews(context, it)
        }
        var searchJob : Job? = null
        etSearch.addTextChangedListener{ editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(Constants.SEARCH_TIME_DELAY)
                editable?.let{
                    if (!editable.toString().trim().isEmpty()){
                        viewModel.getSearchedNews(editable.toString())
                    }
                }
            }
        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { newsResponse ->
            when(newsResponse){
                is Resource.Success -> {
                    shimmerFrameLayout3.stopShimmerAnimation()
                    shimmerFrameLayout3.visibility = View.GONE
                    newsResponse.data?.let { news->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Error -> {
                    shimmerFrameLayout3.stopShimmerAnimation()
                    shimmerFrameLayout3.visibility = View.GONE
                    newsResponse.message?.let { message ->
                        Log.e(TAG,"Error :: $message")
                    }
                }
                is Resource.Loading -> {
                    shimmerFrameLayout3.startShimmerAnimation()
                    shimmerFrameLayout3.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun setupRecyclerView() {
        newsAdapter = ArticleAdapter()
        rvSearchNews.apply {
            adapter  = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}