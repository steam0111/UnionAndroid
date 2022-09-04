package com.itrocket.union.container.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.itrocket.core.base.AppInsetsStateHolder
import com.itrocket.union.R
import com.itrocket.union.container.domain.ScannerManager
import com.itrocket.utils.setGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.interid.scannerclient.domain.reader.ReaderMode
import ru.interid.scannerclient_impl.platform.entry.ServiceEntry
import ru.interid.scannerclient_impl.screen.ServiceEntryManager

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val appInsetsHolder by inject<AppInsetsStateHolder>()

    private val scannerManager: ScannerManager by inject()

    private val viewModel by viewModel<MainViewModel>()

    private val navController by lazy {
        findNavController(R.id.mainActivityNavHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        findViewById<View>(R.id.mainActivityNavHostFragment).setOnApplyWindowInsetsListener { v, insets ->
            val windowInsets = WindowInsetsCompat.toWindowInsetsCompat(insets)
            appInsetsHolder.update(windowInsets)
            insets
        }

        initObservers()
        initGraph(R.id.splash)
    }

    private fun initGraph(initFragmentId: Int) {
        val navHostFragment =
            findViewById<FragmentContainerView>(R.id.mainActivityNavHostFragment).getFragment<NavHostFragment>()

        navHostFragment.setGraph(R.navigation.main, initFragmentId)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            launch {
                viewModel.initialScreen.collect {
                    initGraph(it.initialScreenId)
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return scannerManager.onKeyDown(
            keyCode = keyCode,
            event = event,
            currentDestinationLabel = navController.currentDestination?.label.toString(),
            settingsLabel = getString(R.string.module_settings_label)
        ) ?: super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent) =
        scannerManager.onKeyUp(keyCode) ?: super.onKeyUp(keyCode, event)
}