package app.grapheneos.setupwizard.utils

import android.os.Build
import android.provider.Settings
import android.util.ArrayMap
import app.grapheneos.setupwizard.appContext

// To update flag values, run:
// adb shell settings put global setupwizard2_debug_flags flag_1=value_1,flag2,flag_3=value_3
//
// If flag is specified without a value, then it's assumed to have the "true" value.
object DebugFlags {
    fun get(name: String): String? {
        if (!Build.isDebuggable()) {
            return null
        }
        return getMap().get(name)
    }

    fun getBool(name: String): Boolean? {
        return get(name)?.toBooleanStrict()
    }

    private fun getMap(): Map<String, String> {
        val s = Settings.Global.getString(appContext.contentResolver, "setupwizard2_debug_flags")
        if (s == null) {
            return emptyMap()
        }
        val res = ArrayMap<String, String>()
        s.split(",").forEach {
            if (it.contains("=")) {
                val (k, v) = it.split("=")
                res.put(k, v)
            } else {
                res.put(it, "true")
            }
        }
        return res
    }
}
