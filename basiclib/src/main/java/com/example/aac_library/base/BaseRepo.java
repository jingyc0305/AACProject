package com.example.aac_library.base;

/**
 * 接口调度器 持有BaseRemoteDataSource 中转ViewModel中的请求
 * ViewModel 则不在关心接口的实际调用 方便替换掉BaseRemoteDataSource的实现方式
 * @param <T>
 */
public class BaseRepo<T> {
    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
}
