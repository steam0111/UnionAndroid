package com.itrocket.union.comment

import android.os.Bundle
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itrocket.core.base.BaseViewModel
import com.itrocket.union.comment.domain.CommentInteractor
import com.itrocket.union.comment.presentation.store.CommentArguments
import com.itrocket.union.comment.presentation.store.CommentStore
import com.itrocket.union.comment.presentation.store.CommentStoreFactory
import com.itrocket.union.comment.presentation.view.CommentComposeFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CommentModule {
    val COMMENT_VIEW_MODEL_QUALIFIER = named("COMMENT_VIEW_MODEL")

    val module = module {
        viewModel(COMMENT_VIEW_MODEL_QUALIFIER) { (args: Bundle) ->
            BaseViewModel(get<CommentStore> {
                parametersOf(args.getParcelable<CommentArguments>(CommentComposeFragment.COMMENT_ARGS))
            })
        }

        factory {
            CommentInteractor(coreDispatchers = get())
        }

        factory { (args: CommentArguments) ->
            CommentStoreFactory(
                DefaultStoreFactory,
                get(),
                args
            ).create()
        }
    }
}