package com.example.roomlib

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

/**
 * @author: JingYuchun
 * @date: 2019/8/8 10:02
 * @desc: 数据库操作基类
 */
@Dao
interface BaseDao<T> {

    /**
     * 插入数据
     */
    @Insert
    fun insertData(data: T)

    /**
     * 插入多条集合 List
     */
    @Insert
    fun insertDatas(datas: List<T>)


    /**
     * 删除数据
     */
    @Delete
    fun deleteData(data: T)

    /**
     * 删除多条数据 List
     */
    @Delete
    fun deleteData(data: List<T>)

    /**
     * 删除表所有数据
     */
    fun deleteAll()

//    /**
//     * 查询数据
//     */
//    fun queryeData(): T

    /**
     * 查询数据
     */
    fun queryeData(): LiveData<List<T>>
    /**
     * 更新数据
     */
    @Update
    fun updateData(data: T)

    /**
     * 更新数据 List
     */
    @Update
    fun updateDatas(data: List<T>)

}