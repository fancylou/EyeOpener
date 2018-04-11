package net.muliba.eyeopener.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import net.muliba.accounting.utils.ext.DateHelper
import net.muliba.eyeopener.repository.api.ApiService
import net.muliba.eyeopener.repository.room.HomeItemDao
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem
import org.jetbrains.anko.doAsync
import java.util.*
import java.util.concurrent.Executor

/**
 * Created by fancyLou on 10/04/2018.
 */


class HomeRepository(val apiService: ApiService, val homeItemDao: HomeItemDao) {

    private val TAG = "HomeRepository"


    fun getHomeItemList(createDate: String) : LiveData<List<RoomHomeItem>> {
        refreshList(createDate)
        return homeItemDao.findHomeItemListByCreateDate(createDate)
    }

    private fun refreshList(createDate: String) {

        doAsync {
            val count = homeItemDao.countHomeItemByCreateDate(createDate)
            Log.i(TAG, "count: ${count}")
            if (count==null || count < 1) {
                val time = changeDateToLong(createDate)
                Log.i(TAG, "time: $time")
                val homedata = apiService.getHomeMoreData(time.toString(), "1")
                homedata.subscribe({ bean ->
                    bean.issueList?.map { isuue ->
                        val items = isuue.itemList
                        if (items?.isEmpty() != true) {
                            items?.filter{ it.type == "video" }?.map { item->
                                item.createDate = createDate
                                item.itemId = UUID.randomUUID().toString()
                                homeItemDao.insert(item)
                            }
                        }
                    }
                }, {e->
                    Log.e(TAG, "", e)
                })
            }
        }
    }

    private fun changeDateToLong(createDate: String): Long {
        val calendar = DateHelper.convertStringToCalendar(createDate)
        calendar.set(Calendar.HOUR, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}