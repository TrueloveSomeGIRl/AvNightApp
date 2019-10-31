package com.cxw.avnight.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse

import com.cxw.avnight.repository.UploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UploadViewModel : BaseViewModel() {
    private val repository by lazy { UploadRepository() }
    private val uploadActorInfo: MutableLiveData<String> = MutableLiveData()

    fun getUpload(parts: List<MultipartBody.Part>, info: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getUpload(parts, info)
            }
            executeResponse(result, { uploadActorInfo.value = result.data }, {})
        }
    }
}