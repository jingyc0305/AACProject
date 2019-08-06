package com.n4.scanlib.util;

import android.content.Context;
import android.util.Log;

import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.CustomBeanDao;
import com.n4.scanlib.dao.DaoManager;
import com.n4.scanlib.dao.MusicBean;
import com.n4.scanlib.dao.MusicBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DbMusicUtils {
    private static final String TAG = "music-" + DbMusicUtils.class.getSimpleName();
    private DaoManager mManager;
    private Context context;
    private static volatile DbMusicUtils instance = null;

    public DbMusicUtils(Context context) {
        this.context = context;
        mManager = DaoManager.getDaoManagerInstance(context);
    }

    public static DbMusicUtils getDbMusicUtilsInstance(Context context) {
        if (instance == null) {
            synchronized (DbMusicUtils.class) {
                if (instance == null) {
                    instance = new DbMusicUtils(context);
                }
            }
        }
        return instance;
    }


    /**
     * 完成localMusic记录的插入，如果表未创建，先创建localMusic表
     *
     * @param localMusicData
     * @return
     */
    public  boolean insertLocalMusic(MusicBean localMusicData) {
        boolean flag = false;
        try {
            flag = mManager.getDaoSession().getMusicBeanDao().insert(localMusicData) == -1 ? false : true;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG, "insert Meizi :" + flag + "-->" + localMusicData.toString());
        return flag;
    }


    /**
     * 新建歌单
     *
     * @param customName
     * @return
     */
    public  boolean insertCustom(String customName) {
        boolean flag = false;
        CustomBean customBean = new CustomBean();
        customBean.setCustom_name(customName);
        try {
            flag = mManager.getDaoSession().getCustomBeanDao().insert(customBean) == -1 ? false : true;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG, "insertCustom :" + flag + "-->" + customBean.toString());
        return flag;
    }

    /**
     * 歌单改名
     *
     * @return
     */
    public  boolean upDateCustom(CustomBean customBean) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(customBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 所有歌单
     * @return
     */
    public List<CustomBean> queryCustomList() {
        try {
            return    mManager.getDaoSession().loadAll(CustomBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 删除歌单
     * @return
     */
    public boolean deleteCustom(CustomBean customBean) {
        boolean flag = false;

        try {
            mManager.getDaoSession().delete(customBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "deleteCustom :" + flag + "-->" + customBean.toString());

        return false;
    }
    /**
     * 删除所有歌单记录
     *
     * @return
     */
    public boolean deleteAllCustom() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(CustomBean.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 歌单名称是否存在
     * @return
     */
    public boolean isCustomNameExist(String customName){

        boolean flag = false;
        try {
            QueryBuilder<CustomBean> queryBuilder = mManager.getDaoSession().queryBuilder(CustomBean.class);
            List<CustomBean> list =  queryBuilder.where(CustomBeanDao.Properties.Custom_name.eq(customName)).list();
            if(list == null){
                return flag;
            }
            if(list.size() < 1){
                return flag;
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }



    /**
     * 插入多条数据，在子线程操作
     *
     * @param musicList
     * @return
     */
    public boolean insertMultLocalMusicData(final List<MusicBean> musicList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (MusicBean localMusicData : musicList) {
                        mManager.getDaoSession().insertOrReplace(localMusicData);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @param localMusicData
     * @return
     */
    public  boolean updateLocalMusic(MusicBean localMusicData) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(localMusicData);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 更新指定信息
    public  boolean updateLocalMusicFavourByName(String name, String music_uuid,int favour_type) {
        boolean flag = false;
        List<MusicBean> list = queryLocalMusicByName(name,music_uuid);
        if (list == null){
            return flag;
        }
        if(list.size() < 1){
            Log.d(TAG, "updateLocalMusicFavourByName: " + name + "not exist");
            return flag;
        }
        try {
            for (MusicBean data : list){
                data.setFavour_type(favour_type);
                mManager.getDaoSession().update(data);
                flag = true;
            }
            if(flag){
                Log.d(TAG, "updateLocalMusicFavourByName: name:" + name + "favour : " + favour_type + " 更新成功");
            }else{
                Log.d(TAG, "updateLocalMusicFavourByName: name:" + name + "favour : " + favour_type + " 未更新");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public  boolean updateLocalMusicRecentByName(String name,String music_uuid, int recent_count) {
        boolean flag = false;
        List<MusicBean> list = queryLocalMusicByName(name,music_uuid);
        if (list == null){
            return flag;
        }
        if(list.size() < 1){
            Log.d(TAG, "updateLocalMusicFavourByName: " + name + "not exist");
            return flag;
        }
        try {
            for (MusicBean data : list){
                data.setRecent_count(recent_count);
                mManager.getDaoSession().update(data);
                flag = true;
            }
            if(flag){
                Log.d(TAG, "updateLocalMusicRecentByName: name:" + name + "recent : " + recent_count + " 更新成功");
            }else{
                Log.d(TAG, "updateLocalMusicRecentByName: name:" + name + "recent : " + recent_count + " 未更新");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }



    /**
     * 删除单条记录
     *
     * @param localMusicData
     * @return
     */
    public  boolean deleteLocalMusic(MusicBean localMusicData) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(localMusicData);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除全部记录
     *
     * @param localMusicData
     * @return
     */
    public  boolean deleteAllLocalMusic(MusicBean localMusicData) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(MusicBean.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除指定信息
     *
     * @param
     * @return
     */
    public boolean deleteLocalMusicByName(String name,String uuid) {
        boolean flag = false;
        try {
            QueryBuilder<MusicBean> queryBuilder = mManager.getDaoSession().queryBuilder(MusicBean.class);
            queryBuilder.where(MusicBeanDao.Properties.Music_name.eq(name),MusicBeanDao.Properties.Source_uuid.eq(uuid)).buildDelete().executeDeleteWithoutDetachingEntities();
            Log.d(TAG, "deleteLocalMusicByName: " + name + " 删除成功");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }



    /**
     * 删除所有music记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(MusicBean.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public  List<MusicBean> queryAllLocalMusic() {
        return mManager.getDaoSession().loadAll(MusicBean.class);
    }

    /**
     * 查询最大music id
     */
    public  Long queryMusicId() {
        MusicBean localMusicData;
        try {
            QueryBuilder<MusicBean> queryBuilder = mManager.getDaoSession().queryBuilder(MusicBean.class);
            localMusicData = queryBuilder.orderAsc(MusicBeanDao.Properties.Local_music_id).build().unique();
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }

        if (localMusicData == null) {
            return 0L;
        }

        return localMusicData.getLocal_music_id();
    }

    /**
     * 查询音乐播放次数
     */
    public  int queryMusicRecent(String name,String music_uuid) {
        List<MusicBean> list = queryLocalMusicByName(name,music_uuid);
        if (list == null){
            return 0;
        }
        if(list.size() < 1){
            Log.d(TAG, "updateLocalMusicFavourByName: " + name + "not exist");
            return 0;
        }
        MusicBean localMusicData = list.get(0);

        if (localMusicData == null) {
            return 0;
        }

        return localMusicData.getRecent_count();
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public  MusicBean queryMeiziById(long key) {
        return mManager.getDaoSession().load(MusicBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public  List<MusicBean> queryLocalMusicByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(MusicBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public  List<MusicBean> queryLocalMusicByQueryBuilder(long id) {
        try {
            QueryBuilder<MusicBean> queryBuilder = mManager.getDaoSession().queryBuilder(MusicBean.class);
            return queryBuilder.where(MusicBeanDao.Properties.Local_music_id.eq(id)).list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<MusicBean> queryLocalMusicByName(String name, String uuid) {
        try {
            QueryBuilder<MusicBean> queryBuilder = mManager.getDaoSession().queryBuilder(MusicBean.class);
            return queryBuilder.where(MusicBeanDao.Properties.Music_name.eq(name),MusicBeanDao.Properties.Source_uuid.eq(uuid)).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 所有收藏歌曲
     * @return
     */
    public List<MusicBean> queryLocalMusicFavour() {
        try {
            QueryBuilder<MusicBean> queryBuilder = mManager.getDaoSession().queryBuilder(MusicBean.class);
            return queryBuilder.where(MusicBeanDao.Properties.Favour_type.eq("1")).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 歌曲是否在数据库中存在
     * @return
     */
    public boolean musicExistInLocalDB(MusicBean musicBean){
        String name = musicBean.getMusic_name();
        String dev_uuid = musicBean.getSource_uuid();
        boolean flag = false;
        try {
            QueryBuilder<MusicBean> queryBuilder = mManager.getDaoSession().queryBuilder(MusicBean.class);
            List<MusicBean> list =  queryBuilder.where(MusicBeanDao.Properties.Music_name.eq(name),MusicBeanDao.Properties.Source_uuid.eq(dev_uuid)).list();
            if(list == null){
                return flag;
            }
            if(list.size() < 1){
                return flag;
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }




}
