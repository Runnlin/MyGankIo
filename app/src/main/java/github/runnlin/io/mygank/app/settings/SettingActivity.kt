package github.runnlin.io.mygank.app.settings

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import github.runnlin.io.mygank.R
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
class SettingActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.setting_fragment, SettingFragment())
            .commit()

        initView()
    }

    private fun initView() {

    }

}