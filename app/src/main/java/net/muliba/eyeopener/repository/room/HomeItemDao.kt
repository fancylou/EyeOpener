package net.muliba.eyeopener.repository.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import net.muliba.eyeopener.repository.room.entity.RoomHomeItem


/**
 * Created by fancyLou on 10/04/2018.
 */


@Dao
interface HomeItemDao {


    @Insert
    fun insert(item: RoomHomeItem)


    @Query("select count(*) from roomhomeitem where eye_create_date = :createDate ")
    fun countHomeItemByCreateDate(createDate: String): Long


    @Query("select * from roomhomeitem where eye_create_date = :createDate  order by date DESC, releaseTime ASC ")
    fun findHomeItemListByCreateDate(createDate: String): LiveData<List<RoomHomeItem>>

}