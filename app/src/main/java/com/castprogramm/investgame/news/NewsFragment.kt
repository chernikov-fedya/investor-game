package com.castprogramm.investgame.news

import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.R
import com.castprogramm.investgame.Updater
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.swipeRefreshLayout
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

// фрагмент для отрисовки новостей
class NewsFragment: Fragment() {
    companion object{
        fun newInstance(excer: MutableList<String>): NewsFragment {
            var temp = NewsFragment()
            temp.recMSG = excer
            return temp
        }
    }
    private fun showNewsPrompt(){
        val prefManagerNews = PreferenceManager.getDefaultSharedPreferences(this.context)
        if (!prefManagerNews.getBoolean("didShowNewsPrompT", false)){
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
                        prefEditor.putBoolean("didShowNewsPrompT", true)
                        prefEditor.apply()
                    }
                }
                .show()
        })}
    }

    var recMSG: MutableList<String> =  mutableListOf()
    private lateinit var runnable: Runnable
    var handler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_news, container, false)
        ret.swipeRefreshLayout.setOnRefreshListener {
            // инициализирует новый Runnable
            runnable = Runnable {
                // обновлени е информации RecyclerView
                var recycler : RecyclerView = ret.findViewById(R.id.aleksey)
                recycler.adapter = NewsAdapter()
                    .apply {
                    msgs = recMSG
                }
                // Для того чтобы после обновления значок пропадал
                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 500.toLong()
            )
        }

        var recycler : RecyclerView = ret.findViewById(R.id.aleksey)
        recycler.adapter = NewsAdapter().apply {
            msgs = recMSG
        }
        showNewsPrompt()
        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi

        return ret
    }
}