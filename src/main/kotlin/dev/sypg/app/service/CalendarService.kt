package dev.sypg.app.service

import dev.sypg.app.handler.AwsLambdaHandler
import dev.sypg.app.service.client.openapi.CalendarClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CalendarService {

    companion object {
        val logger = LoggerFactory.getLogger(CalendarService::class.java)
    }
    private val calendarClient by lazy { AwsLambdaHandler.applicationContext.getBean(CalendarClient::class.java) }
    fun solarDateToLunarDate(solarDate: LocalDate): LocalDate {
        val response = calendarClient.getLunarDateByGregorianSolar(solarDate)
        logger.info(response.toString())
        return calendarClient.getLunarDateByGregorianSolar(solarDate).date
    }
}