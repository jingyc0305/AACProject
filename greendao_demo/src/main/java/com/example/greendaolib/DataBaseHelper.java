package com.example.greendaolib;

import android.content.Context;
import com.example.greendaolib.greendao.DaoMaster;
import com.example.greendaolib.greendao.DaoSession;
import com.example.greendaolib.greendao.MusicBeanDao;

import java.util.List;

/**
 * @author: JingYuchun
 * @date: 2019/8/12 10:49
 * @desc: greenDao 数据操作辅助类
 */
public class DataBaseHelper {

    private DaoMaster.DevOpenHelper devOpenHelper;
    private MusicBeanDao musicBeanDao;
    private static DataBaseHelper dataBaseHelper;

    private DataBaseHelper(Context context) {
        //1.创建数据库
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "music_db");
        //2.获取读写对象
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        //3.获取管理类
        DaoSession daoSession = daoMaster.newSession();
        //4.获取表对象
        musicBeanDao = daoSession.getMusicBeanDao();
    }

    public static DataBaseHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            synchronized (DataBaseHelper.class) {
                if (dataBaseHelper == null) {
                    dataBaseHelper = new DataBaseHelper(context);
                }
            }
        }
        return dataBaseHelper;
    }
    //----------增删改查操作------------

    /**
     * 插入一条数据
     */
    public void insertData(MusicBean musicBean){
        musicBeanDao.insert(musicBean);
    }
    /**
     * 删除一条数据
     */
    public void deleteData(MusicBean musicBean){
        musicBeanDao.delete(musicBean);

    }
    /**
     * 修改一条数据
     */
    public void updateData(MusicBean musicBean){
        musicBeanDao.updateInTx(musicBean);
    }
    /**
     * 查询数据
     */
    public List<MusicBean> queryData(){
        return musicBeanDao.queryBuilder().list();
    }
    /**
     * 删除全部数据
     */
    public void deleteAll() {
        //删除全部数据
        musicBeanDao.deleteAll();
        //清除musicBeanDao缓存
        //musicBeanDao.detachAll();
    }
}
