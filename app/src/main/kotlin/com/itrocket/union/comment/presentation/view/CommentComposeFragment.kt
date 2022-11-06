package com.itrocket.union.comment.presentation.view

import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.itrocket.core.base.AppInsets
import com.itrocket.core.base.BaseComposeBottomSheet
import com.itrocket.union.R
import com.itrocket.union.comment.CommentModule
import com.itrocket.union.comment.presentation.store.CommentStore

class CommentComposeFragment :
    BaseComposeBottomSheet<CommentStore.Intent, CommentStore.State, CommentStore.Label>(
        CommentModule.COMMENT_VIEW_MODEL_QUALIFIER
    ) {

    override val backgroundColor: Int
        get() = ContextCompat.getColor(requireContext(), R.color.bottom_sheet_background)

    override fun renderState(
        state: CommentStore.State,
        composeView: ComposeView,
        appInsets: AppInsets
    ) {
        composeView.setContent {
            CommentScreen(
                state = state,
                appInsets = appInsets,
                onCloseClickListener = {
                    accept(CommentStore.Intent.OnCloseClicked)
                },
                onTextChanged = {
                    accept(CommentStore.Intent.OnTextChanged(it))
                },
                onAcceptClickListener = {
                    accept(CommentStore.Intent.OnAcceptClicked)
                }
            )
        }
    }

    companion object {
        const val COMMENT_ARGS = "comment args"
        const val COMMENT_RESULT_LABEL = "comment result"
        const val COMMENT_RESULT_CODE = "comment code"
    }
}