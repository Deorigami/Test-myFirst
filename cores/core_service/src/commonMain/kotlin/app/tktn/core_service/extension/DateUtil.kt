package app.tktn.core_service.extension

import co.touchlab.kermit.Logger
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

object DateUtil {

    /**
     * Converts an ISO 8601 date string (e.g. 2026-01-26T13:21:20Z) to a formatted string.
     * Supports standard patterns internally: yyyy, MM, MMM, MMMM, dd, HH, mm, ss, a.
     * Note: 'MMM' and 'MMMM' use English locale.
     */
    fun format(
        isoString: String,
        pattern: String = "dd MMM yyyy",
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): String {
        return try {
            val instant = Instant.parse(isoString)
            val date = instant.toLocalDateTime(timeZone)
            formatWithPattern(date, pattern)
        } catch (e: Exception) {
            Logger.e("DateUtil") { "Error formatting date: ${e.message}" }
            isoString
        }
    }

    private fun formatWithPattern(date: LocalDateTime, pattern: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < pattern.length) {
            val c = pattern[i]
            var count = 1
            while (i + count < pattern.length && pattern[i + count] == c) {
                count++
            }

            when (c) {
                'y' -> {
                    val year = date.year.toString()
                    if (count == 2) sb.append(year.takeLast(2))
                    else sb.append(year)
                }
                'M' -> {
                    when (count) {
                        1 -> sb.append(date.monthNumber)
                        2 -> sb.append(date.monthNumber.toString().padStart(2, '0'))
                        3 -> sb.append(date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }) // Jan, Feb
                        4 -> sb.append(date.month.name.lowercase().replaceFirstChar { it.uppercase() }) // January
                        else -> sb.append(date.monthNumber.toString().padStart(2, '0'))
                    }
                }
                'd' -> {
                    if (count == 2) sb.append(date.dayOfMonth.toString().padStart(2, '0'))
                    else sb.append(date.dayOfMonth)
                }
                'H' -> { // 0-23
                    if (count == 2) sb.append(date.hour.toString().padStart(2, '0'))
                    else sb.append(date.hour)
                }
                'h' -> { // 1-12
                    val h = if (date.hour % 12 == 0) 12 else date.hour % 12
                    if (count == 2) sb.append(h.toString().padStart(2, '0'))
                    else sb.append(h)
                }
                'm' -> { // minute
                    if (count == 2) sb.append(date.minute.toString().padStart(2, '0'))
                    else sb.append(date.minute)
                }
                's' -> {
                    if (count == 2) sb.append(date.second.toString().padStart(2, '0'))
                    else sb.append(date.second)
                }
                'a' -> {
                    sb.append(if (date.hour < 12) "AM" else "PM")
                }
                '\'' -> { // Text literal
                    // Simple quote handling: skip the quote
                }
                else -> {
                    repeat(count) { sb.append(c) }
                }
            }
            i += count
        }
        return sb.toString()
    }
}

/**
 * Extension function to easily format a date string.
 */
fun String.toFormattedDate(
    pattern: String = "dd MMM yyyy",
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    return DateUtil.format(this, pattern, timeZone)
}
