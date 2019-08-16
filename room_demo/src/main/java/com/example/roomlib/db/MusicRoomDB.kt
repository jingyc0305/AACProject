package com.example.roomlib.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author: JingYuchun
 * @date: 2019/8/8 11:32
 * @desc:
 */
@Database(entities = [Music::class], version = 1)
abstract class MusicRoomDB : RoomDatabase() {
    abstract fun getMusicDao(): MusicDao

    companion object {
        @Volatile
        private var instance: MusicRoomDB? = null

        fun getInstance(context: Context): MusicRoomDB? {
            if (instance == null) {
                synchronized(MusicRoomDB::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            MusicRoomDB::class.java,
                            "music_database"
                        ).build()
                    }
                }
            }
            return instance
        }
    }

}