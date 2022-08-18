package com.itrocket.union.unionPermissions

import com.itrocket.union.unionPermissions.domain.UnionPermissionsInteractor
import org.koin.dsl.module

object UnionPermissionsModule {
    val module = module {
        factory {
            UnionPermissionsInteractor(
                get()
            )
        }
    }
}