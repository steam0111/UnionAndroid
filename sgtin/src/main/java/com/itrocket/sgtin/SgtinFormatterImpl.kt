package com.itrocket.sgtin

import android.util.Log
import androidx.core.text.isDigitsOnly
import java.math.BigInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SgtinFormatterImpl : SgtinFormatter {

    /**
     * Метод для преобразования Штрих-кода и серийного номера в EPC RFID
     * Путь преобразования:
     * Проверить корректность Штрих-кода и серийного номера
     * Привести штрих-код к стандарту GTIN (14 символов)
     * Отделить префикс компании и привести ее к бинарному виду (32 бита)
     * Отформатировать биты префикса компании (30 битов)
     * Отделить предмет ссылки и привести его к бинарному виду (12 битов)
     * Отформатировать биты предмета ссылки (14 битов)
     * Привести серийный номер к бинарному вида (40 битов)
     * Отформатировать биты серийного номера (38 битов)
     * Соединить хардкодный хедер(8 битов), фильтер(3 бита), разделить(3 бита) с полчившимися бинарными префиксом компании, предмета ссылки и серийного номера
     * Итоговое бинарное представление (всегда 96 бит) приветси в HEX строку
     */
    override suspend fun barcodeToEpcRfid(barcode: String, serialNumber: String): String {
        return withContext(Dispatchers.IO) {
            val isValidBarcode =
                barcode.isDigitsOnly() && barcode.length >= MIN_BARCODE_LENGTH && barcode.length <= MAX_BARCODE_LENGTH //Штрихкод должен состоять из цифр и размер должен быть от 13 до 14

            val isValidSerialNumber =
                serialNumber.isDigitsOnly() && serialNumber.toLong() < MAX_SERIAL_NUMBER_VALUE // Серийный номер так же должен состоять только из цифр и размер числа не должен превышать 274877906943
            if (isValidBarcode && isValidSerialNumber) {
                val gtin =
                    if (barcode.length == MIN_BARCODE_LENGTH) {
                        "$GTIN_PREFIX$barcode"
                    } else {
                        barcode
                    } // Если у штрих-кода длинна 13 символов - нунжно добавить 0, чтобы декодировать всегда одинаковый размер

                val companyPrefix = gtin.substring(
                    COMPANY_PREFIX_START,
                    COMPANY_PREFIX_END
                ) //Префикс компании - 9 чисел - состоит из подстроки штрихкода с первого по десятый символ

                val binaryCompanyPrefix =
                    companyPrefix.toBinary(COMPANY_PREFIX_BITS_COUNT) //Перевод префикса компании в бинарное представление, количество бит всегда 32

                val formattedBinaryCompanyPrefix =
                    binaryCompanyPrefix.substring(
                        startIndex = USELESS_BITS_END,
                        endIndex = binaryCompanyPrefix.length
                    ) // По методичке необходимо отбросить два первых бита у бинарного префикса компании

                val itemReference = gtin.first() + gtin.substring(
                    COMPANY_PREFIX_END,
                    gtin.length - 1
                ) // Предмет ссылки - это остаток штрих-кода после отделения префикса компании

                val binaryItemReference =
                    ITEM_REFERENCE_PREFIX + itemReference.toBinary(ITEM_REFERENCE_BITS_COUNT) //Переводим предмет ссылки в бинарное представление и всегда добавляем в начало два пустых бита, количество бит всегда 14

                val serialNumberBinary =
                    serialNumber.toBinary(SERIAL_NUMBER_BITS_COUNT)// Так же переводим серийный номер в бинарное число, количество бит всегда 40

                val formattedSerialNumberBinary =
                    serialNumberBinary.substring(
                        USELESS_BITS_END,
                        serialNumberBinary.length
                    ) //Откидываем два первых бита ( из методички )

                val epcRfidBinary =
                    HEADER + FILTER + SEPARATOR + formattedBinaryCompanyPrefix + binaryItemReference + formattedSerialNumberBinary //Соединяем постоянные header, filter и separator с получившимися бинарными числами компании префикса, предметы ссылки и серийного номера

                val epcRfid = epcRfidBinary.binaryToRadixNumber(HEX_RADIX)
                    .uppercase() //Переводим полученный бинарник в Hex

                epcRfid
            } else {
                throw IncorrectBarcodeException() //Ошибка валидации штрих-кода и серийного номера
            }
        }
    }

    /**
     * Временный метод, нет смысла расписывать доку, как Рома добавит нормальную формулу - метод удалится
     * Это просто обратная операция методу barcodeToEpcRfid, вся документация находится у этого метода
     */
    override suspend fun epcRfidToBarcode(epcRfid: String): BarcodeSerialNumber {
        return withContext(Dispatchers.IO) {
            val binaryEpcRfid = epcRfid.rfidToBinary()

            val removablePrefix = HEADER + FILTER + SEPARATOR

            val formattedBinaryEpcRfid =
                binaryEpcRfid.substring(removablePrefix.length, binaryEpcRfid.length)

            val binaryBarcode = formattedBinaryEpcRfid.substring(0, BARCODE_FROM_RFID_BITS_COUNT)
            val binarySerialNumber = formattedBinaryEpcRfid.substring(
                BARCODE_FROM_RFID_BITS_COUNT,
                formattedBinaryEpcRfid.length
            )

            val binaryCompanyPrefix = binaryBarcode.substring(0, COMPANY_PREFIX_BITS_COUNT - 2)
            val binaryItemReference =
                binaryBarcode.substring(COMPANY_PREFIX_BITS_COUNT, binaryBarcode.length)

            val companyPrefix = binaryCompanyPrefix.binaryToRadixNumber(DECIMAL_RADIX)
                .withLeadingZero(COMPANY_PREFIX_LENGTH)
            val itemReference = binaryItemReference.binaryToRadixNumber(DECIMAL_RADIX)
                .withLeadingZero(ITEM_REFERENCE_LENGTH)

            val serialNumber = BigInteger(binarySerialNumber, BINARY_RADIX).toString(DECIMAL_RADIX)

            val firstBarcodeDecimal = if (itemReference.length == 4) {
                itemReference.first().toString()
            } else {
                ""
            }

            val lastBarcodeDecimals = if (itemReference.length == 4) {
                itemReference.substring(1, itemReference.length)
            } else {
                itemReference
            }

            val barcodeWithoutControl =
                firstBarcodeDecimal + companyPrefix.toString() + lastBarcodeDecimals

            val controlNumber = calculateControlNumber(barcodeWithoutControl)

            val barcode = barcodeWithoutControl + controlNumber

            BarcodeSerialNumber(serialNumber = serialNumber, barcode = barcode)
        }
    }

    private fun String.withLeadingZero(length: Int) =
        String.format("%" + length + "s", this).replace(" ".toRegex(), "0")

    private fun String.binaryToRadixNumber(radix: Int) =
        BigInteger(this, BINARY_RADIX).toString(radix)

    private fun String.toBinary(len: Int): String {
        val long = toLong()
        return long.toString(BINARY_RADIX).withLeadingZero(len)
    }

    private fun String.rfidToBinary(): String {
        val bigInteger = BigInteger(this, HEX_RADIX).toString(BINARY_RADIX)
        return String.format("%" + RFID_BITS_COUNT + "s", bigInteger).replace(" ".toRegex(), "0")
    }

    private fun calculateControlNumber(barcodeWithoutControl: String): String {
        var evenSum = 0 //Сумма всех чисел на четных позициях в штрих коде
        var oddSum = 0 //Сумма всех чисел на нечетных позицях в штрих коде
        barcodeWithoutControl.reversed()
            .forEachIndexed { index, c -> // reversed потому что в Модуло 10 нумерация идет справа налево
                if ((index + 1) % 2 == 0) {
                    evenSum += c.digitToInt()
                } else {
                    oddSum += c.digitToInt()
                }
            }

        val sum =
            oddSum * 3 + evenSum // По алгоритму Модуло 10 складываем сумму четных чисел с нечетными умноженными на три
        val sumString = sum.toString()

        val lastSumNumber = sumString.last()
        return if (lastSumNumber == '0') { // если последнее число суммы = 0, то контрольное число 0
            0
        } else {
            10 - lastSumNumber.digitToInt() // иначе по формуле Модуло 10 вычитаем из десятки последнюю сумму цифры
        }.toString()
    }

    /**
     * Часть метода которая описана в доке. Не используется пока методичка не будет дописана Ромой
     */

    private suspend fun epcRfidToBarcodeNonready(epcRfid: String): String {
        return withContext(Dispatchers.IO) {
            val binaryEpcRfid = epcRfid.rfidToBinary()

            val removablePrefix = HEADER + FILTER + SEPARATOR

            val formattedBinaryEpcRfid =
                binaryEpcRfid.substring(removablePrefix.length, binaryEpcRfid.length)


            val binaryBarcode = formattedBinaryEpcRfid.substring(0, BARCODE_FROM_RFID_BITS_COUNT)

            val rawBarcode = BigInteger(binaryBarcode, BINARY_RADIX).toString(DECIMAL_RADIX)
            val barcode = when {
                rawBarcode.length == MAX_BARCODE_LENGTH && rawBarcode.startsWith(GTIN_PREFIX) -> rawBarcode.substring(
                    1,
                    MAX_BARCODE_LENGTH
                )
                else -> rawBarcode
            }
            barcode
        }
    }

    companion object {

        //Barcode to epc rfid const
        private const val MIN_BARCODE_LENGTH = 13
        private const val MAX_BARCODE_LENGTH = 14
        private const val COMPANY_PREFIX_BITS_COUNT = 32
        private const val ITEM_REFERENCE_BITS_COUNT = 12
        private const val SERIAL_NUMBER_BITS_COUNT = 40
        private const val COMPANY_PREFIX_START = 1
        private const val COMPANY_PREFIX_END = 10
        private const val MAX_SERIAL_NUMBER_VALUE = 274877906943L
        private const val ITEM_REFERENCE_PREFIX = "00"
        private const val USELESS_BITS_END = 2

        //Epc rfid to barcode
        private const val BARCODE_FROM_RFID_BITS_COUNT = 44
        private const val RFID_BITS_COUNT = 96
        private const val ITEM_REFERENCE_LENGTH = 4
        private const val COMPANY_PREFIX_LENGTH = 9

        //basic constants
        private const val GTIN_PREFIX = "0"
        private const val BINARY_RADIX = 2
        private const val HEX_RADIX = 16
        private const val DECIMAL_RADIX = 10
        private const val HEADER = "00110000"
        private const val FILTER = "001"
        private const val SEPARATOR = "011"
    }
}