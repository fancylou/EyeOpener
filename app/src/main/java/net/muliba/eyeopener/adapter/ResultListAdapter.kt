package net.muliba.eyeopener.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.muliba.accounting.utils.ext.DateHelper
import net.muliba.eyeopener.R
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem
import net.muliba.eyeopener.ui.vo.VideoVO
import net.muliba.eyeopener.util.ImageLoadUtils
import java.text.SimpleDateFormat

/**
 * Created by fancyLou on 13/04/2018.
 */


class ResultListAdapter(context: Context) : RecyclerView.Adapter<ResultListAdapter.ResultListViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val mContext: Context = context
    private var itemList: List<RoomHomeItem>? = null
    var itemClickListener: SearchResultItemClickListener? = null


    fun setlist(list: List<RoomHomeItem>) {
        if (itemList == null) {
            itemList = list
            notifyItemRangeInserted(0, list.size)
        } else {
            val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int {
                    return itemList?.size ?: 0
                }

                override fun getNewListSize(): Int {
                    return list.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return itemList?.get(oldItemPosition)?.data?.id == list[newItemPosition].data?.id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = itemList?.get(oldItemPosition)
                    val new = list[newItemPosition]
                    return old?.data?.title == new.data?.title
                            && old?.data?.author?.icon == new.data?.author?.icon
                }

            })
            itemList = list
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ResultListViewHolder =
            ResultListViewHolder(inflater.inflate(R.layout.item_search_result, parent, false))

    override fun getItemCount(): Int = itemList?.size ?: 0

    override fun onBindViewHolder(holder: ResultListViewHolder?, position: Int) {
        val bean = itemList?.get(position)
        if (bean != null) {
            val title = bean.data?.title ?: ""
            val category = bean.data?.category ?: ""
            val minute = bean.data?.duration?.div(60)
            val second = bean.data?.duration?.minus((minute?.times(60)) as Long)
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
            var date = SimpleDateFormat(DateHelper.md_string).format(bean.data?.releaseTime)
            holder?.tv_title?.text = title
            holder?.tv_time?.text = "$category / $realMinute'$realSecond'' / $date"

            val photo = bean.data?.cover?.feed
            if (photo != null) {
                ImageLoadUtils.display(mContext, holder?.iv_photo, photo)
            }

            //click
            holder?.itemView?.setOnClickListener {
                val desc = bean.data?.description
                val duration = bean.data?.duration
                val playUrl = bean.data?.playUrl
                val blurred = bean.data?.cover?.blurred
                val collect = bean.data?.consumption?.collectionCount
                val share = bean.data?.consumption?.shareCount
                val reply = bean.data?.consumption?.replyCount
                val vo = VideoVO(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply)
                itemClickListener?.click(vo)
            }
        }
    }

    class ResultListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var iv_photo: ImageView = itemView?.findViewById(R.id.iv_photo) as ImageView
        var tv_title: TextView = itemView?.findViewById(R.id.tv_title) as TextView
        var tv_time: TextView = itemView?.findViewById(R.id.tv_detail) as TextView
    }

    interface SearchResultItemClickListener {
        fun click(vo: VideoVO)
    }
}