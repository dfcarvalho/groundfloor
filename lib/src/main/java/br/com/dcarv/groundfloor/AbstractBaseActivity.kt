package br.com.dcarv.groundfloor

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Base Activity
 *
 * @param layoutId: id of the layout resource to be used as the activity's content inside the rootId
 * @param titleId: id of the activity's title string resource
 * @param rootId: optional, id of the layout resource to be used as the activity's content,
 * defaults to library's R.layout.activity_layout. Custom layout should contain an item with id
 * activity_content where the layoutId resource will be inflated
 * @param themeId: optional, defaults to app's theme
 *
 * @author Danilo Carvalho
 */
abstract class AbstractBaseActivity(private val layoutId: Int,
                                    private val titleId: Int = R.string.app_name,
                                    private val rootId: Int = R.layout.activity_layout,
                                    private val themeId: Int? = null) : AppCompatActivity() {
    // View references
    var appbar: AppBarLayout? = null
    var toolbar: Toolbar? = null
    var fab: FloatingActionButton? = null
    var contentViewGroup: ViewGroup? = null
    var loadingViewGroup: ViewGroup? = null

    // Defines whether to show the ProgressBar or not
    var isLoading: Boolean = false
        set(value) {
            field = value
            loadingViewGroup?.visibility = if (value) View.VISIBLE else View.GONE
        }

    // Defines whether to show the FAB or not
    var isFabVisible: Boolean = false
        set(value) {
            field = value
            fab?.visibility = if (value) View.VISIBLE else View.GONE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config()
    }

    // Inflates layout and sets up view references
    protected open fun config() {
        themeId?.let { setTheme(it) }
        setContentView(rootId)

        appbar = findViewById(R.id.activity_appbar)
        toolbar = findViewById(R.id.activity_toolbar)
        fab = findViewById(R.id.fab)
        loadingViewGroup = findViewById(R.id.activity_loading)
        contentViewGroup = findViewById(R.id.activity_content)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(titleId)

        val context = if (themeId == null) this else ContextThemeWrapper(this, themeId)
        LayoutInflater.from(context).inflate(layoutId, contentViewGroup, true)

        hideLoading()
    }

    // Show ProgressBar
    fun showLoading() {
        isLoading = true
    }

    // Hides ProgressBar
    fun hideLoading() {
        isLoading = false
    }

    // Show FAB and defines its image resource
    fun showFab(iconId: Int? = null) {
        if (iconId != null) {
            fab?.setImageResource(iconId)
        }
        isFabVisible = true
    }

    // Hides FAB
    fun hideFab() {
        isFabVisible = false
    }
}