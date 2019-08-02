package com.example.aacdemo.wanandroid.home.viewmodel

import androidx.lifecycle.ViewModelProviders
import com.example.business_library.repository.HomeDataSource
import com.example.business_library.repository.HomeRepository
import com.example.business_library.viewmodel.HomeViewModel
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

/**
 * @author: JingYuchun
 * @date: 2019/7/31 16:35
 * @desc:
 */
class HomeViewModelTest {
//    private lateinit var homeFragment: HomeFragment
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRepository: HomeRepository
    private lateinit var homeDataSource: HomeDataSource
    @Before
    fun setUp() {
//        homeFragment = mock()
//        homeViewModel = ViewModelProviders.of(homeFragment).get(HomeViewModel::class.java)
        homeViewModel = spy(HomeViewModel())
        homeDataSource = HomeDataSource(verify(homeViewModel))
        homeRepository = HomeRepository(homeDataSource)
    }

    @Test
    fun getHomeArticalData() {
        homeRepository.queryHomeArticleData(0)
    }

    @Test
    fun getHomeBannerData() {
        homeRepository.queryHomeBannerData()
    }
}