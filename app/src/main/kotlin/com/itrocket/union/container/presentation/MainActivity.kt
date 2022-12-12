package com.itrocket.union.container.presentation

import android.content.Intent
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
import com.itrocket.nfc.NfcManager
import com.itrocket.union.R
import com.itrocket.union.container.domain.ScannerManager
import com.itrocket.union.intentHandler.IntentHandler
import com.itrocket.utils.setGraph
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val appInsetsHolder by inject<AppInsetsStateHolder>()

    private val scannerManager: ScannerManager by inject()

    private val viewModel by viewModel<MainViewModel>()

    private val nfcManager: NfcManager by inject()

    private val intentHandler: IntentHandler by inject()

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
        nfcManager.init(this)
    }

    private fun initGraph(initFragmentId: Int) {
        val navHostFragment = getNavHostFragment()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            lifecycleScope.launch {
                intentHandler.pushIntent(intent)
            }
        }
    }

    override fun onDestroy() {
        nfcManager.onDestroy()
        super.onDestroy()
    }

    private fun getNavHostFragment() =
        findViewById<FragmentContainerView>(R.id.mainActivityNavHostFragment).getFragment<NavHostFragment>()
}