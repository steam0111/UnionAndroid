package com.itrocket.union.comment.presentation.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentResult(val entityId: String, val comment: String) : Parcelable