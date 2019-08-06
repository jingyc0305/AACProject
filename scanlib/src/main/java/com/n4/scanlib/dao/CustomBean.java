package com.n4.scanlib.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 歌单表
 */
@Entity
public class CustomBean {
    @Id(autoincrement = true)                                  //autoincrement是设置ID值自增
    private Long custom_id;
    @NotNull
    private String custom_name;
    @Generated(hash = 1529026605)
    public CustomBean(Long custom_id, @NotNull String custom_name) {
        this.custom_id = custom_id;
        this.custom_name = custom_name;
    }
    @Generated(hash = 1921656256)
    public CustomBean() {
    }
    public Long getCustom_id() {
        return this.custom_id;
    }
    public void setCustom_id(Long custom_id) {
        this.custom_id = custom_id;
    }
    public String getCustom_name() {
        return this.custom_name;
    }
    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    @Override
    public String toString() {
        return "CustomBean{" +
                "custom_id=" + custom_id +
                ", custom_name='" + custom_name + '\'' +
                '}';
    }
}
