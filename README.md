# BaseAndroid[MVVM]
ViewModel+LiveData+Retrifit+RxJava+DataBinding 组合构架的一个基础MVVM架构

![img](https://images2018.cnblogs.com/blog/1041439/201803/1041439-20180328145939667-783068993.png)

> ##### 界面层(MVVM-V)

V层即View层 : 就是 静态的XML文件 负责绘制界面布局 包括

- Activity
- Fragment

> ##### 数据层(MVVM-M)

M层即Model层 : 负责定义解析数据  数据来源有:

- 数据库 文件 SharedPreferences等
- 远程服务器
- 内存缓存

> ##### 业务控制层(MVVM-VM)

VM层即ViewModel : 从Repository 仓库获取数据,对数据进行处理,类似Controller和Presenter 的角色,区别是ViewModel 角色比较单纯 不涉及UI相关的操作只处理业务相关数据 通过中间件LiveData与UI进行通讯

![img](https://upload-images.jianshu.io/upload_images/1412608-fac3f62e45a39669.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/960/format/webp)



------

**基于MVVM的代码模块划分总结**



![1563789292663](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\1563789292663.png)

> #### **UI** 界面

- **BaseActivity**
  1. 加载布局 initLayout()
  2. 视图初始化 initView()
  3. 状态栏/导航栏设置 包括隐藏/显示/沉浸式/全屏等
  4. 统一数据加载 initData()
  5. 带标题栏和不带标题栏BaseToolBarActivity 默认不带
  6. 带列表的BaseRecycleViewActivity 默认不带
  7. intent 跳转和带返回值
  8. 注册广播/发送广播
  9. 注册点击事件
  10. onResum onPause onStop onDestory 等重要的生命周期
  11. 返回事件

- **BaseFragment**

     1.包含activity的Base操作

     2.懒加载机制

- **BaseAdapter**

​          1.item视图填充

​          2.支持一个RecycleView 多Type类型view

​          3.点击/长按事件

- **BaseViewModel**

     1.业务处理 统一公共操作 loading dismiss toast等...

> #### NetWork 网络框架

 这里采用RxJava+OkHttp+Retrifit 封装一套完整的网络请求 并需要支持以下接口:

- **Get请求**
- **Post请求**
- **下载请求 支持断点续传**
- **上传请求 支持断点续传**
- **数据缓存**
- **Cookie管理**
- **Https SSL支持**
- **日志打印 包含: 请求url 响应数据 请求时间 等 自定义TAG**
- **统一设置请求头**
- **统一设置公共参数**
- **支持token刷新机制**
- **支持多BaseUrl 及动态切换等**

> #### Libs 第三方或自己的

- **Glide** 统一处理图片加载显示相关操作
- **OkHttp**  统一处理网络请求相关配置
- **Retrifit**  统一处理接口请求配置
- **RxJava**  统一处理线程间的切换 替代Handler 响应式处理数据 这个框架比较强大 暂时只是配合网络请求
- **RxAndroid** 配合RxJava 在Android中使用响应式编程
- **LiveData** 一种可观察的数据存储器类 被观察者 具有生命周期感知能力 只有在activity或fragment处于active时候才通知观察者进行数据变化
- **ViewModel**  负责管理和存储UI相关的数据 一个中间桥梁 连接view和model的重要组件 优势是并不会在视图销毁后而导致数据丢失 生命周期大于activity和fragment  解决fragment之间通讯繁琐问题
- **AndroidX Support** google 统一支持组件 持续更新
- **Room**  google推荐的数据库框架 与livedata配合更好.增删改查可直接返回livedata类型的数据便于视图使用
- .......

> #### Widgets 自定义公共组件

- **BaseDialog**  统一弹窗 支持自定义布局样式 动画等 也可以使用xpopup 封装的很丰富
- **BasePop**   统一弹窗 支持自定义布局样式 及动画 支持位置任意弹出
- **SDSeekBar ***
- **SDProgress ***
- **SDLoading***
- **SDSwitchBtn ***

> #### Utils 工具类

​     直接使用了强大的AndroidUtilCode


> #### 组件通讯

- **应用内通讯**

  `Intent/Bundle` 主要在于activity和fragment,service之间的通讯

  `Handler`  主线程和子线程之间的通信

  ------

  `EventBus` 管理比较混乱 需要注册反注册容易出错

  `RxBus` 配合RxJava 比较方便

  `LiveDataBus` 配合LiveData  比较方便

  综上 目前选择 LiveDataBus 因为框架使用了LiveData库

- **进程间通讯**

- **IPC**（Inter-Process Communication）**为进程间通信或跨进程通信，是指两个进程进行进程间通信的过程。**

  > **Android中进程间通信方式**

  - **Bundle**

    1. 基本数据类型或者能够被序列化
    2. 单方向的简单数据传输，使用简单,但有一定的局限性

  - **File Share**

    1.并发读写不可靠 可以是XML 、JSON、FILE

    2.无法进程间实时通信

  - **BroadCast**

    1.被动的进程间通讯,属于系统级别的监听,仅一次有效

    2.接收器中10s内未处理完事件会直接ANR 建议不要做耗时操作

    3.通过AMS进行转发过滤注册的Action回调通知onRecive函数

  - **Socket**

    1.网络通信

  - **ContentProvider**

    1.ContentResolver进行监听数据库 操作数据库 增删改查

    2.ContentProvider

  - **Messenger**

    1.轻量级IPC 基于AIDL实现 单进程处理 不支持并发

    2.只支持Bundle类型数据

  - **AIDL**

    1.处理多线程,多客户端并发访问

    2.支持实时通信 使用较复杂



  图标形式看吧

  | 方案 | Bundle             | File Share            | BroadCast  | Socket         | ContentProvider | Messenger               | AIDL           |
  | ---- | ------------------ | --------------------- | ---------- | -------------- | --------------- | ----------------------- | -------------- |
  | 优点 | 使用简单           | 使用简单              | 使用简单   | 网络通信       | 使用简单        | 轻量级IPC               | 支持多并发访问 |
  | 缺点 | 数据类型大小有限制 | 不支持实时,不支持并发 | 消耗资源大 | 支持实时跨进程 |                 | 仅支持Bundle 不支持并发 | 支持实时通信   |
  |      | 跳转               |                       | 网络监听等 | IM             | 全局变量        |                         | 所有           |



#####        `基于binder封装的okbinder 无需创建aidl接口 通过java接口及注解 实现进程间通讯 比较便捷`

##### Base层封装

```
/**
 * Aidl业务回调接口 须添加 @OkBinder.Interface 注解
 */
@OkBinder.Interface
interface ICallback {
    val data: String
    fun onResult(result: String)
}
/**
 * 抽象的Service
 */
abstract class BaseService : Service() {
    abstract fun initOkBinder():OkBinder
    override fun onBind(intent: Intent?): IBinder? {
        return initOkBinder()
    }
}

```

##### UserCase使用层示例

```
/**
 * 自定义Aidl业务接口 须添加 @OkBinder.Interface 注解
 */
@OkBinder.Interface
interface IRemoteService {
    fun doSomething(data:String,callback:ICallback)
}

/**
 * 具体的Service
 */
class MyService : BaseService() {
   override fun initOkBinder(): OkBinder {
        return OkBinder(object : IRemoteService {
            override fun doSomething(data: String, callback: ICallback) {
                Log.d("okbinder", ">> **data = $data ** <<")
                Log.d("okbinder", ">> **callback.data = ${callback.data} ** <<")
                callback.onResult("I am from binder callback data")
            }
        })
    }
}
```



------



> #### 类图

![1564543470722](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\1564543470722.png)





> #### 层级图

![1563852504876](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\1563852504876.png)



> #### 时序图

![1563853417338](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\1563853417338.png)

