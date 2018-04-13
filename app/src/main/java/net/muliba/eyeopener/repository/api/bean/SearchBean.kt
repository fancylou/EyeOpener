package net.muliba.eyeopener.repository.api.bean

import net.muliba.eyeopener.repository.room.entity.RoomHomeItem

/**
 * Created by fancyLou on 13/04/2018.
 */


data class SearchBean(
        var count: Int? = 0,
        var total: Long? = 0,
        var nextPageUrl: String? = "",
        var itemList: List<RoomHomeItem>? = ArrayList()
)