package dev.sypg.app.dto

import java.time.LocalDate

data class DateResponse( val date: LocalDate,
                         val resultCode: String,
                         val resultMessage: String
)