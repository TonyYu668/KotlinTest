package com.tony.demo.kotlintest

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivityForResult

class MainActivity : AppCompatActivity() {

    private var phone: String by ByPreference(this, "phone", "")
    private var password: String by ByPreference(this, "password", "")
    private var remember: Boolean by ByPreference(this, "remember", false)
    private val mRequestCode = 66

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        content_main_text.text = ""
        content_main_clear.visibility = View.INVISIBLE

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        content_main_clear.setOnClickListener {
            phone = ""
            password = ""
            remember = false
            content_main_text.text = ""
            content_main_clear.visibility = View.INVISIBLE
        }


        btn_content_main_button.setOnClickListener {
            startActivityForResult<LoginActivity>(mRequestCode, "button" to "login(the login text from mainActivity)")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == mRequestCode && data != null) {
            var content: String =
                "用户名：" + phone + "\n" + "（来自 Preference，" + "\n" + "如果没有信息，则是没有选中记住密码）" + "\n" +
                        "密码：" + data.getStringExtra("password") + "\n" + "（来自startActivityForResult）"
            content_main_text.text = content
            content_main_clear.visibility = View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
