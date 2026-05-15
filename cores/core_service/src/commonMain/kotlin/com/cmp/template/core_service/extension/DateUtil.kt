package com.cmp.template.core_service.extension
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
fun String.toReadableDate(): String {
    return runCatching {
        val instant = Instant.parse(this)
        val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        "%(local.month.name)s %(local.dayOfMonth)s, %(local.year)s"
            .replace("%(local.month.name)s", local.month.name)
            .replace("%(local.dayOfMonth)s", local.dayOfMonth.toString())
            .replace("%(local.year)s", local.year.toString())
    }.getOrDefault(this)
}
