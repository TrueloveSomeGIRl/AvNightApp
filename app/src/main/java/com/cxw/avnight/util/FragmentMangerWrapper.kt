package com.cxw.avnight.util


import androidx.fragment.app.Fragment

import java.util.HashMap


class FragmentMangerWrapper {
    private lateinit var fragment: Fragment
    private val mHasMap = HashMap<String, Fragment>()

    fun createFragment(clazz: Class<*>): Fragment {
        val className = clazz.name
        if (mHasMap.containsKey(className)) {
            fragment = mHasMap[className]!!
        } else {
            try {
                fragment = Class.forName(className).newInstance() as Fragment
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }
        mHasMap[className] = fragment
        return fragment
    }

    companion object {
        @Volatile
        private var mInstance: FragmentMangerWrapper? = null  //volatile 可见性

        val instance: FragmentMangerWrapper
            @Synchronized get() {
                if (mInstance == null) {
                    synchronized(FragmentMangerWrapper::class.java) {
                        if (mInstance == null) {
                            mInstance = FragmentMangerWrapper()
                        }
                    }
                }
                return mInstance!!
            }
    }

}
