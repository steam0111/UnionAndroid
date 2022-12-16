package com.itrocket.sgtin

import android.text.TextUtils
import androidx.core.text.isDigitsOnly
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FormatterTest {

    @Test
    fun barcodeSerialNumber_toEpcRfid_isCorrectWithStartZero() = runBlocking {
        mockkStatic(TextUtils::class)

        val barcode = "04610038292078"
        val serialNumber = "100000000341"
        val result = "302DB7A5C35033D74876E955"

        every { TextUtils.isDigitsOnly(any()) } answers { barcode.all { it.isDigit() } }

        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        assertEquals(
            sgtinFormatter.barcodeToEpcRfid(barcode = barcode, serialNumber = serialNumber),
            result
        )
    }

    @Test
    fun barcodeSerialNumber_toEpcRfid_isCorrectWithoutStartZero() = runBlocking {
        mockkStatic(TextUtils::class)

        val barcode = "4610038292078"
        val serialNumber = "100000000341"
        val result = "302DB7A5C35033D74876E955"

        every { TextUtils.isDigitsOnly(any()) } answers { barcode.all { it.isDigit() } }

        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        assertEquals(
            sgtinFormatter.barcodeToEpcRfid(barcode = barcode, serialNumber = serialNumber),
            result
        )
    }

    @Test
    fun barcodeSerialNumber_toEpcRfid_barcodeIsNotDigitOnly() = runBlocking {
        mockkStatic(TextUtils::class)

        val barcode = "abc10038292078"
        val serialNumber = "100000000341"

        every { TextUtils.isDigitsOnly(any()) } answers { barcode.all { it.isDigit() } }

        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        val exception = assertThrows(IncorrectBarcodeException::class.java) {
            runBlocking {
                sgtinFormatter.barcodeToEpcRfid(barcode = barcode, serialNumber = serialNumber)
            }
        }

        assert(exception is IncorrectBarcodeException)
    }

    @Test
    fun barcodeSerialNumber_toEpcRfid_serialNumberIsNotDigitOnly() = runBlocking {
        mockkStatic(TextUtils::class)

        val barcode = "04610038292078"
        val serialNumber = "abc000000341"

        every { TextUtils.isDigitsOnly(any()) } answers { serialNumber.all { it.isDigit() } }

        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        val exception = assertThrows(IncorrectBarcodeException::class.java) {
            runBlocking {
                sgtinFormatter.barcodeToEpcRfid(barcode = barcode, serialNumber = serialNumber)
            }
        }

        assert(exception is IncorrectBarcodeException)
    }

    @Test
    fun barcodeSerialNumber_toEpcRfid_barcodeIsMoreMaxLength() = runBlocking {
        mockkStatic(TextUtils::class)

        val barcode = "0004610038292078"
        val serialNumber = "100000000341"

        every { TextUtils.isDigitsOnly(any()) } answers { barcode.all { it.isDigit() } }

        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        val exception = assertThrows(IncorrectBarcodeException::class.java) {
            runBlocking {
                sgtinFormatter.barcodeToEpcRfid(barcode = barcode, serialNumber = serialNumber)
            }
        }

        assert(exception is IncorrectBarcodeException)
    }

    @Test
    fun barcodeSerialNumber_toEpcRfid_serialNumberIsMoreMaxValue() = runBlocking {
        mockkStatic(TextUtils::class)

        val barcode = "04610038292078"
        val serialNumber = "284877906943"

        every { TextUtils.isDigitsOnly(any()) } answers { serialNumber.all { it.isDigit() } }

        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        val exception = assertThrows(IncorrectBarcodeException::class.java) {
            runBlocking {
                sgtinFormatter.barcodeToEpcRfid(barcode = barcode, serialNumber = serialNumber)
            }
        }

        assert(exception is IncorrectBarcodeException)
    }

    @Test
    fun epcRfid_toBarcodeSerialNumber_isCorrect() = runBlocking {

        val epcRfid = "302DB7A5C35033D74876E955"
        val result = BarcodeSerialNumber(barcode = "04610038292078", serialNumber = "100000000341")
        val sgtinFormatter: SgtinFormatter = SgtinFormatterImpl()

        assertEquals(result, sgtinFormatter.epcRfidToBarcode(epcRfid))
    }
}