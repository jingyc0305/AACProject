package com.n4.scanlib.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

public class ReleaseOpenHelper extends DaoMaster.OpenHelper  {
    private String TAG = "music-" + ReleaseOpenHelper.class.getSimpleName();
    Class<? extends AbstractDao<?,?>>[] daoClasses;

    @SafeVarargs
    public ReleaseOpenHelper(Context context, String name, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        super(context, name);
        this.daoClasses = daoClasses;
    }

    @SafeVarargs
    public ReleaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        super(context, name, factory);
        this.daoClasses = daoClasses;
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
        if(oldVersion < newVersion){
            Log.d(TAG, "onUpgrade: migrate enter");
            MigrationHelper.migrate(db,daoClasses);
        }
    }

}
