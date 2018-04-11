package net.muliba.eyeopener.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.muliba.eyeopener.R
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem
import net.muliba.eyeopener.util.ImageLoadUtils

/**
 * Created by fancyLou on 11/04/2018.
 */


class HomeDailyListAdapter(context: Context) : RecyclerView.Adapter<HomeDailyListAdapter.HomeDailyListViewHolder>() {


    private var itemList: MutableList<RoomHomeItem>? = null
    private val inflater = LayoutInflater.from(context)
    private val mContext: Context = context

    fun setList(list: MutableList<RoomHomeItem>) {
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
                    return itemList?.get(oldItemPosition)?.data?.id == list.get(newItemPosition).data?.id
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

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeDailyListViewHolder {
        return HomeDailyListViewHolder(inflater.inflate(R.layout.item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeDailyListViewHolder?, position: Int) {
        val bean = itemList?.get(position)
        val title = bean?.data?.title ?: ""
        val category = bean?.data?.category ?: ""
        val minute = bean?.data?.duration?.div(60)
        val second = bean?.data?.duration?.minus((minute?.times(60)) as Long)
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
        holder?.tv_title?.text = title
        holder?.tv_detail?.text = "发布于 $category / $realMinute:$realSecond"
        val author = bean.data?.author
        if (author != null && !TextUtils.isEmpty(author.icon)) {
            ImageLoadUtils.display(mContext, holder?.iv_user, author.icon)
        } else {
            holder?.iv_user?.visibility = View.GONE
        }
        val photo = bean.data?.cover?.feed
        if (photo != null) {
            ImageLoadUtils.display(mContext, holder?.iv_photo, photo)
        }
        //todo click
    }

    class HomeDailyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_detail: TextView? = null
        var tv_title: TextView? = null
        var iv_photo: ImageView? = null
        var iv_user: ImageView? = null

        init {
            tv_detail = itemView?.findViewById(R.id.tv_detail) as TextView?
            tv_title = itemView?.findViewById(R.id.tv_title) as TextView?
            iv_photo = itemView?.findViewById(R.id.iv_photo) as ImageView?
            iv_user = itemView?.findViewById(R.id.iv_user) as ImageView?
        }
    }
}