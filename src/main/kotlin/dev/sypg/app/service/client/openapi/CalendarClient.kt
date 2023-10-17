package dev.sypg.app.service.client.openapi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate



@Component
class CalendarClient {

    @Value("\${api.public.calendar.baseUrl}")
    private val BASE_URL: String? = null

    @Value("\${api.public.calendar.encodingKey}")
    private val ENCODING_KEY: String? = null

    @Value("\${api.public.calendar.decodingKey}")
    private val DECODING_KEY: String? = null

    fun gregorianSolarToLunar(sol: LocalDate): LocalDate {

        val queryString = "solYear=${sol.year}&solMonth=${sol.monthValue}&solDay=${sol.dayOfMonth}&ServiceKey=$ENCODING_KEY"
        val url = "$BASE_URL/getLunCalInfo?$queryString"

        val client = HttpClient(CIO)

        // API 호출 결과를 받아 저장할 변수
        var lunarDate: LocalDate = LocalDate.now()

        runBlocking {
            try {
                val response: HttpResponse = client.get(url)

                val responseBody: String = response.bodyAsText()

                // Convert XML to Kotlin object
                val mapper = jacksonObjectMapper().findAndRegisterModules()
                val result = mapper.readValue(responseBody)

                // Convert Kotlin object to LocalDate
                val lunYear = result.body.items.item.lunYear
                val lunMonth = result.body.items.item.lunMonth
                val lunDay = result.body.items.item.lunDay
                lunarDate = LocalDate.of(lunYear, lunMonth, lunDay)

            } catch (e: Exception) {
                e.printStackTrace()
                // 예외 처리 로직
            } finally {
                client.close()
            }
        }
        return lunarDate
    }
    fun lunarTogregorianSolar(lun: LocalDate): LocalDate {

        val queryString =
            "lunYear=${lun.year}&lunMonth=${lun.month}&lunDay=${lun.dayOfMonth}&ServiceKey=${ENCODING_KEY}"
        val url = "${BASE_URL}/getSolCalInfo?$queryString"

        return lun
    }
}