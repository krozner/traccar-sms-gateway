package org.traccar.gateway

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.PendingIntent
import android.content.Context
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import com.simplemobiletools.commons.compose.extensions.getActivity
import com.simplemobiletools.smsmessenger.messaging.getScheduleSendPendingIntent


@Suppress("DEPRECATION")
object GatewayServiceUtil {

    fun isServiceRunning(context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (GatewayService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }

    // @SuppressLint("MissingPermission")
    @SuppressLint("ALL")
    fun sendMessage(context: Context, phone: String, message: String, slot: Int?) {
        val smsManager = SmsManager.getDefault();

//        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
//        val subscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(smsManager.subscriptionId)

        /* val smsManager = if (slot != null) {
            val subscriptionInfo = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slot)
            SmsManager.getSmsManagerForSubscriptionId(subscriptionInfo.subscriptionId)
        } else {
            SmsManager.getDefault()
        } */

        if (message.length > 70) {
            val msgs: ArrayList<String> = smsManager.divideMessage(message)
            smsManager.sendMultipartTextMessage(phone, null, msgs, null, null)
        } else {
            smsManager.sendTextMessage(phone, null, message, null, null)
        }
    }

}
