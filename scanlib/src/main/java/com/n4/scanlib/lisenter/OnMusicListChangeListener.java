package com.n4.scanlib.lisenter;

public interface OnMusicListChangeListener {

     void OnUsbMusicChanged();
     void OnLocalMusicChanged();
     void onFavourMusicChanged();
     void onCostomListChanged();
}
