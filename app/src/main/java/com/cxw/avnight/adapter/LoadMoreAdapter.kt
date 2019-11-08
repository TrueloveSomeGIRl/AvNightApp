package com.cxw.avnight.adapter



import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.LoadMoreBean
import com.drakeet.multitype.ItemViewBinder





class LoadMoreAdapter : ItemViewBinder<LoadMoreBean, LoadMoreAdapter.ViewHolder>(){
    public interface onLoadMore{
        fun onLoadMore(position:Int)
    }



    lateinit var onLoadMoreInterface: onLoadMore

    override fun onBindViewHolder(holder: ViewHolder, item: LoadMoreBean) {

        holder.itemView.setOnClickListener {

            if(onLoadMoreInterface != null){

                onLoadMoreInterface.onLoadMore(holder.adapterPosition)

            }

        }

    }



    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {

        return ViewHolder(inflater.inflate(R.layout.item_comment_footer,parent,false))

    }





    open class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



    }



}