package com.example.roomlib.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: JingYuchun
 * @date: 2019/8/8 11:04
 * @desc:
 */
@Entity(tableName = "music")
class Music{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "music_name")
    lateinit var musicName: String

}