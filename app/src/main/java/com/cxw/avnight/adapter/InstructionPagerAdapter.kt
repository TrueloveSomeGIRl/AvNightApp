package com.cxw.avnight.adapter


import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cxw.avnight.LouFengInFragment
import java.util.HashMap

@Suppress("DEPRECATION")
class InstructionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mPagerMap: HashMap<Int, LouFengInFragment> = HashMap()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val obj = super.instantiateItem(container, position)
        if (obj is LouFengInFragment) {
            mPagerMap[position] = obj
        }
        return obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        mPagerMap.remove(position)
    }

    override fun getItem(i: Int): Fragment {
        return LouFengInFragment.newInstance(i)
    }

    override fun getCount(): Int {
        return 2
    }
}
