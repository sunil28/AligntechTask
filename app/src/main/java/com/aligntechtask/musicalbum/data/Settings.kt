package com.aligntechtask.musicalbum.data

import android.content.Context
import android.telephony.TelephonyManager

class Settings constructor(context: Context ) {
    /*Getting country code*/
    val tm =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val countryCodeValue = tm.networkCountryIso

}