package com.itrocket.union.comment.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itrocket.core.navigation.GoBackNavigationLabel
import com.itrocket.union.comment.presentation.view.CommentComposeFragment

interface CommentStore :
    Store<CommentStore.Intent, CommentStore.State, CommentStore.Label> {

    sealed class Intent {
        data class OnTextChanged(val text: String) : Intent()
        object OnAcceptClicked : Intent()
        object OnCloseClicked : Intent()
    }

    data class State(
        val text: String = "",
    )

    sealed class Label {
        data class GoBack(override val result: CommentResult? = null) : Label(),
            GoBackNavigationLabel {

            override val resultCode: String
                get() = CommentComposeFragment.COMMENT_RESULT_CODE

            override val resultLabel: String
                get() = CommentComposeFragment.COMMENT_RESULT_LABEL
        }
    }
}