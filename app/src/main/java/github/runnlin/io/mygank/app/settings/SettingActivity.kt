package github.runnlin.io.mygank.app.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.runnlin.io.mygank.R

/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.setting_fragment, SettingFragment())
            .commit()

        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData() {
    }

}