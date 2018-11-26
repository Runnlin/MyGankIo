package github.runnlin.io.mygank.app.settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.runnlin.io.mygank.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Runnlin
 * @date 2018/11/22/0022.
 */
class AboutFragment:Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container,false)
    }
}