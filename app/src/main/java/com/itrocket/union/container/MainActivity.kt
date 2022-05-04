package com.itrocket.union.container

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.itrocket.core.base.AppInsetsStateHolder
import com.itrocket.union.R
import com.itrocket.utils.setGraph
import org.koin.android.ext.android.inject
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val appInsetsHolder by inject<AppInsetsStateHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment =
            findViewById<FragmentContainerView>(R.id.mainActivityNavHostFragment).getFragment<NavHostFragment>()

        findViewById<View>(R.id.mainActivityNavHostFragment).setOnApplyWindowInsetsListener { v, insets ->
            val windowInsets = WindowInsetsCompat.toWindowInsetsCompat(insets)
            appInsetsHolder.update(windowInsets)
            insets
        }

        navHostFragment.setGraph(R.navigation.main, R.id.documentsMenu)
    }
}