package net.muliba.eyeopener.repository.api.bean

import net.muliba.eyeopener.repository.room.entity.RoomHomeItem

/**
 * Created by fancyLou on 10/04/2018.
 */

open class HomeBean (
        open var nextPageUrl: String? = "",
        open var nextPublishTime: Long = 0L,
        open var newestIssueType: String? = "",
        open var dialog: Any? = null,
        open var issueList: List<HomeBeanIssue>? = null
)


open class HomeBeanIssue (
        open var releaseTime: Long = 0L,
        open var type: String? = "",
        open var date: Long = 0L,
        open var publishTime: Long =0L,
        open var count: Int = 0,
        open var itemList: List<RoomHomeItem>? = null
)