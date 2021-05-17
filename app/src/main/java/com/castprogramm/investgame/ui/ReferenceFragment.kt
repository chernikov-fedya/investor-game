package com.castprogramm.investgame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment

// класс фрагмента справочника
class ReferenceFragment(var urlPath : URL): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // отрисовка html файла в фрагменте, при получении ссылки
        return WebView(requireContext()).apply { loadUrl(urlPath.path) }
    }
}
// перечисление ссылок на html файлы
enum class URL(var path : String){
    REFERENCE("file:///android_asset/index.html"),
    MANUAL("file:///android_asset/manual.htm")
}