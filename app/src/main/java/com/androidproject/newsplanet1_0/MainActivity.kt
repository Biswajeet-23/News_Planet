package com.androidproject.newsplanet1_0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androidproject.newsplanet1_0.repository.NewsRepository
import com.androidproject.newsplanet1_0.repository.db.ArticleDatabase
import com.androidproject.newsplanet1_0.viewModel.NewsViewModel
import com.androidproject.newsplanet1_0.viewModel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)
        bottomNavigationView.setupWithNavController(newsFragment.findNavController())
    }
}