package com.cxw.avnight.adapter

import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.App
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.util.DisplayUtil
import kotlinx.android.synthetic.main.loufeng_item_actor_layout.view.*

class LouFengAdapter(layoutResId: Int = R.layout.loufeng_item_actor_layout) :
    BaseQuickAdapter<ActorInfo, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: ActorInfo?) {
        val w:Int=DisplayUtil.getScreenWidth(App.CONTEXT)
        val h:Int=item!!.actorImgs[0].img_h.toInt()
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        /**
         *  因为这里上传得图片宽高不一  这里就直接随机写死 不然有些 item 高很长 有些很短
         */
        params.height = if (h>950) DisplayUtil.dip2px(App.CONTEXT, 200.0f+(5..10).random()*10) else h
        params.width= w/2
        helper!!.itemView.actor_iv.layoutParams = params

        Glide.with(mContext).load(item.actorImgs[0].img_url).into(helper.getView(R.id.actor_iv))
        helper.setText(R.id.actor_tv,item.actor_name)
    }
}