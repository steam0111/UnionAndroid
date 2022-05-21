package com.itrocket.union.container.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.itrocket.core.base.AppInsetsStateHolder
import com.itrocket.union.R
import com.itrocket.union.network.NetworkModule
import com.itrocket.utils.setGraph
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val appInsetsHolder by inject<AppInsetsStateHolder>()

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        findViewById<View>(R.id.mainActivityNavHostFragment).setOnApplyWindowInsetsListener { v, insets ->
            val windowInsets = WindowInsetsCompat.toWindowInsetsCompat(insets)
            appInsetsHolder.update(windowInsets)
            insets
        }

        initObservers()
    }

    private fun initGraph(initFragmentId: Int) {
        val navHostFragment =
            findViewById<FragmentContainerView>(R.id.mainActivityNavHostFragment).getFragment<NavHostFragment>()

        navHostFragment.setGraph(R.navigation.main, initFragmentId)
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.initialScreen.collect {
                initGraph(it.initialScreenId)
            }
        }
    }
}