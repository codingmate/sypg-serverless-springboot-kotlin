package dev.sypg.app.web.rest

import dev.sypg.app.service.CalendarService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = ["http://localhost:3000", "http://sypg-react.s3-website.ap-northeast-2.amazonaws.com"])
class CalendarController(private val calendarService: CalendarService) {

    companion object {
        val logger = LoggerFactory.getLogger(CalendarController::class.java)
    }

    data class Payload(val year: Int, val month: Int, val day: Int)
    @GetMapping("/lunar", produces = ["application/json"])
    fun getLunarDateBySolarDate(payload: Payload): String {
        logger.info("getLunarDateBySolarDate :: $payload")

        val solarDate = calendarService.getLunarDateBySolarDate(LocalDate.of(payload.year, payload.month, payload.day))
        logger.info("solarDate: $solarDate")

        return solarDate.toString()
    }

    @GetMapping("/solar", produces = ["application/json"])
    fun getSolarDateByLunarDate(payload: Payload): String {
        logger.info("getSolarDateByLunarDate :: $payload")

        val lunarDate = calendarService.getSolarDateByLunarDate(LocalDate.of(payload.year, payload.month, payload.day))
        logger.info("lunarDate: $lunarDate")

        return lunarDate.toString()
    }

}