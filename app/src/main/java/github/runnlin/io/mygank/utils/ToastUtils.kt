package github.runnlin.io.mygank.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import github.runnlin.io.mygank.MyApplication
import java.lang.Exception

/**
 * Toast Tools
 * @author Runnlin
 * @date 2018/11/26/0026.
 */
open class ToastUtils {

    companion object {
        private var toast: Toast? = null
        val TAG = Toast::class.java.simpleName
        private var view: View? = null

        @SuppressLint("ShowToast")
        private fun getToast(context: Context) {
            if (toast == null) {
                toast = Toast(context)
            }
            if (view == null) {
                view = Toast.makeText(context, "", Toast.LENGTH_SHORT).view
            }
            toast!!.view = view
        }

        fun showShort(context: Context, msg: CharSequence) {
            showToast(context.applicationContext, msg, Toast.LENGTH_SHORT)
        }

        fun showShort(context: Context, resId: Int) {
            showToast(context.applicationContext, resId, Toast.LENGTH_SHORT)
        }

        fun showLong(context: Context, msg: CharSequence) {
            showToast(context.applicationContext, msg, Toast.LENGTH_SHORT)
        }

        fun showLong(context: Context, resId: Int) {
            showToast(context.applicationContext, resId, Toast.LENGTH_LONG)
        }

        private fun showToast(context: Context, msg: CharSequence, duration: Int) {
            try {
                getToast(context)
                toast!!.setText(msg)
                toast!!.duration = duration
                toast!!.setGravity(Gravity.BOTTOM, 0, 0)
                toast!!.show()
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }

        }

        private fun showToast(context: Context, resId: Int, duration: Int) {
            try {
                if (resId == 0) {
                    return
                }
                getToast(context)
                toast!!.setText(resId)
                toast!!.duration = duration
                toast!!.setGravity(Gravity.CENTER, 0, 0)
                toast!!.show()
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }

        }

        fun cancelToast() {
            if (toast != null) {
                toast!!.cancel()
            }
        }
    }




}