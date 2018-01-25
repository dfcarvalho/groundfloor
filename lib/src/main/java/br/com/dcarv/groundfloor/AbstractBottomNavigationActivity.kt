package br.com.dcarv.groundfloor

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem

/**
 * Base Activity with a Bottom Navigation Bar
 *
 * @param layoutId: id of the layout resource to be used as the activity's content inside the rootId
 * @param navigationMenuId: if of the menu resource containing the bottom menu options
 * @param titleId: id of the activity's title string resource
 * @param themeId: optional, defaults to app's theme
 *
 * @author Danilo Carvalho
 */
abstract class AbstractBottomNavigationActivity(
    layoutId: Int,
    navigationMenuId: Int,
    titleId: Int = R.string.app_name,
    themeId: Int? = null
) : AbstractBaseActivity(
    layoutId,
    titleId,
    rootId = R.layout.activity_bottom_navigation,
    themeId = themeId
), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private val navMenuId = navigationMenuId

    override fun config() {
        super.config()

        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setOnNavigationItemReselectedListener { /* do nothing */ }
        bottomNavigationView.inflateMenu(navMenuId)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presentSection(item.itemId)
        return true
    }

    /**
     * Called when one of the navigation items is clicked
     *
     * @param id: id of item that was clicked
     */
    protected abstract fun presentSection(id: Int)
}