package com.itrocket.union.nfcReader

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.nfcReader.domain.NfcReaderInteractor
import com.itrocket.union.nfcReader.presentation.store.NfcReaderArguments
import com.itrocket.union.nfcReader.presentation.store.NfcReaderStore
import com.itrocket.union.nfcReader.presentation.store.NfcReaderStoreFactory
import com.itrocket.union.nfcReader.presentation.view.NfcReaderComposeFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NfcReaderModule {
    val NFCREADER_VIEW_MODEL_QUALIFIER = named("NFCREADER_VIEW_MODEL")

    val module = module {
        viewModel(NFCREADER_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<NfcReaderStore>() {
                parametersOf(
                    args.getParcelable<NfcReaderArguments>(NfcReaderComposeFragment.NFC_READER_ARGUMENT)
                )
            })
        }

        factory {
            NfcReaderInteractor(get(), get(), get(), get())
        }

        factory { (args: NfcReaderArguments) ->
            NfcReaderStoreFactory(
                DefaultStoreFactory,
                get(),
                get(),
                args,
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            ).create()
        }
    }
}