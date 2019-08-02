package com.example.aacdemo.wanandroid.home.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.aacdemo.R
import kotlinx.android.synthetic.main.act_home.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 14:59
 * @desc: 首页
 */
class HomeActivity: AppCompatActivity(){
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_home)
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
    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigateUp()
    }


}