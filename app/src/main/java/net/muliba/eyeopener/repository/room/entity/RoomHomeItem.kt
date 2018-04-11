package net.muliba.eyeopener.repository.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by fancyLou on 10/04/2018.
 */

@Entity
open class RoomHomeItem(

        @PrimaryKey
        open var itemId: String = "",

        /**
         * 创建日期 视频的获取日期
         */
        @ColumnInfo(name = "eye_create_date")
        open var createDate:String? ="",
        open var type: String? = "",
        @Embedded
        open var data: RoomHomeItemData? = null
)

@Entity
open class RoomHomeItemData(
        open var dataType: String? = "",
        @PrimaryKey
        open var id: Int = 0,
        open var title: String? = "",
        open var description:String? = "",
        @Embedded
        open var consumption: RoomHomeItemConsumption? =null,
        open var slogan:String? = "",
        open var category: String? = "",
        @Embedded
        open var author: RoomHomeItemAuthor? = null,
        @Embedded
        open var cover: RoomHomeItemCoverBean? = null,
        open var playUrl: String = "",
        open var duration: Long? = 0L,
        open var releaseTime : Long? =0L,
        open var date: Long? = 0L

)

@Entity
open class RoomHomeItemCoverBean (
        @PrimaryKey
        open var feed : String = "",
        open var detail : String? = "",
        open var blurred : String? = "",
        open var sharing : String? = "",
        open var homepage:String? = ""
)

@Entity
open class RoomHomeItemConsumption (
        @PrimaryKey(autoGenerate = true)
        open var conId: Int = 0,
        open var collectionCount: Int = 0,
        open var shareCount: Int= 0,
        open var replyCount: Int= 0
)

@Entity
open class RoomHomeItemAuthor (
        @PrimaryKey(autoGenerate = true)
        open var authorId: Int = 0,
        open var icon: String = ""
)

