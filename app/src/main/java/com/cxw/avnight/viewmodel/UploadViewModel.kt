package com.cxw.avnight.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse

import com.cxw.avnight.repository.UploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel : BaseViewModel() {
    private val repository by lazy { UploadRepository() }
    private val uploadActorInfo: MutableLiveData<String> = MutableLiveData()

    fun getUpload(parts: List<MultipartBody.Part>, info: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getUpload(parts, info)
            }
            Log.d("cxx","$result")
            executeResponse(result, { uploadActorInfo.value = result.data }, {})
        }
    }
}