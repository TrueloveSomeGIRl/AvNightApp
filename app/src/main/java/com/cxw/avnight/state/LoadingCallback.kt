package com.cxw.avnight.state

import com.cxw.avnight.R
import com.kingja.loadsir.callback.Callback

class LoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.loading_state_layout
    }
}