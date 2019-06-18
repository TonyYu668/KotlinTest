package com.tony.demo.kotlintest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

/**
 * description: 登录界面
 *
 * @author tony
 */
class LoginActivity : AppCompatActivity() {
    private var phone: String by ByPreference(this, "phone", "")
    private var password: String by ByPreference(this, "password", "")
    private var remember: Boolean by ByPreference(this, "remember", false)
    private var rightPassword = "666666"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_login)
        activity_login_btn.text = intent.getStringExtra("button")
        if (remember) {
            activity_login_phone_et.setText(phone)
            activity_login_password_et.setText(password)
            activity_login_checkBox.isChecked = remember
        }
        initListener()

    }

    private fun initListener() {
        activity_login_checkBox.setOnCheckedChangeListener { buttonView, isChecked -> remember = isChecked }
        activity_login_btn.setOnClickListener { goLogin() }
        activity_login_phone_et.addTextChangedListener(HideTextWatcher(activity_login_phone_et))
        activity_login_password_et.addTextChangedListener(HideTextWatcher(activity_login_password_et))
    }

    private fun goLogin() {
        val phone = activity_login_phone_et.text.toString()
        if (phone.isBlank() || phone.length < 11) {
            toast("请输入正确的手机号")
            return
        }

        Log.d("YT", activity_login_password_et.text.toString())
        if (activity_login_password_et.text.toString() != rightPassword) {
            toast("请输入正确的密码")
            return
        } else {
            loginSuccess()
        }

    }

    private fun loginSuccess() {
        if (remember) {
            phone = activity_login_phone_et.text.toString()
            password = activity_login_password_et.text.toString()
        }
        alert("手机号码${activity_login_phone_et.text}，验证成功", "验证成功") {
            positiveButton("确定登录") {
                val intent = Intent().putExtra("password", password)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            negativeButton("取消") { }
        }.show()

    }


    private inner class HideTextWatcher(private val mView: EditText) : TextWatcher {
        private val mMaxLength: Int = ViewUtil.getMaxLength(mView)
        private var mStr: CharSequence? = null

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            mStr = s
        }

        override fun afterTextChanged(s: Editable) {
            if (mStr.isNullOrEmpty())
                return
            if (mStr!!.length == 11 && mMaxLength == 11 || mStr!!.length == 6 && mMaxLength == 6) {
                ViewUtil.hideInputMethod(this@LoginActivity, mView)
            }
        }
    }
}