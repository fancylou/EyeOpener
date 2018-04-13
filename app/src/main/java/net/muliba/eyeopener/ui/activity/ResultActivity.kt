package net.muliba.eyeopener.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_result.*
import net.muliba.accounting.utils.ext.go
import net.muliba.eyeopener.R
import net.muliba.eyeopener.adapter.ResultListAdapter
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem
import net.muliba.eyeopener.ui.vo.VideoVO
import net.muliba.eyeopener.viewmodel.ResultActivityViewModel

class ResultActivity : AppCompatActivity() {

    companion object {
        val KEYWORD_KEY = "KEYWORD_KEY"
        fun start(keyword: String): Bundle {
            val bundle = Bundle()
            bundle.putString(KEYWORD_KEY, keyword)
            return bundle
        }
    }

    private var keyWord = ""
    private lateinit var viewModel: ResultActivityViewModel
    private val adapter: ResultListAdapter by lazy { ResultListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setContentView(R.layout.activity_result)
        keyWord = intent.getStringExtra(KEYWORD_KEY) ?: ""
        setToolbar()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.itemClickListener = object : ResultListAdapter.SearchResultItemClickListener {
            override fun click(vo: VideoVO) {
                this@ResultActivity.go<VideoDetailActivity>(VideoDetailActivity.startVideoDetail(vo))
            }
        }

        viewModel = ViewModelProviders.of(this).get(ResultActivityViewModel::class.java)
        viewModel.mResultList.observe(this, Observer<List<RoomHomeItem>> { list ->
            if (list != null) {
                adapter.setlist(list)
            }
        })
        if (!TextUtils.isEmpty(keyWord)) {
            viewModel.search(keyWord)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        val bar = supportActionBar
        bar?.title = "'$keyWord' 相关"
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}
