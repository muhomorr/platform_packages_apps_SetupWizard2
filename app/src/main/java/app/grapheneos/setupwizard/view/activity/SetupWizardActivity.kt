package app.grapheneos.setupwizard.view.activity

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import app.grapheneos.setupwizard.R
import com.google.android.setupdesign.GlifLayout
import com.google.android.setupdesign.util.ThemeHelper

/**
 * This is the base activity for all setup wizard activities.
 */
abstract class SetupWizardActivity(
    @LayoutRes val layoutResID: Int? = null,
    @DrawableRes val icon: Int? = null,
    @StringRes val header: Int? = null,
    @StringRes val description: Int? = null,
) : AppCompatActivity() {

    private var glifLayout: GlifLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeHelper.getSuwDefaultTheme(applicationContext))
        ThemeHelper.trySetDynamicColor(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        if (layoutResID == null) return
        // setup view
        setContentView(layoutResID)
        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(
                top = insets.getInsets(WindowInsets.Type.systemBars()).top,
                bottom = insets.getInsets(WindowInsets.Type.navigationBars()).bottom
            )
            insets
        }
        initBaseView()
        bindViews()
        setupActions()
    }

    private fun initBaseView() {
        glifLayout = findViewById(R.id.glif_layout)
        if (icon != null) glifLayout?.icon = getDrawable(icon)
        if (header != null) glifLayout?.setHeaderText(header)
        if (description != null) glifLayout?.setDescriptionText(description)
    }

    @MainThread
    abstract fun bindViews()

    @MainThread
    abstract fun setupActions()
}
