package dev.sypg.app.service.client.openapi.res

import java.time.LocalDate

data class CalendarClientResponse(
    val resultCode: String,
    val resultMsg: String,
    val dateType: String = "SOLAR", // "SOLAR" or "LUNAR"
    val date: LocalDate
)