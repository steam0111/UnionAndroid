package com.itrocket.union.readingMode.presentation.store

import android.os.Parcelable
import com.itrocket.union.readingMode.presentation.view.ReadingModeTab
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReadingModeResult(val readingModeTab: ReadingModeTab) : Parcelable