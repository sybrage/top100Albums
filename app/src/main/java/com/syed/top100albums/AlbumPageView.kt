package com.syed.top100albums

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity

class AlbumPageView : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val albumPageUrl: String = intent.getStringExtra("albumPageUrl").toString()
        if (albumPageUrl.isNotEmpty()) {
            val myWebView = WebView(applicationContext).also {
                setContentView(it)
            }
            myWebView.loadUrl(albumPageUrl);
        } else {
            finish()
        }
    }
}