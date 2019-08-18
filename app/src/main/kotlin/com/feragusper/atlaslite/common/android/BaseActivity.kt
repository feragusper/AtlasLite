package com.feragusper.atlaslite.common.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.feragusper.atlaslite.R
import com.feragusper.atlaslite.common.extension.inTransaction
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(makeContentView())
        setSupportActionBar(toolbar as Toolbar)
        addFragment(savedInstanceState)
    }

    internal open fun makeContentView(): Int {
        return R.layout.activity_layout
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment != null) {
            (fragment as BaseFragment).onBackPressed()
        }
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragmentToAdd = fragment()
            if (fragmentToAdd != null) {
                supportFragmentManager.inTransaction {
                    add(
                        R.id.fragmentContainer, fragmentToAdd
                    )
                }
            }
        }
    }

    internal open fun fragment(): BaseFragment? = null
}
