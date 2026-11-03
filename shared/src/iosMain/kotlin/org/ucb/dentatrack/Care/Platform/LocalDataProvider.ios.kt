package org.ucb.dentatrack.Care.Platform

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale

actual fun todayLocalDate(): String {

    val formatter = NSDateFormatter()

    formatter.dateFormat = "yyyy-MM-dd"
    formatter.locale = NSLocale(
        localeIdentifier = "en_US_POSIX"
    )

    return formatter.stringFromDate(NSDate())
}