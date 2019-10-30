package com.cxw.avnight.state

import com.cxw.avnight.R
import com.kingja.loadsir.callback.Callback

class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.error_state_layout
    }

}