package com.c242ps263.riceup.ui.prediction

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PredictionScreen() {
    val mUrl = "http://35.244.148.228/"

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}