package com.example.roomlib.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.roomlib.BaseDao

/**
 * @author: JingYuchun
 * @date: 2019/8/8 11:01
 * @desc:
 */
@Dao
interface MusicDao {
    /**
     * 查询音乐列表
     */
    @Query("SELECT * FROM music")
    fun queryData() : LiveData<List<Music>>
    /**
     * 删除表全部数据
     */
    @Query("DELETE FROM music")
    fun deleteAll()
    /**
     * 删除一条数据
     */
    @Delete
    fun deleteData(data: Music)
    /**
     * 插入一条数据
     */
    @Insert
    fun insertData(data: Music)
    /**
     * 修改一条数据
     */
    @Update
    fun updateData(data: Music)
}

