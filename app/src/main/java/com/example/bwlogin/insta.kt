package com.example.bwlogin

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.instagram.*

class insta:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.instagram, container, false)
        val mWebView = v.findViewById<View>(R.id.webview) as WebView


        if (mWebView != null){
            val webSettings = mWebView!!.settings
            webSettings.javaScriptEnabled = true
            mWebView!!.webViewClient = WebViewClient()
            mWebView!!.webChromeClient = WebChromeClient()
            mWebView!!.loadUrl("https://www.instagram.com/?hl=en")
            mWebView!!.webViewClient = object: WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressBar.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }
        }

        fun onBackPressed(){
            if (mWebView!!.canGoBack()) {
                mWebView!!.goBack()
            }
        }


        return v
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Instagram"
    }
}