package github.runnlin.io.mygank.app.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import github.runnlin.io.mygank.R


/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
class SettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}
