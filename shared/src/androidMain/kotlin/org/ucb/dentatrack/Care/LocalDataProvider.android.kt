package org.ucb.dentatrack.Care.Platform

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun todayLocalDate(): String {
    return SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.US
    ).format(Date())
}