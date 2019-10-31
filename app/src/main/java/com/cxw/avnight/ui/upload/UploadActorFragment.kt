package com.cxw.avnight.ui.upload


import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.cxw.avnight.R
import com.cxw.avnight.adapter.UploadActorImgAdapter

import com.cxw.avnight.base.BaseVMFragment
import com.cxw.avnight.dialog.AlertDialog
import kotlinx.android.synthetic.main.upload_fragment.*


import com.yanzhenjie.album.Album

import com.cxw.avnight.util.AppConfigs

import com.cxw.avnight.viewmodel.UploadViewModel
import java.util.*
import kotlin.collections.HashMap

class UploadActorFragment : BaseVMFragment<UploadViewModel>() {


    override fun providerVMClass(): Class<UploadViewModel>? = UploadViewModel::class.java
    private val imgPathList = arrayListOf<String>()
    private val mActorInfo = HashMap<String, Objects>()
    override fun getLayoutResId(): Int = R.layout.upload_fragment
    private lateinit var cameraDialog: AlertDialog
    private val uploadActorImgAdapter by lazy { UploadActorImgAdapter() }
    override fun initData() {
        // mActorInfo.put("",)
    }

    override fun onNetReload(v: View) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBaseLoadService.showSuccess()  //这里方案怎么解决
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        initRv()
        initCamera()
    }

    private fun initRv() {
        with(actor_info_img_rv) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = uploadActorImgAdapter
        }
        initAdapter()
    }

    private fun initAdapter() {
        with(uploadActorImgAdapter) {
            setOnItemChildClickListener { _, _, position ->
                remove(position)
            }
        }
    }

    private fun initCamera() {
        camera.setOnClickListener {
            if (uploadActorImgAdapter.itemCount > 6) {
                Toast.makeText(context, "dayu", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            cameraDialog = AlertDialog.Builder(context!!)
                .setContentView(R.layout.dialog_change_layout)
                .setOnClickListener(R.id.dialog_choose_one, View.OnClickListener {
                    Album.camera(this)
                        .image()
                        .onResult {
                            uploadActorImgAdapter.addData(it)
                        }
                        .start()
                    cameraDialog.dismiss()
                })
                .setOnClickListener(R.id.dialog_choose_two, View.OnClickListener {
                    Album.image(this)
                        .multipleChoice()
                        .camera(false)
                        .columnCount(AppConfigs.COLUMN_COUNT)
                        .selectCount(AppConfigs.SELECT_COUNT)
                        .onResult {
                            it.forEach {
                                imgPathList.add(it.path)
                            }
                            uploadActorImgAdapter.addData(imgPathList)
                        }
                        .onCancel {

                        }
                        .start()
                    cameraDialog.dismiss()

                })
                .setOnClickListener(R.id.dialog_cancel, View.OnClickListener {
                    cameraDialog.dismiss()
                })
                .fullWidth()
                .formBottom(true)
                .show()
        }
    }
}


