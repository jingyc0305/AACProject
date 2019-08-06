package com.n4.scanlib.dao;

import android.content.Context;

import com.n4.scanlib.Constant;

import org.greenrobot.greendao.query.QueryBuilder;

public class DaoManager {
    private Context context;
    private static volatile DaoManager instance=null;
    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static ReleaseOpenHelper releaseOpenHelper;
    private static DaoSession sDaoSession;

    private DaoManager(Context context){
        this.context = context;
    }

    public static DaoManager getDaoManagerInstance(Context context){
        if(instance==null){
            synchronized(DaoManager.class){
                if(instance==null){
                    instance=new DaoManager (context);
                }
            }
        }
        return instance;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     * @return
     */

    private DaoMaster getDaoMaster(){
        if(sDaoMaster == null) {
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constant.DB_NAME, null);
            ReleaseOpenHelper helper = new ReleaseOpenHelper(context, Constant.DB_NAME, MusicBeanDao.class,CustomBeanDao.class);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     * @return
     */
    public DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
        if(releaseOpenHelper != null){
            releaseOpenHelper.close();
            releaseOpenHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}
