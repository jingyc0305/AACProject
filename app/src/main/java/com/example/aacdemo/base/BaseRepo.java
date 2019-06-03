package com.example.aacdemo.base;

public class BaseRepo<T> {
    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
}
