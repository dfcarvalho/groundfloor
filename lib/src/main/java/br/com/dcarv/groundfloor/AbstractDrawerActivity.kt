package br.com.dcarv.groundfloor

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View

/**
 * Base Activity with a Drawer Navigation
 *
 * @param layoutId: id of the layout resource to be used as the activity's content inside the rootId
 * @param navigationMenuId: if of the menu resource containing the bottom menu options
 * @param titleId: id of the activity's title string resource
 * @param navigationViewHeaderId: id of the layout resource for the drawer's header
 * @param themeId: optional, defaults to app's theme
 *
 * @author Danilo Carvalho
 */
abstract class AbstractDrawerActivity(layoutId: Int,
                                      navigationMenuId: Int,
                                      titleId: Int = R.string.app_name,
                                      navigationViewHeaderId: Int = R.layout.nav_header_main,
                                      themeId: Int? = null) : AbstractBaseActivity(layoutId, titleId, rootId = R.layout.activity_drawer_layout, themeId = themeId), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawer: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var navHeaderView: View

    private val navHeaderId = navigationViewHeaderId
    private val navMenuId = navigationMenuId

    override fun config() {
        super.config()

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, getNavigationDrawerOpenStringId(), getNavigationDrawerCloseStringId())
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navView = findViewById(R.id.nav_view)
        navHeaderView = LayoutInflater.from(this).inflate(navHeaderId, null)
        navView.addHeaderView(navHeaderView)
        navView.inflateMenu(navMenuId)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        presentSection(item.itemId)

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Called when one of the navigation items is clicked
     *
     * @param id: id of item that was clicked
     */
    protected abstract fun presentSection(id: Int)

    /**
     * Returns the string id for the nacigation drawer open state
     */
    protected abstract fun getNavigationDrawerOpenStringId(): Int

    /**
     * Returns the string id for the nacigation drawer close state
     */
    protected abstract fun getNavigationDrawerCloseStringId(): Int
}