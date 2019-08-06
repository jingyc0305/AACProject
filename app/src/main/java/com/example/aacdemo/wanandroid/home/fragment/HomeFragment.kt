package com.example.aacdemo.wanandroid.home.fragment

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.view.BaseFragment
import com.example.aac_library.utils.image.GlideImageLoader
import com.example.aac_library.utils.image.ImageLoaderUtil
import com.example.aac_library.utils.util.LoggerUtil
import com.example.aacdemo.R
import com.example.aacdemo.wanandroid.home.MyLoadMoreView
import com.example.aacdemo.wanandroid.home.adapter.HomeArticalQuickAdapter
import com.example.business_library.viewmodel.HomeViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_observer.*
import kotlinx.android.synthetic.main.frag_home.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 15:04
 * @desc: 首页
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener{
    private var homeViewModel: HomeViewModel? = null
    private var bannerView : View? = null
    private var adapter: HomeArticalQuickAdapter? = null
    private  var mBGABanner : BGABanner? = null
    private var totalCount:Int = 0
    private var currPage:Int = 0

    private var isLoadMore = false
    override fun initViewModel(): BaseViewModel? {
        //homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel = getViewModelForFragment(this,HomeViewModel::class.java)
        homeViewModel?.lifecycleOwner = this
        homeViewModel?.articalLiveData?.observe(this, Observer {
            if (null == it.datas || it.datas.size == 0){
                showEmpty()
                return@Observer
            }else {
                totalCount = it.pageCount
                currPage = it.curPage
                if (isLoadMore){
                    adapter?.loadMoreComplete()
                    adapter?.addData(it.datas)
                }else{
                    home_artical_rv.adapter = adapter
                    adapter?.setNewData(it.datas)
                }
            }
        })

        if (homeViewModel?.bannerLiveData != null) {
        }
        homeViewModel?.bannerLiveData?.observe(this, Observer {
            mBGABanner?.setAdapter { _, imageView, feedImageUrl, _ ->
                if (imageView != null) {
                    ImageLoaderUtil
                        .get()
                        .setImageLoaderStrategy(GlideImageLoader())
                        .errorImage(R.mipmap.banner_empty)
                        .placeholderImage(R.mipmap.banner_empty)
                        .loadImage(context,imageView as ImageView,feedImageUrl)
                }
            }
            val bannerUrlList = ArrayList<String>()
            val bannerTitleList = ArrayList<String>()
            //获取banner数据中的url 和 tip
            it?.let {
                Observable.fromIterable(it).subscribe { banner ->
                    banner.imagePath.let { it1 -> bannerUrlList.add(it1) }
                    banner.title.let { it1 -> bannerTitleList.add(it1) }
                }
            }

            //给banner设置数据
            mBGABanner?.setData(bannerUrlList,bannerTitleList)

        })
        homeViewModel?.blelLiveData?.observe(this, Observer {
            showError("")
            LoggerUtil.d("blestate","connectState:${it.connectState},macAddress:${it.macAddress}")
        })
        return homeViewModel
    }

    override fun initLayoutResId(): Int {
        return R.layout.frag_home
    }

    override fun onRefresh() {
        isLoadMore = false
        refresh(true)
        Handler().postDelayed({
            refresh_ll?.isRefreshing = false
        }, 2000)
    }

    override fun initView() {

        refresh_ll.setOnRefreshListener(this)
        refresh_ll.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE)
        adapter = HomeArticalQuickAdapter()
        adapter?.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        adapter?.isFirstOnly(false)
        adapter?.setLoadMoreView(MyLoadMoreView())
        bannerView = layoutInflater?.inflate(R.layout.home_banner, home_artical_rv.parent as ViewGroup,false)
        //初始化banner
        mBGABanner = bannerView?.findViewById(R.id.home_banner_content) as BGABanner?
        adapter?.addHeaderView(bannerView)
        adapter?.setOnLoadMoreListener({
            if(currPage >= totalCount){
                //加载完成
                adapter?.loadMoreEnd()
            }else {
                isLoadMore = true
                currPage++
                Handler().postDelayed({
                    refresh(false)
                }, 2000)

            }
        },obser_recy)
        adapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener{
                _, _, _ ->  /*showError("")*/
            //手动连接蓝牙
            homeViewModel?.connectBletooth(false)
        }
        val manager = LinearLayoutManager(context)
        home_artical_rv.let {
            it?.layoutManager = manager
            //it?.adapter = adapter
        }
        //绑定RecycleView 防止disableLoadMoreIfNotFullPage空指针
        adapter?.bindToRecyclerView(home_artical_rv)
        adapter?.disableLoadMoreIfNotFullPage()
    }
    override fun initData() {
        homeViewModel?.getHomeBannerData()
        homeViewModel?.getHomeArticalData(currPage,true)
    }

    /**
     * 上拉加载
     */
    private fun refresh(isRefresh:Boolean){
        if (isRefresh){
            homeViewModel?.getHomeArticalData(0,true)
        }else if(isLoadMore){
            homeViewModel?.getHomeArticalData(currPage,false)
        }
    }
    override fun initDataBinding() {
    }
}