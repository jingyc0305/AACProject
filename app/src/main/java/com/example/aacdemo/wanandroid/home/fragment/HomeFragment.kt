package com.example.aacdemo.wanandroid.home.fragment

import android.graphics.Color
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.view.BaseFragment
import com.example.aac_library.utils.image.GlideImageLoader
import com.example.aac_library.utils.image.ImageLoaderUtil
import com.example.aacdemo.R
import com.example.aacdemo.demo.loadImage
import com.example.aacdemo.wanandroid.home.adapter.HomeArticalQuickAdapter
import com.example.aacdemo.wanandroid.home.viewmodel.HomeViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.frag_home.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 15:04
 * @desc: 首页
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener{
    private var homeViewModel: HomeViewModel? = null
    //private var adapter: HomeArticalAdapter? = null
    private var adapter: HomeArticalQuickAdapter? = null
    //private var articalList : MutableList<HomeArticalBean.DatasBean>?  = null
    private var bannerView : View? = null
    private  var mBGABanner : BGABanner? = null
    override fun initViewModel(): BaseViewModel? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel?.lifecycleOwner = this
        homeViewModel?.getArticalLiveData()?.observe(this, Observer {
            adapter?.setNewData(it)
            home_artical_rv.adapter = adapter
            adapter?.notifyDataSetChanged()
        })

        if (homeViewModel?.getBannerLiveData() != null) {
        }
        homeViewModel?.getBannerLiveData()?.observe(this, Observer {
            mBGABanner?.setAdapter { _, imageView, feedImageUrl, _ ->
                if (imageView != null) {
                    ImageLoaderUtil
                        .get()
                        .setImageLoaderStrategy(GlideImageLoader())
                        .errorImage(R.mipmap.banner_empty)
                        .placeholderImage(R.mipmap.banner_empty)
                        .loadImage(activity!!,imageView as ImageView,feedImageUrl)
                }
            }
            val bannerUrlList = ArrayList<String>()
            val bannerTitleList = ArrayList<String>()
            //获取banner数据中的url 和 tip
            it?.let {
                Observable.fromIterable(it).subscribe { banner ->
                    banner.imagePath?.let { it1 -> bannerUrlList.add(it1) }
                    banner.title?.let { it1 -> bannerTitleList.add(it1) }
                }
            }

            //给banner设置数据
            mBGABanner?.setData(bannerUrlList,bannerTitleList)

        })
        return homeViewModel
    }

    override fun initLayoutResId(): Int {
        return R.layout.frag_home
    }

    override fun onRefresh() {
        initData()
        Handler().postDelayed({
            refresh_ll?.isRefreshing = false
        }, 2000)
    }

    override fun initView() {

        refresh_ll.setOnRefreshListener(this)
        refresh_ll.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE)
//        adapter = HomeArticalAdapter(this)
        adapter = HomeArticalQuickAdapter()
        adapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener{
            adapter, view, position ->  showError()
        }
        val manager = LinearLayoutManager(context)
        home_artical_rv.let {
            it?.layoutManager = manager
            it?.adapter = adapter
        }
        bannerView = layoutInflater?.inflate(R.layout.home_banner, home_artical_rv.parent as ViewGroup,false)
        //初始化banner
        mBGABanner = bannerView?.findViewById(R.id.home_banner_content)
        adapter?.addHeaderView(bannerView)



    }
    override fun initData() {
        homeViewModel?.getHomeBannerData()
        homeViewModel?.getHomeArticalData(0)
    }

    override fun initDataBinding() {
    }
}