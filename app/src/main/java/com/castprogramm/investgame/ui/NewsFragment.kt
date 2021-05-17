package com.castprogramm.investgame.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.HelpApp
import com.castprogramm.investgame.R
import com.castprogramm.investgame.news.NewsAdapter
import com.castprogramm.investgame.stock.Stoks
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground

// фрагмент для отрисовки новостей
class NewsFragment: Fragment() {
    companion object{
        val prefManagerNews = PreferenceManager.getDefaultSharedPreferences(HelpApp.globalContext)
        fun newInstance(excer: MutableList<String>): NewsFragment {
            var temp = NewsFragment()
            temp.recMSG = excer
            return temp
        }
    }
    private fun showNewsPrompt(){
        if (!prefManagerNews.getBoolean("didShowNewsPrompt", false)){
        android.os.Handler().post(Runnable {
            MaterialTapTargetPrompt.Builder(this).setClipToView(null)
                .setTarget(R.id.aleksey)
                .setPrimaryText("Здесь будут отображаться новости, которые будут влиять на цену акций")
                .setBackButtonDismissEnabled(false)
                .setPromptBackground(RectanglePromptBackground())
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED||
                            state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                    {
                        val prefEditor = prefManagerNews.edit()
                        prefEditor.putBoolean("didShowNewsPrompt", true)
                        prefEditor.apply()
                    }
                }
                .show()
        })}
    }

    var recMSG: MutableList<String> =  mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ret = inflater.inflate(R.layout.fragment_news, container, false)
        val newsAdapter = NewsAdapter().apply {
            msgs = recMSG
        }
        val recycler : RecyclerView = ret.findViewById(R.id.aleksey)
        recycler.adapter = newsAdapter
        val liveData = Stoks.liveDataNews
        liveData.observe(viewLifecycleOwner, Observer{
            newsAdapter.update(it)
        })
        showNewsPrompt()
        val pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi

        return ret
    }
}