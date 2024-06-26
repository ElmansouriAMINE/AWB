package com.example.testoo.UI.SmsBroadCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadCastReceiver : BroadcastReceiver() {

    var smsBroadCastReceiverListener : SmsBroadCastReceiverListener ?= null
    override fun onReceive(context: Context?, intent: Intent?) {
        if(SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){
           val extras = intent.extras
            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when(smsRetrieverStatus.statusCode){
                CommonStatusCodes.SUCCESS ->{
                    val messageIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsBroadCastReceiverListener?.onSucces(messageIntent)

                }
                CommonStatusCodes.TIMEOUT ->{

                    smsBroadCastReceiverListener?.onFailure()

                }
            }
        }
    }


    interface  SmsBroadCastReceiverListener{
        fun onSucces(intent : Intent?)
        fun onFailure()

    }
}

//class SmsBroadCastReceiver : BroadcastReceiver() {
//    var otpReceiver: OtpReceiver? = null
//
//    override fun onReceive(context: Context, intent: Intent) {
//        if (intent.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
//            val extras = intent.extras
//            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
//            when (smsRetrieverStatus.statusCode) {
//                CommonStatusCodes.SUCCESS -> {
//                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
//                    otpReceiver?.onOtpReceived(message)
//                }
//                CommonStatusCodes.TIMEOUT -> {
//                    otpReceiver?.onOtpTimeout()
//                }
//            }
//        }
//    }
//
//    interface OtpReceiver {
//        fun onOtpReceived(otp: String)
//        fun onOtpTimeout()
//    }
//}
//
