package com.itrocket.union.utils.flow

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive

private sealed class WindowEvent {
    object TimeToEmitCollectionInBuffer : WindowEvent()
    class Value<T>(val value: T) : WindowEvent()
}

fun <T> Flow<T>.window(
    maxBufferSize: Int = 0,
    maxMsWaitTime: Long = 0,
    bufferOnlyUniqueValues: Boolean = false
): Flow<List<T>> {
    val source = this

    return flow {
        val buffer: MutableCollection<T> = if (bufferOnlyUniqueValues) {
            mutableSetOf()
        } else {
            mutableListOf()
        }

        var isCompleted = false

        merge(source.map {
            WindowEvent.Value(it)
        }.onCompletion {
            isCompleted = true
        }, createDelayFlow(maxMsWaitTime) {
            isCompleted
        }).collect {
            when (it) {
                is WindowEvent.Value<*> -> {
                    buffer.add(it.value as T)

                    if (buffer.size >= maxBufferSize) {
                        emit(buffer.toList())
                        buffer.clear()
                    }
                }
                is WindowEvent.TimeToEmitCollectionInBuffer -> {
                    if (buffer.isNotEmpty()) {
                        emit(buffer.toList())
                        buffer.clear()
                    }
                }
            }
        }
    }
}

private fun createDelayFlow(
    maxMsWaitTime: Long = 0,
    isCompleted: () -> Boolean
): Flow<WindowEvent> = flow {
    while (currentCoroutineContext().isActive && maxMsWaitTime != 0L && !isCompleted()) {
        delay(maxMsWaitTime)
        emit(WindowEvent.TimeToEmitCollectionInBuffer)
    }
}