package com.cxw.avnight

import android.content.Context
import android.graphics.Color
import com.cxw.avnight.adapter.InstructionPagerAdapter
import com.cxw.avnight.base.BaseFragment
import kotlinx.android.synthetic.main.loufeng_fragment.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView

class LouFengFragment : BaseFragment() {
    private val indicatorTitleList by lazy {
        arrayOf(
            resources.getString(R.string.demining),
            resources.getString(R.string.attend_class)
        )
    }

    override fun getLayoutResId(): Int = R.layout.loufeng_fragment
    override fun initView() {
        initMagicIndicator()
        vp.adapter = InstructionPagerAdapter(childFragmentManager)
    }


    override fun initData() {

    }

    private fun initMagicIndicator() {
        val commonNavigator = CommonNavigator(context)
        println(indicatorTitleList.size)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return indicatorTitleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.normalColor = Color.parseColor("#666666")
                simplePagerTitleView.selectedColor = R.color.appThemeColor
                simplePagerTitleView.text = indicatorTitleList[index]
                simplePagerTitleView.setOnClickListener { vp.currentItem = index }
                badgePagerTitleView.innerPagerTitleView = simplePagerTitleView
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setColors(R.color.appThemeColor)
                return linePagerIndicator
            }
        }
        mi.navigator = commonNavigator
        ViewPagerHelper.bind(mi, vp)
    }
}