package github.runnlin.io.mygank.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import github.runnlin.io.mygank.R
import github.runnlin.io.mygank.app.Android.AndroidFragment
import github.runnlin.io.mygank.app.gallery.GalleryFragment
import github.runnlin.io.mygank.app.settings.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var androidFragment: Fragment? = null
    private var galleryFragment: Fragment? = null
    private var currentFragment: Fragment? = Fragment()
//    private lateinit var kv:MMKV
//    val rxPermission: RxPermissions = RxPermissions(this)
//    var granted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        initView()
        initData()
    }

    private fun initView() {

        androidFragment = AndroidFragment()
        galleryFragment = GalleryFragment()
    }

    @SuppressLint("CheckResult")
    private fun initData() {
        //quest permission
//        rxPermission
//            .request()
//            .subscribe {
//                if (!granted) {
//                    //Oups persmission denied
//                    Toast.makeText(this, "没有权限不保证会发生什么事", Toast.LENGTH_LONG).show()
//                }
//            }
//        MMKV.initialize(this)
//        kv = MMKV.defaultMMKV()
//        if (kv.containsKey("isHappy")) {
//            tv_main.append("\nWhat a happy guy it is!")
//        }
    }

    private fun switchFragment(targetFragment: Fragment): FragmentTransaction {

        val transaction =
            supportFragmentManager.beginTransaction()

        if (currentFragment != targetFragment) {

            if (!targetFragment.isAdded) {
                if (currentFragment != null) {
                    transaction.hide(currentFragment!!)
                }
                transaction.add(
                    R.id.tabs, targetFragment,
                    targetFragment.javaClass.name
                ).commit()
            } else {
                transaction
                    .hide(currentFragment!!)
                    .show(targetFragment).commit()
            }

            currentFragment = targetFragment
        }

        return transaction
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer_layout.closeDrawer(Gravity.START)
        when (item.itemId) {
            R.id.nav_android -> {
                // Handle the camera action
                toolbar.title = "My Android!"
                switchFragment(androidFragment!!)
            }
            R.id.nav_gallery -> {
                toolbar.title = "My Girls!"
                switchFragment(galleryFragment!!)
            }
            R.id.nav_share -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
