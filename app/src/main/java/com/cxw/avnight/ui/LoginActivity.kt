package com.cxw.avnight.ui


import android.content.Intent
import android.view.View
import android.widget.CompoundButton
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

class LoginActivity : BaseVMActivity<LoginViewModel>(), CompoundButton.OnCheckedChangeListener {

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java
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
            R.id.boy -> sex = "男"
            R.id.girl -> sex = "女"

        }
    }


    override fun initData() {
        get_email_code.setOnClickListener {
            if (!user_email_tv.text.isNotBlank()) {
                BaseTools.checkEtIsNotEmpty(
                    this@LoginActivity,
                    user_email_tv.text.toString(),
                    getString(R.string.email_not_empty)
                )
            } else {
                if (!BaseTools.isEmail(user_email_tv.text.toString())) {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.email_format_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    mViewModel.getEmailCode(user_email_tv.text.toString().trim())

                }
            }
        }
        login.setOnClickListener {
            when (login.text.toString()) {
                getString(R.string.login) -> {
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
                    userInfo["password"] = password_tv.text.toString()
                    mViewModel.login(
                        RequestBody.create(
                            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
                            Gson().toJson(userInfo)
                        )
                    )
                }
                getString(R.string.register) -> {
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
                    if (password_tv.text.toString() != reply_password_tv.text.toString()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.two_passwords_are_inconsistent),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        return@setOnClickListener
                    }
                    if (code != input_email_code_tv.text.toString().trim()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.email_code_error),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                    userInfo["name"] = user_name_tv.text.toString()
                    userInfo["email"] = user_email_tv.text.toString().trim()
                    userInfo["password"] = password_tv.text.toString()
                    userInfo["code"] = input_email_code_tv.text.toString().trim()
                    userInfo["sex"] = sex
                    mViewModel.registered(
                        RequestBody.create(
                            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
                            Gson().toJson(userInfo)
                        )
                    )
                }
                getString(R.string.sure) -> {
                    userInfo.clear()
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
                    if (code != input_email_code_tv.text.toString().trim()) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.email_code_error),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    userInfo["email"] = user_email_tv.text.toString().trim()
                    userInfo["password"] = password_tv.text.toString()
                    userInfo["code"] = input_email_code_tv.text.toString().trim()
                    mViewModel.Updatepassword(
                        RequestBody.create(
                            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
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
            } else {
                input_email_code_layout.visibility = View.VISIBLE
                reply_user_password_layout.visibility = View.VISIBLE
                forgetPassword = true
                login.text = getString(R.string.sure)
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

    override fun RequestLoading(isLoading: Boolean) {
        super.RequestLoading(isLoading)
        playLottieAnim()
    }

    override fun startObserve() {
        super.startObserve()
        pauseLottieAnim()
        mViewModel.run {

            loginViewModel.observe(this@LoginActivity, Observer {
                //后面改造用DB存
                SPUtil.saveValue("token", it.token)
                SPUtil.saveValue("isLogin", true)
                SPUtil.saveValue("headImg", it.userHeadImg)
                SPUtil.saveValue("userId", it.userId)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()

            })
            emailCodeViewModel.observe(this@LoginActivity, Observer {
                get_email_code.text = getString(R.string.send_email_success)
                code = it.code
            })
            registeredViewModel.observe(this@LoginActivity, Observer {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.register_success),
                    Toast.LENGTH_LONG
                ).show()
                userInfo["email"] = it.email
                userInfo["password"] = it.password
                mViewModel.login(
                    RequestBody.create(
                        okhttp3.MediaType.parse("application/json;charset=UTF-8"),
                        Gson().toJson(userInfo)
                    )
                )
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        pauseLottieAnim()
    }

    private fun playLottieAnim() {
        lv.setAnimation("net_work_loading_lottie.json")
        lv.repeatCount = 100
        lv.playAnimation()
        lv.visibility = View.VISIBLE
    }

    private fun pauseLottieAnim() {
        lv.visibility = View.GONE
        lv.setAnimation("net_work_loading_lottie.json")
        lv.repeatCount = 100
        lv.pauseAnimation()

    }
}
