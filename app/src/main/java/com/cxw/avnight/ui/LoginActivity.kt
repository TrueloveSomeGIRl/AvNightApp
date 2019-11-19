package com.cxw.avnight.ui


import android.view.View
import android.widget.CompoundButton

import androidx.lifecycle.Observer
import com.cxw.avnight.R
import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.SPUtil
import com.cxw.avnight.viewmodel.LoginViewModel
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseVMActivity<LoginViewModel>(), CompoundButton.OnCheckedChangeListener {

    override fun providerVMClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_login
    private val userInfo = HashMap<String, Any>()
    private var code = ""
    private var sex = "男"
    private var loginOrRegister = true
    private var forgetPassword = false
    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, line_view)
        StatusBarUtil.setLightMode(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.boy -> sex = getString(R.string.boy)
            R.id.girl -> sex = getString(R.string.girl)
        }
    }

    override fun requestSuccess(requestSuccess: Boolean) {
        super.requestSuccess(requestSuccess)
        BaseTools.initLottieAnim(lv, View.VISIBLE, true)
    }

    override fun initData() {
        get_email_code.setOnClickListener {
            if (!user_email_tv.text.isNotBlank()) {
                toast(getString(R.string.email_not_empty))
                return@setOnClickListener
            } else {
                if (!BaseTools.isEmail(user_email_tv.text.toString())) {
                    toast(getString(R.string.email_format_error))
                    return@setOnClickListener
                } else {
                    mViewModel.getEmailCode(user_email_tv.text.toString().trim())
                }
            }
        }
        login.setOnClickListener {
            when (login.text.toString()) {
                getString(R.string.login) -> {
                    userInfo.clear()
                    if (!user_email_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.email_not_empty))
                        return@setOnClickListener
                    }
                    if (!BaseTools.isEmail(user_email_tv.text.toString())) {
                        toast(getString(R.string.email_format_error))
                        return@setOnClickListener
                    }
                    if (!password_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.password_not_empty))
                        return@setOnClickListener
                    }

                    userInfo["email"] = user_email_tv.text.toString().trim()
                    userInfo["password"] = password_tv.text.toString()
                    mViewModel.login(
                        RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            Gson().toJson(userInfo)
                        )
                    )
                }
                getString(R.string.register) -> {
                    userInfo.clear()
                    if (!user_name_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.name_not_empty))
                        return@setOnClickListener
                    }
                    if (!user_email_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.email_not_empty))
                        return@setOnClickListener
                    }
                    if (!BaseTools.isEmail(user_email_tv.text.toString())) {
                        toast(getString(R.string.email_format_error))
                        return@setOnClickListener
                    }
                    if (!input_email_code_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.email_code_not_empty))
                        return@setOnClickListener
                    }
                    if (!password_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.password_not_empty))
                        return@setOnClickListener
                    }
                    if (!reply_password_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.two_password_not_empty))
                        return@setOnClickListener
                    }
                    if (password_tv.text.toString() != reply_password_tv.text.toString()) {
                        toast(getString(R.string.two_passwords_are_inconsistent))
                        return@setOnClickListener
                    }
                    if (code != input_email_code_tv.text.toString().trim()) {
                        toast(getString(R.string.email_code_error))
                        return@setOnClickListener
                    }
                    userInfo["name"] = user_name_tv.text.toString()
                    userInfo["email"] = user_email_tv.text.toString().trim()
                    userInfo["password"] = password_tv.text.toString()
                    userInfo["code"] = input_email_code_tv.text.toString().trim()
                    userInfo["sex"] = sex
                    mViewModel.registered(
                        RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            Gson().toJson(userInfo)
                        )
                    )
                }
                getString(R.string.sure) -> {
                    userInfo.clear()
                    if (!user_email_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.email_not_empty))
                        return@setOnClickListener
                    }
                    if (!BaseTools.isEmail(user_email_tv.text.toString())) {
                        toast(getString(R.string.email_format_error))
                        return@setOnClickListener
                    }
                    if (!input_email_code_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.email_code_not_empty))
                        return@setOnClickListener
                    }

                    if (!password_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.password_not_empty))
                        return@setOnClickListener
                    }
                    if (!reply_password_tv.text.toString().isNotBlank()) {
                        toast(getString(R.string.two_password_not_empty))
                        return@setOnClickListener
                    }
                    if (password_tv.text.toString() != reply_password_tv.text.toString()) {
                        toast(getString(R.string.two_passwords_are_inconsistent))
                        return@setOnClickListener
                    }

                    if (code != input_email_code_tv.text.toString().trim()) {
                        toast(getString(R.string.email_code_error))
                        return@setOnClickListener
                    }

                    userInfo["email"] = user_email_tv.text.toString().trim()
                    userInfo["password"] = password_tv.text.toString()
                    userInfo["code"] = input_email_code_tv.text.toString().trim()
                    mViewModel.updatePassword(
                        RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            Gson().toJson(userInfo)
                        )
                    )
                }

            }
        }

        forget_password_tv.setOnClickListener {
            if (forgetPassword) {
                input_email_code_layout.visibility = View.GONE
                reply_user_password_layout.visibility = View.GONE
                forgetPassword = false
                login.text = getString(R.string.login)
                forget_password_tv.text = getString(R.string.find_password)
            } else {
                input_email_code_layout.visibility = View.VISIBLE
                reply_user_password_layout.visibility = View.VISIBLE
                forgetPassword = true
                login.text = getString(R.string.sure)
                forget_password_tv.text = getString(R.string.back_login)
            }
        }
        register_tv.setOnClickListener {
            if (loginOrRegister) {
                loginXmlUI(
                    View.VISIBLE,
                    View.VISIBLE,
                    getString(R.string.click_login),
                    getString(R.string.register),
                    View.INVISIBLE,
                    View.VISIBLE,
                    View.VISIBLE
                )
                loginOrRegister = false
            } else {
                loginXmlUI(
                    View.GONE,
                    View.GONE,
                    getString(R.string.register_user),
                    getString(R.string.login),
                    View.VISIBLE,
                    View.GONE,
                    View.GONE
                )
                loginOrRegister = true
            }

        }
    }

    private fun loginXmlUI(
        userNameLayoutVisibility: Int,
        replyUserPasswordVisibility: Int,
        registerTv: String,
        loginTv: String,
        forgetPasswordTvVisibility: Int,
        emailCodeLayoutVisibility: Int,
        rgLayoutVisibility: Int

    ) {
        user_name_layout.visibility = userNameLayoutVisibility
        input_email_code_layout.visibility = emailCodeLayoutVisibility
        reply_user_password_layout.visibility = replyUserPasswordVisibility
        register_tv.text = registerTv
        login.text = loginTv
        forget_password_tv.visibility = forgetPasswordTvVisibility
        rg.visibility = rgLayoutVisibility
    }

    override fun requestLoading(isLoading: Boolean) {
        super.requestLoading(isLoading)
        BaseTools.initLottieAnim(lv, View.VISIBLE, true)
    }


    override fun startObserve() {
        super.startObserve()
        mViewModel.run {
            loginViewModel.observe(this@LoginActivity, Observer {
                //后面改造用DB存
                SPUtil.saveValue("token", it.token)
                SPUtil.saveValue("isLogin", true)
                SPUtil.saveValue("headImg", it.userHeadImg)
                SPUtil.saveValue("userId", it.userId)
                SPUtil.saveValue("userName", it.userName)
                startActivity<MainActivity>()
                finish()
            })
            emailCodeViewModel.observe(this@LoginActivity, Observer {
                get_email_code.text = getString(R.string.send_email_success)
                code = it.code
            })
            registeredViewModel.observe(this@LoginActivity, Observer {
                toast(getString(R.string.register_success))
                userInfo["email"] = it.email
                userInfo["password"] = it.password
                mViewModel.login(
                    RequestBody.create(
                        MediaType.parse("application/json;charset=UTF-8"),
                        Gson().toJson(userInfo)
                    )
                )
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        toast(e.message.toString())
        BaseTools.initLottieAnim(lv, View.GONE, false)
    }


}
