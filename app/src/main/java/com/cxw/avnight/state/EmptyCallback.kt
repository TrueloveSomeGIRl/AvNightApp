package com.cxw.avnight.state

import com.cxw.avnight.R
import com.kingja.loadsir.callback.Callback

class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
      return  R.layout.empty_state_layout
    }
}