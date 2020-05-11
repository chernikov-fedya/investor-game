package com.castprogramm.investgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment

// класс фрагмента справочника
class ReferenceFragmemt: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // отрисовка html файла в фрагменте
        return WebView(container?.context).apply { loadUrl("file:///android_asset/index.html") }
    }
}