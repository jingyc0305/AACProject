package com.n4.scanlib.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class MusicBean {

    @Id(autoincrement = true)                                  //autoincrement是设置ID值自增
    private Long local_music_id;
    @NotNull
    private String music_name;
    private String music_title;
    private String artist;
    private String music_album;
    private int music_size;
    private String music_path;
    private int favour_type;
    private int album_id;
    private int recent_count;
    private String uuid;     //音乐在哪个列表里     //音乐在my_music_list3是否存在
    private String source_uuid;                                                                  //音乐是从那个设备收藏的
    @Generated(hash = 1115168434)
    public MusicBean(Long local_music_id, @NotNull String music_name, String music_title, String artist,
                     String music_album, int music_size, String music_path, int favour_type, int album_id,
                     int recent_count, String uuid, String source_uuid) {
        this.local_music_id = local_music_id;
        this.music_name = music_name;
        this.music_title = music_title;
        this.artist = artist;
        this.music_album = music_album;
        this.music_size = music_size;
        this.music_path = music_path;
        this.favour_type = favour_type;
        this.album_id = album_id;
        this.recent_count = recent_count;
        this.uuid = uuid;
        this.source_uuid = source_uuid;
    }
    @Generated(hash = 1899243370)
    public MusicBean() {
    }
    public Long getLocal_music_id() {
        return this.local_music_id;
    }
    public void setLocal_music_id(Long local_music_id) {
        this.local_music_id = local_music_id;
    }
    public String getMusic_name() {
        return this.music_name;
    }
    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }
    public String getMusic_title() {
        return this.music_title;
    }
    public void setMusic_title(String music_title) {
        this.music_title = music_title;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getMusic_album() {
        return this.music_album;
    }
    public void setMusic_album(String music_album) {
        this.music_album = music_album;
    }
    public int getMusic_size() {
        return this.music_size;
    }
    public void setMusic_size(int music_size) {
        this.music_size = music_size;
    }
    public String getMusic_path() {
        return this.music_path;
    }
    public void setMusic_path(String music_path) {
        this.music_path = music_path;
    }
    public int getFavour_type() {
        return this.favour_type;
    }
    public void setFavour_type(int favour_type) {
        this.favour_type = favour_type;
    }
    public int getAlbum_id() {
        return this.album_id;
    }
    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }
    public int getRecent_count() {
        return this.recent_count;
    }
    public void setRecent_count(int recent_count) {
        this.recent_count = recent_count;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getSource_uuid() {
        return this.source_uuid;
    }
    public void setSource_uuid(String source_uuid) {
        this.source_uuid = source_uuid;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "local_music_id=" + local_music_id +
                ", music_name='" + music_name + '\'' +
                ", music_title='" + music_title + '\'' +
                ", artist='" + artist + '\'' +
                ", music_album='" + music_album + '\'' +
                ", music_size=" + music_size +
                ", music_path='" + music_path + '\'' +
                ", favour_type=" + favour_type +
                ", album_id=" + album_id +
                ", recent_count=" + recent_count +
                ", uuid='" + uuid + '\'' +
                ", source_uuid='" + source_uuid + '\'' +
                '}';
    }
}
