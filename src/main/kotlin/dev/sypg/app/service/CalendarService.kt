package dev.sypg.app.service

import dev.sypg.app.client.publicapi.CalendarClient
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CalendarService(private val client: CalendarClient) {
    fun getLunarDateBySolarDate(solarDate: LocalDate): LocalDate {
        val solYear = solarDate.year.toString()
        val solMonth = (if (solarDate.monthValue < 10) "0" else "") + solarDate.monthValue
        val solDay = (if (solarDate.dayOfMonth < 10) "0" else "") + solarDate.dayOfMonth

        val response = client.getLunCalInfo(
            solYear,
            solMonth,
            solDay,
            "2oZgjmX5voitj34%2FUianpw77PCv41zm5IQ8NDXUsDERsTAve9wzr1IDCQm7aJ2wtPMp5XH1Dy1b%2F3Rr0VQH0rw%3D%3D"
        )
        val item = response.body.items.item

        val lunYear = item.lunYear
        val lunMonth = item.lunMonth
        val lunDay = item.lunDay

        return LocalDate.of(lunYear, lunMonth, lunDay)
    }

    fun getSolarDateByLunarDate(lunarDate: LocalDate): LocalDate {
        val lunYear = lunarDate.year.toString()
        val lunMonth = (if (lunarDate.monthValue < 10) "0" else "") + lunarDate.monthValue
        val lunDay = (if (lunarDate.dayOfMonth < 10) "0" else "") + lunarDate.dayOfMonth

        val response = client.getSolCalInfo(
            lunYear,
            lunMonth,
            lunDay,
            "2oZgjmX5voitj34%2FUianpw77PCv41zm5IQ8NDXUsDERsTAve9wzr1IDCQm7aJ2wtPMp5XH1Dy1b%2F3Rr0VQH0rw%3D%3D"
        )
        val item = response.body.items.item

        val solYear = item.solYear
        val solMonth = item.solMonth
        val solDay = item.solDay

        return LocalDate.of(solYear, solMonth, solDay)
    }
}