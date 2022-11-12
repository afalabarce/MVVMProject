package io.github.afalalabarce.mvvmproject.model.utilities

import java.text.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

fun <T>Boolean.iif(ifTrue: () -> T, ifFalse: () -> T): T = if (this) ifTrue() else ifFalse()
fun Int.format(format: String = "#,##0"): String = if (this >= 0) DecimalFormat(
    format,
    DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator= '.'
    }
).format(this) else DecimalFormat("${format}-").format(this)
fun Long.format(format: String = "#,##0"): String = DecimalFormat(
    format,
    DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator= '.'
    }
).format(this)
fun Double.format(format: String = "#,##0.00"): String = DecimalFormat(
    format,
    DecimalFormatSymbols().apply {
        decimalSeparator = ','
        groupingSeparator= '.'
    }
).format(this)
fun Float.format(format: String = "#,##0.00"): String = DecimalFormat(format,
    DecimalFormatSymbols().also { x -> x.decimalSeparator = ',' }.also { x -> x.groupingSeparator= '.' }).format(this)
fun Date?.format(format: String = "dd-MM-yyyy"): String = (this != null).iif({ SimpleDateFormat(format, Locale.getDefault()).format(this!!) } , { "" })
fun Instant?.format(format: String = "dd-MM-yyyy"): String = (this != null).iif( { DateTimeFormatter.ofPattern(format, Locale.getDefault()).format(this@format) }, { "" })
fun CharSequence?.toDate(format: String = "dd-MM-yyyy"): Date? = this.toString().toDate(format)
fun CharSequence?.toInstant(format: String = "dd-MM-yyyy"): Instant? = this.toString().toInstant(format)
fun Calendar.today(): Date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(this.time.format("dd-MM-yyyy")) as Date
fun CharSequence?.toNotNullDate(format: String = "dd-MM-yyyy"): Date = try{
    this?.let { date -> SimpleDateFormat(format, Locale.getDefault()).parse(date.toString())  } ?:
    Calendar.getInstance().time.format(format).toDate()!!
}catch (ex: Exception){
    Calendar.getInstance().time.format(format).toDate()!!
}

fun String?.toDate(format: String = "dd-MM-yyyy"): Date? = try{
    this?.let { SimpleDateFormat(format, Locale.getDefault()).parse(it) }
}catch (ex: Exception){
    null
}

fun String?.toInstant(format: String = "dd-MM-yyyy"): Instant? = try{
    this?.let { LocalDateTime.parse(it, DateTimeFormatter.ofPattern(format, Locale.getDefault())).toInstant(
        ZoneOffset.UTC) }
}catch (ex: Exception){
    null
}



fun String?.toNotNullDate(format: String = "dd-MM-yyyy"): Date = try {
    this?.let { date -> SimpleDateFormat(format, Locale.getDefault()).parse(date) }
        ?: Calendar.getInstance().time.format(format).toDate()!!
}catch (fEx: IllegalArgumentException){
    Calendar.getInstance().time.format("dd-MM-yyyy").toDate()!!
}catch (ex: Exception){
    Calendar.getInstance().time.format(format).toDate()!!
}

infix fun Date.plusDays(days: Int): Date = Calendar.getInstance().apply {
    time = this@plusDays
    add(Calendar.DAY_OF_MONTH, days)
    add(Calendar.SECOND, 86399)
}.time

infix fun Instant.plusDays(days: Long): Instant = this.plus(days , ChronoUnit.DAYS)

infix fun Date.plusMonth(month: Int): Date = Calendar.getInstance().apply {
    time = this@plusMonth
    add(Calendar.MONTH, month)
}.time

infix fun Instant.plusMonth(month: Long): Instant = this.plus(month , ChronoUnit.MONTHS)

fun Date.getMondayOfWeek(): Date = Calendar.getInstance().apply {
    time = this@getMondayOfWeek
    add(Calendar.DAY_OF_WEEK, this.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY)
}.time

fun Instant.getMondayOfWeek(): Instant = this.plus((DayOfWeek.MONDAY.value - this.atZone(ZoneOffset.UTC).dayOfWeek.value).toLong() , ChronoUnit.DAYS)

fun Date.getFirstDayOfMonth(): Date = Calendar.getInstance().apply {
    time = this@getFirstDayOfMonth
    set(Calendar.DAY_OF_MONTH, 1)
}.time

fun Instant.getFirstDayOfMonth(): Instant = this.atZone(ZoneOffset.UTC).withDayOfMonth(1).toInstant()