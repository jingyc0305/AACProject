package com.example.aacdemo.wanandroid.home.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.view.BaseActivity
import com.example.aacdemo.R
import kotlinx.android.synthetic.main.act_home.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 14:59
 * @desc: 首页
 */
class HomeActivity: BaseActivity(){
    private lateinit var navController: NavController
    override fun initView() {
        val  hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = hostFragment?.navController
        navigation_bottom_view.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.home-> {
                    navController.navigate(R.id.home)
                }
                R.id.nav-> {
                    navController.navigate(R.id.nav)
                }
                R.id.mine->{
                    navController.navigate(R.id.mine)
                }
            }
            true
        }
        navigation_bottom_view.let {
            it.setupWithNavController(navController)
        }

    }

    override fun initViewModel(): BaseViewModel? {
        return null
    }

    override fun initLayoutResId(): Int {
        return R.layout.act_home
    }

    override fun initData() {
    }

    override fun initDataBinding() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigateUp()
    }


}