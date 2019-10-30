package com.cxw.avnight


import android.content.Intent
import android.view.View
import com.cxw.avnight.base.BaseFragment
import com.cxw.avnight.dialog.AlertDialog
import kotlinx.android.synthetic.main.upload_fragment.*

import android.os.Environment
import androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK
import com.cxw.avnight.util.AppConfigs
import com.cxw.avnight.util.Glide4Engine

import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.entity.SelectionSpec
import com.zhihu.matisse.internal.utils.MediaStoreCompat
import java.io.File


class UploadActorFragment : BaseFragment() {
    private lateinit var mMediaStoreCompat: MediaStoreCompat
    private lateinit var mSpec: SelectionSpec
    override fun getLayoutResId(): Int = R.layout.upload_fragment
    private lateinit var cameraDialog: AlertDialog
    override fun initView() {
        camera.setOnClickListener {
            cameraDialog = AlertDialog.Builder(context!!)
                .setContentView(R.layout.dialog_change_layout)
                .setOnClickListener(R.id.dialog_choose_one, View.OnClickListener {
                    Matisse.from(this@UploadActorFragment)
                        .choose(MimeType.ofImage())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(6)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(
                            CaptureStrategy(
                                true,
                                "com.cxw.avnight.fileprovider"
                            )
                        )
                        .imageEngine(Glide4Engine())
                        .forResult(AppConfigs.ALBUM)
                    cameraDialog.dismiss()
                })
                .setOnClickListener(R.id.dialog_choose_two, View.OnClickListener {
                    mSpec = SelectionSpec.getInstance()
                    mMediaStoreCompat = MediaStoreCompat(activity)
                    mMediaStoreCompat.setCaptureStrategy(
                        CaptureStrategy(
                            true,
                            "com.cxw.avnight.fileprovider"
                        )
                    )
                    mMediaStoreCompat.dispatchCaptureIntent(context, AppConfigs.TAKE_PHOTO)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
          if (resultCode==AppConfigs.TAKE_PHOTO&&requestCode==RESULT_OK){

          }else{

          }
    }
    override fun initData() {
    }
}