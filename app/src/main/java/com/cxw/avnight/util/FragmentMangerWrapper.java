package com.cxw.avnight.util;



import androidx.fragment.app.Fragment;

import java.util.HashMap;



public class FragmentMangerWrapper {
    private volatile static FragmentMangerWrapper mInstance = null;  //volatile 可见性

    public synchronized static FragmentMangerWrapper getInstance() {
        if (mInstance == null) {
            synchronized (FragmentMangerWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FragmentMangerWrapper();
                }
            }
        }
        return mInstance;
    }

    private HashMap<String, Fragment> mHasMap = new HashMap<>();

    public Fragment createFragment(Class<?> clazz) {
        return createFragment(clazz, true);
    }

    public Fragment createFragment(Class<?> clazz, boolean isObtain) {
        Fragment fragment = null;
        String className = clazz.getName();
        if (mHasMap.containsKey(className)) {  //判断HasMap Key中是否有clazz.getName这个 Key
            fragment = mHasMap.get(className);
        } else {
            try {
                fragment = (Fragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (isObtain) {
            mHasMap.put(className, fragment);
        }
        return fragment;
    }
}
