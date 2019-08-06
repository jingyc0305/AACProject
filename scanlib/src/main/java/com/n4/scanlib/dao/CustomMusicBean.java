package com.n4.scanlib.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 歌单音乐表
 */
@Entity
public class CustomMusicBean {
    @Id(autoincrement = true)                                  //autoincrement是设置ID值自增
    private Long id;
    @NotNull
    private String music_id;
    @NotNull
    private String custom_id;
    @Generated(hash = 562843345)
    public CustomMusicBean(Long id, @NotNull String music_id, @NotNull String custom_id) {
        this.id = id;
        this.music_id = music_id;
        this.custom_id = custom_id;
    }
    @Generated(hash = 1549888469)
    public CustomMusicBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMusic_id() {
        return this.music_id;
    }
    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }
    public String getCustom_id() {
        return this.custom_id;
    }
    public void setCustom_id(String custom_id) {
        this.custom_id = custom_id;
    }
}
