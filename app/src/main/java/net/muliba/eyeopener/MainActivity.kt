package net.muliba.eyeopener

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import net.muliba.eyeopener.ui.fragment.HomeFragment
import net.muliba.eyeopener.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {


    lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params

        tv_bar_title.text = "日报"
        iv_search.setOnClickListener {
            searchFragment = SearchFragment()
            searchFragment.show(supportFragmentManager, SearchFragment.TAG)
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_main_content, HomeFragment()).commit()

    }
}
