package com.itrocket.nfc

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import kotlin.experimental.and

class NfcManager {

    private var activity: Activity? = null
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    /**
     * Вызывается в onCreate чтобы инициализировать поля
     */
    fun init(activity: Activity) {
        this.activity = activity
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        pendingIntent = PendingIntent.getActivity(
            activity, 0, Intent(
                activity,
                activity.javaClass
            ).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )
    }

    /**
     * Метод для включения режима работы с nfc. Вызывается мануально либо в onResume
     */
    fun enableForegroundNfcDispatch() {
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    /**
     * Метод для выключения режима работы с nfc. Выключается в onPause
     */
    fun disableForegroundNfcDispatch() {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    fun onDestroy() {
        activity = null
        nfcAdapter = null
        pendingIntent = null
    }

    /**
     * Обработка onNewIntent в которой достаются данные из метки,
     * а именно hex строка либо из tagId, либо из списка message'ей
     */
    fun onNfcDataHandled(intent: Intent): String {
        return when (intent.action) {
            NfcAdapter.ACTION_NDEF_DISCOVERED -> handleNdefDiscovered(intent)
            NfcAdapter.ACTION_TAG_DISCOVERED -> handleTagDiscovered(intent)
            else -> throw Exception()
        }
    }

    private fun handleNdefDiscovered(intent: Intent): String {
        val rawMessages =
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.map {
                it as NdefMessage
            }
        val message = rawMessages?.lastOrNull()
        val record = message?.records?.firstOrNull()
        val payload = requireNotNull(record?.payload)
        return ndefPayloadToString(payload)
    }

    private fun handleTagDiscovered(intent: Intent): String {
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val tagId = tag?.id
        val data = tagId?.toHexString()
        return requireNotNull(data)
    }

    private fun ByteArray.toHexString() = joinToString("") {
        String.format("%02x", it)
    }

    /**
     * https://stackoverflow.com/a/64345509
     * Решение сделано, потому что у ndefMessage есть конфликт с современными кодировками. Решает проблему " en" в начале данных из метки
     */
    private fun ndefPayloadToString(payload: ByteArray): String {
        val textEncoding = if (payload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"
        val langCodeLength = payload[0] and 63.toByte()
        return String(
            payload,
            langCodeLength + 1,
            payload.count() - langCodeLength - 1,
            charset(textEncoding)
        )

    }
}