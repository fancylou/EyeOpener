package net.muliba.eyeopener.repository.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.muliba.eyeopener.repository.room.entity.*

/**
 * Created by fancyLou on 10/04/2018.
 */


@Database(entities = [RoomHomeItem::class, RoomHomeItemData::class, RoomHomeItemCoverBean::class,
    RoomHomeItemAuthor::class, RoomHomeItemConsumption::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun homeItemDao(): HomeItemDao
}