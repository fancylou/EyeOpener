package net.muliba.eyeopener.ui.activity

import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import net.muliba.accounting.utils.ext.visible
import net.muliba.eyeopener.R
import net.muliba.eyeopener.ui.vo.VideoVO
import net.muliba.eyeopener.util.ImageLoadUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class VideoDetailActivity : AppCompatActivity() {


    companion object {
        val VIDEO_KEY = "VIDEO_KEY"
        fun startVideoDetail(vo: VideoVO): Bundle {
            val bundle = Bundle()
            bundle.putParcelable(VIDEO_KEY, vo)
            return bundle
        }
    }

    private lateinit var bean: VideoVO
    private lateinit var thumbImageView: ImageView
    private lateinit var orientationUtils: OrientationUtils
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        bean = intent.getParcelableExtra(VIDEO_KEY) as VideoVO

        initView()
        prepareVideoPlay()
    }

    override fun onBackPressed() {
        orientationUtils?.let {
            orientationUtils.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        getCurPlay().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        getCurPlay().onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurPlay().release()
        }
        orientationUtils?.let {
            orientationUtils.releaseListener()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            gsy_player.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }


    private fun initView() {
        val bgUrl = bean.blurred
        bgUrl?.let { ImageLoadUtils.displayHigh(this, iv_bottom_bg, bgUrl) }
        tv_video_desc.text = bean.description
        tv_video_title.text = bean.title
        val category = bean.category
        val duration = bean.duration
        val minute = duration?.div(60)
        val second = duration?.minus((minute?.times(60)) as Long)
        val realMinute: String
        val realSecond: String
        realMinute = if (minute!! < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        realSecond = if (second!! < 10) {
            "0$second"
        } else {
            second.toString()
        }
        tv_video_time.text = "$category / $realMinute : $realSecond"
        tv_video_favor.text = bean.collect.toString()
        tv_video_share.text = bean.share.toString()
        tv_video_reply.text = bean.share.toString()
        iv_video_download.setOnClickListener {
            //todo download video cache

        }
    }

    private fun prepareVideoPlay() {
        gsy_player.setUp(bean.playUrl, false, null, null)
        obtainThumbImageViewFromVideo()
        gsy_player.titleTextView.visible()
        gsy_player.backButton.visible()
        orientationUtils = OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true)
        //关闭自动旋转
        gsy_player.isRotateViewAuto = false
        gsy_player.isLockLand = false
        gsy_player.isShowFullAnimation = false
        gsy_player.isNeedLockFull = true
        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(this@VideoDetailActivity, true, true)
        }
        gsy_player.setVideoAllCallBack(object : VideoAllCallBack {
            override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onEnterFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onClickResume(url: String?, vararg objects: Any?) {
            }

            override fun onClickSeekbarFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onStartPrepared(url: String?, vararg objects: Any?) {
            }

            override fun onClickStartIcon(url: String?, vararg objects: Any?) {
            }

            override fun onTouchScreenSeekLight(url: String?, vararg objects: Any?) {
            }

            override fun onClickStartThumb(url: String?, vararg objects: Any?) {
            }

            override fun onEnterSmallWidget(url: String?, vararg objects: Any?) {
            }

            override fun onClickBlankFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {
            }

            override fun onTouchScreenSeekVolume(url: String?, vararg objects: Any?) {
            }

            override fun onClickBlank(url: String?, vararg objects: Any?) {
            }

            override fun onClickStop(url: String?, vararg objects: Any?) {
            }

            override fun onClickSeekbar(url: String?, vararg objects: Any?) {
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
            }

            override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {
            }

            override fun onTouchScreenSeekPosition(url: String?, vararg objects: Any?) {
            }

            override fun onPrepared(url: String?, vararg objects: Any?) {
                //开始播放了才能旋转和全屏
                orientationUtils.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {

            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                orientationUtils?.let { orientationUtils.backToProtVideo() }
            }
        })
        gsy_player.setLockClickListener { _, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
        gsy_player.backButton.setOnClickListener({
            onBackPressed()
        })
    }

    private fun obtainThumbImageViewFromVideo() {
        thumbImageView = ImageView(this)
        thumbImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        doAsync {
            val future = Glide.with(this@VideoDetailActivity)
                    .load(bean.feed)
                    .downloadOnly(100, 100)
            val cacheFile = future.get()
            val bitmap = BitmapFactory.decodeFile(cacheFile.absolutePath)
            uiThread {
                thumbImageView.setImageBitmap(bitmap)
                gsy_player.thumbImageView = thumbImageView
            }
        }
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (gsy_player.fullWindowPlayer != null) {
            gsy_player.fullWindowPlayer
        } else gsy_player
    }
}
