package github.runnlin.io.mygank.app.gallery

import android.app.Activity
import android.media.Image
import android.os.Bundle
import github.runnlin.io.mygank.R
import kotlinx.android.synthetic.main.nav_header_main.view.*

/**
 * @author Runnlin
 * @date 2018/11/22/0022.
 */
class ImageDetail: Activity() {

    private var imgResource:Int? = null

    init {
        imgResource = android.R.drawable.arrow_up_float
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getImageResource(): Int {
        return imgResource!!
    }

}