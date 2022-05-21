package com.itrocket.union.container

import com.itrocket.union.container.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ContainerModule {
    val module = module {
        viewModel {
            MainViewModel(get())
        }
    }
}