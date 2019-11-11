package com.cxw.avnight.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.cxw.avnight.R
import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.SPUtil
import com.cxw.avnight.viewmodel.LoginViewModel
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.RequestBody

class LoginActivity : BaseVMActivity<LoginViewModel>() {
    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_login
    private val userInfo = HashMap<String, Any>()
    private var code = ""
    private var loginOrRegister = true
    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, line_view)
        StatusBarUtil.setLightMode(this)
    }

    override fun initData() {
        login.setOnClickListener {
            if (loginOrRegister) {
                userInfo.clear()
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    password_tv.text.toString(),
                    getString(R.string.password_not_empty)
                )
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    user_email_tv.text.toString(),
                    getString(R.string.email_not_empty)
                )

                if (!BaseTools.isEmail(user_email_tv.text.toString())) {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.email_format_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    return@setOnClickListener
                }
                userInfo["email"] = user_email_tv.text.toString().trim()
                userInfo["password"] = "cxw9681"
                mViewModel.login(
                    RequestBody.create(
                        okhttp3.MediaType.parse("application/json;charset=UTF-8"),
                        Gson().toJson(userInfo)
                    )
                )
                Log.d("cxx","${ Gson().toJson(userInfo)}")

                playLottieAnim()
            } else {
                userInfo.clear()
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    user_name_tv.text.toString(),
                    getString(R.string.name_not_empty)
                )
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    user_email_tv.text.toString(),
                    getString(R.string.email_not_empty)
                )
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    input_email_code_tv.text.toString(),
                    getString(R.string.email_code_not_empty)
                )
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    password_tv.text.toString(),
                    getString(R.string.password_not_empty)
                )
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    reply_password_tv.text.toString(),
                    getString(R.string.two_password_not_empty)
                )
                if (code != input_email_code_tv.text.toString().trim()) {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.email_code_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                userInfo["name"] = user_name_tv.text
                userInfo["email"] = user_email_tv.text.toString().trim()
                userInfo["password"] = password_tv.text
                userInfo["code"] = input_email_code_tv.text.toString().trim()
                get_email_code.setOnClickListener {
                    if (input_email_code_tv.text.isNotEmpty()) {
                        BaseTools.checkEtIsNotEmpty(
                            this@LoginActivity,
                            user_email_tv.text.toString(),
                            getString(R.string.email_not_empty)
                        )
                    } else {
                        get_email_code.setOnClickListener {
                            mViewModel.getEmailCode(user_email_tv.text.toString().trim())
                        }
                    }
                }
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

    override fun startObserve() {
        super.startObserve()
        mViewModel.run {
            loginViewModel.observe(this@LoginActivity, Observer {
                pauseLottieAnim()
                //后面改造用DB存
                SPUtil.saveValue("token", it.token)
                SPUtil.saveValue("isLogin", true)
                SPUtil.saveValue("headImg", it.userHeadImg)
                SPUtil.saveValue("userId", it.userId)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            })
            emailCodeViewModel.observe(this@LoginActivity, Observer {
                pauseLottieAnim()
                code = it.code
            })
        }
    }

    private fun playLottieAnim() {
        lv.visibility = View.VISIBLE
        lv.pauseAnimation()
    }

    private fun pauseLottieAnim() {
        lv.visibility = View.GONE
        lv.playAnimation()
    }
}
