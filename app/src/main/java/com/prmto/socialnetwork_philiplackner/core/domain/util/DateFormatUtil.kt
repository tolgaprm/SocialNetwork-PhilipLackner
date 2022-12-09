package com.prmto.socialnetwork_philiplackner.core.domain.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {

    fun timestampsToFormattedString(timestamp: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).run {
            format(timestamp)
        }
    }
}