package com.itrocket.union.readingMode.presentation.store

import android.os.Parcelable
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReadingModeArguments(val selectedReadingMode: ReadingModeTab) : Parcelable