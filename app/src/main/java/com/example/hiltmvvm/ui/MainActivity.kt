package com.example.hiltmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hiltmvvm.R
import com.example.hiltmvvm.model.Blog
import com.example.hiltmvvm.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "AppDebug"

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvents)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer {
            when(it){
                is DataState.Success<List<Blog>> ->{
                    displayProgressBar(false)
                    appendBlockTitles(it.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?){
        if (message != null){
            text.text = message
        } else {
            text.text = "Unknown Error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlockTitles(blogs: List<Blog>){
        val sb = StringBuilder()
        for (blog in blogs)
            sb.append(blog.title + "\n")
        text.text = sb.toString()
    }
}