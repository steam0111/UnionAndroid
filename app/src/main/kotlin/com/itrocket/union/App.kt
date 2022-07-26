package com.itrocket.union

import android.app.Application
import com.itrocket.union.di.Modules.modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        setupLogger()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(EmptyTree())
        }
    }

    private class EmptyTree() : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {}
    }
}