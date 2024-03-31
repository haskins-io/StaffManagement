package io.haskins.staffmanagement.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

class DateUtils {

    companion object {

        fun epochToLocalDate(epoch: Long): LocalDate {
            val instant = Instant.ofEpochMilli(epoch)
            return instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun localDateToEpoch(date: LocalDate): Long {
            return date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        }
    }
}