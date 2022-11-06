package com.itrocket.union.comment.presentation.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentArguments(val entityId: String, val comment: String) : Parcelable