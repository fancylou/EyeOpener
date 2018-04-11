package net.muliba.eyeopener

import android.app.Application
import android.arch.persistence.room.Room
import net.muliba.eyeopener.repository.room.MyDatabase

/**
 * Created by fancyLou on 11/04/2018.
 */


class EyeOpenerApplication : Application() {


    lateinit var database: MyDatabase
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "eyeopener").build()
    }
}