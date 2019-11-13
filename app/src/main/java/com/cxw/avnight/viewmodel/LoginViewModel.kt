package com.cxw.avnight.viewmodel


import androidx.lifecycle.MutableLiveData
import com.cxw.avnight.base.BaseViewModel
import com.cxw.avnight.executeResponse
import com.cxw.avnight.mode.bean.EmailEntity
import com.cxw.avnight.mode.bean.LoginEntity
import com.cxw.avnight.mode.bean.RegisteredEntity
import com.cxw.avnight.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody


class LoginViewModel : BaseViewModel() {


    private val repository by lazy { LoginRepository() }
    val loginViewModel: MutableLiveData<LoginEntity> = MutableLiveData()
    val registeredViewModel: MutableLiveData<RegisteredEntity> = MutableLiveData()
    val updatePasswordViewModel: MutableLiveData<String> = MutableLiveData()
    val emailCodeViewModel: MutableLiveData<EmailEntity> = MutableLiveData()
    fun login(userBody: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.login(userBody)
            }
            executeResponse(result, { loginViewModel.value = result.data }, { })
        }
    }

    fun registered(userBody: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.registered(userBody)
            }
            executeResponse(result, { registeredViewModel.value = result.data }, {})
        }
    }

    fun getEmailCode(email: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getEmailCode(email)
            }
            executeResponse(result, { emailCodeViewModel.value = result.data }, {})
        }
    }

    fun Updatepassword(passwordBody: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.updatePassword(passwordBody)
            }
            executeResponse(result, { updatePasswordViewModel.value = result.data }, {})
        }
    }

}