package com.example.aacdemo.wanandroid.home.activity

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aac_library.base.view.BaseActivity
import com.example.aacdemo.R
import com.example.aacdemo.wanandroid.home.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.act_home.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 14:59
 * @desc: 活动容器
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

    override fun initViewModel(): ViewModel? {
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