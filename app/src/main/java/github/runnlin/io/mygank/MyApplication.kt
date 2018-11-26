package github.runnlin.io.mygank

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
class MyApplication: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var INSTANCE:MyApplication? = null
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        context = this
    }

}