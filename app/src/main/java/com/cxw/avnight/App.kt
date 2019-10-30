package com.cxw.avnight

import android.app.Application
import android.content.Context
import com.cxw.avnight.state.EmptyCallback
import com.cxw.avnight.state.ErrorCallback
import com.cxw.avnight.state.LoadingCallback
import com.kingja.loadsir.core.LoadSir

import kotlin.properties.Delegates

class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()   //委托属性检查为空
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .setDefaultCallback(
                LoadingCallback::class.java
            )
            .commit()
    }
}