package com.androidproject.newsplanet1_0.utils
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.androidproject.newsplanet1_0.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.androidproject.newsplanet1_0.R

/// share news
fun shareNews(context: Context?, article: Article){
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article.urlToImage)
        putExtra(Intent.EXTRA_STREAM, article.urlToImage)
        putExtra(Intent.EXTRA_TITLE, article.title)
        type ="image/*"
    }
    context?.startActivity(Intent.createChooser(intent, "Share News On"))
}
// load image in image view
fun getCircularDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 48f
        setTint(context.resources.getColor(R.color.bgLineColor))
        start()
    }
}
fun ImageView.loadImage(url : String, progressDrawable : CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
@BindingAdapter("loadImage")
fun loadImage(imageView : ImageView, url : String?){
    if (url != null){
        imageView.loadImage(url, getCircularDrawable(imageView.context))
    }
}