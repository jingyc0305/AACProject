package com.example.greendaolib;

import org.greenrobot.greendao.annotation.*;

/**
 * @author: JingYuchun
 * @date: 2019/8/12 10:35
 * @desc:
 */
@Entity
public class MusicBean {
    @Id
    private Long id;

    @Property
    @NotNull
    private String musicName;

    @Generated(hash = 12481490)
    public MusicBean(Long id, @NotNull String musicName) {
        this.id = id;
        this.musicName = musicName;
    }

    @Generated(hash = 1899243370)
    public MusicBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMusicName() {
        return this.musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

}
