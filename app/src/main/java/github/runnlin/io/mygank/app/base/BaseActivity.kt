package github.runnlin.io.mygank.app.base

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import github.runnlin.io.mygank.utils.ConstantUtils

/**
 * @author Runnlin
 * @date 2018/11/23/0023.
 */
@SuppressLint("Registered")
class BaseActivity(var context: Context) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        context = this
        registerBroadcast()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.repeatCount == 0) {
            this.finish()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    private lateinit var receiver:MyReceiver

    private fun registerBroadcast() {
        receiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(ConstantUtils.EXIT_APP)
        context.registerReceiver(receiver, filter)
    }

    class MyReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == ConstantUtils.EXIT_APP) {
                Log.d("zrl", "Log out")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}