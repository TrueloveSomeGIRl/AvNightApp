package com.cxw.avnight.ui.upload


import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxw.avnight.R
import com.cxw.avnight.adapter.UploadActorImgAdapter
import com.cxw.avnight.base.BaseVMFragment
import com.cxw.avnight.dialog.AlertDialog
import kotlinx.android.synthetic.main.upload_fragment.*
import com.yanzhenjie.album.Album
import com.cxw.avnight.util.AppConfigs
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.BaseTools.toRequestBody
import com.cxw.avnight.viewmodel.UploadViewModel
import com.google.gson.Gson
import kotlin.collections.HashMap

class UploadActorFragment : BaseVMFragment<UploadViewModel>() {
    override fun providerVMClass(): Class<UploadViewModel> = UploadViewModel::class.java
    private val imgPathList = arrayListOf<String>()
    private val mActorInfo = HashMap<String, Any>()
    override fun getLayoutResId(): Int = R.layout.upload_fragment
    private lateinit var cameraDialog: AlertDialog
    private val uploadActorImgAdapter by lazy { UploadActorImgAdapter() }
    override fun initData() {
        submit_tv.setOnClickListener {
            initEt()
            mViewModel.getUpload(
                BaseTools.filesToMultipartBodyParts(context!!, imgPathList),
                toRequestBody(Gson().toJson(mActorInfo))
            )
        }
    }

    private fun initEt() {
        //这里该怎么搞 才好 这里 太复杂了
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, age_tv.text.toString(), "年龄不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, gender_et.text.toString(), "性别不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")
        BaseTools.checkEtIsNotEmpty(context!!, name_et.text.toString(), "名字不能为空")




        if (imgPathList.size == 0) {
            Toast.makeText(context, getString(R.string.upload_img), Toast.LENGTH_LONG)
                .show()
            return
        }
        mActorInfo["actor_name"] = name_tv.text.toString().trim()
        mActorInfo["actor_age"] = age_tv.text.toString().trim()
        mActorInfo["actor_gender"] = gender_tv.text.toString().trim()
        mActorInfo["actor_phone"] = phone_tv.text.toString().trim()
        mActorInfo["actor_wx"] = wx_tv.text.toString().trim()
        mActorInfo["actor_qq"] = qq_tv.text.toString().trim()
        mActorInfo["actor_workaddress"] = address_tv.text.toString().trim()
        mActorInfo["actor_introduce"] = class_content_tv.text.toString().trim()
        mActorInfo["actor_evaluate"] = evaluation_tv.text.toString().trim()
        mActorInfo["actor_city"] = city_tv.text.toString().trim()
        mActorInfo["actor_bust"] = bust_tv.text.toString().trim()
        mActorInfo["actor_height"] = height_tv.text.toString().trim()
        mActorInfo["actor_weight"] = weight_tv.text.toString().trim()
        mActorInfo["actor_isinvalid"] = 0
        mActorInfo["actor_isverification"] = 0
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
            layoutManager = LinearLayoutManager(context)
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

    override fun startObserve() {
        super.startObserve()

    }

    private fun initCamera() {
        camera.setOnClickListener {
            if (uploadActorImgAdapter.itemCount >= AppConfigs.SELECT_COUNT) {

                Toast.makeText(context, getString(R.string.up_to_six_pieces), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            cameraDialog = AlertDialog.Builder(context!!)
                .setContentView(R.layout.dialog_change_layout)
                .setOnClickListener(R.id.dialog_choose_one, View.OnClickListener {
                    Album.camera(this)
                        .image()
                        .onResult {
                            uploadActorImgAdapter.addData(it)
                            imgPathList.add(it)
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
                            imgPathList.clear()
                            it.forEach {
                                imgPathList.add(it.path)
                            }
                            uploadActorImgAdapter.addData(imgPathList)
                            if (uploadActorImgAdapter.itemCount < AppConfigs.SELECT_COUNT) return@onResult
                            for (pos in uploadActorImgAdapter.itemCount until 6) {  //TODO
                                uploadActorImgAdapter.remove(pos)
                            }
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


