package com.itrocket.union

import com.itrocket.union.utils.flow.window
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class FlowWindowOperatorTest {

    @Test
    fun rightResult_ifNoWait() = runBlocking {
        val expected = mutableListOf<List<Int>>()

        for (i in 0..999 step 100) {
            val buffer = mutableListOf<Int>()
            for (j in i..(i + 99)) {
                buffer.add(j)
            }
            expected.add(buffer)
        }

        val inputEmitter = flow {
            for (i in 0..1000) {
                emit(i)
            }
        }

        val inputFlow = inputEmitter.window(100, 0)

        val result = mutableListOf<List<Int>>()

        inputFlow.collect { list ->
            result.add(list)
        }

        result.forEachIndexed { index, list ->
            assertTrue(expected[index].containsAll(list))
        }
    }

    @Test
    fun rightResult_ifWait100Ms() = runBlocking {
        val expected = mutableListOf<List<Int>>()

        for (i in 0..20 step 5) {
            val buffer = mutableListOf<Int>()
            for (j in i..(i + 4)) {
                buffer.add(j)
            }
            expected.add(buffer)
        }

        val inputEmitter = flow {
            for (i in 0..19) {
                emit(i)
                kotlinx.coroutines.delay(100)
            }
        }.flowOn(Dispatchers.IO)

        val inputFlow = inputEmitter.window(1000, 500)

        val result = mutableListOf<List<Int>>()

        inputFlow.collect { list ->
            result.add(list)
        }

        result.forEachIndexed { index, list ->
            assertTrue(expected[index].containsAll(list))
        }
    }

    @Test
    fun rightResult_IfStoreOnlyUniqueValues() = runBlocking {
        val expected = mutableListOf<List<Int>>()

        expected.add(listOf(1, 2, 3))
        expected.add(listOf(1, 2, 3))

        val inputEmitter = flow {
            for (i in 1..3) {
                emit(i)
                emit(i)
            }
            for (i in 1..3) {
                emit(i)
                emit(i)
            }
        }.flowOn(Dispatchers.IO)

        val inputFlow = inputEmitter.window(
            3,
            0,
            bufferOnlyUniqueValues = true
        )

        val result = mutableListOf<List<Int>>()

        inputFlow.collect { list ->
            result.add(list)
        }

        result.forEachIndexed { index, list ->
            assertTrue(expected[index].containsAll(list))
        }
    }
}