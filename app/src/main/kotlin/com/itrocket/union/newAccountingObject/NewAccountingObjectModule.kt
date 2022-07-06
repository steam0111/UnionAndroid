package com.itrocket.union.newAccountingObject

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectArguments
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectStore
import com.itrocket.union.newAccountingObject.presentation.store.NewAccountingObjectStoreFactory
import com.itrocket.union.newAccountingObject.presentation.view.NewAccountingObjectComposeFragment.Companion.NEW_ACCOUNTING_OBJECT_ARGUMENT
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NewAccountingObjectModule {
    val NEWACCOUNTINGOBJECT_VIEW_MODEL_QUALIFIER = named("NEWACCOUNTINGOBJECT_VIEW_MODEL")

    val module = module {
        viewModel(NEWACCOUNTINGOBJECT_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<NewAccountingObjectStore>() {
                parametersOf(args.getParcelable<NewAccountingObjectArguments>(NEW_ACCOUNTING_OBJECT_ARGUMENT))
            })
        }

        factory { (args: NewAccountingObjectArguments) ->
            NewAccountingObjectStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args
            ).create()
        }
    }
}