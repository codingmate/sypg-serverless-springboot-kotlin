package dev.sypg.app.client.openapi
import dev.sypg.app.client.openapi.res.CalendarClientResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory


@Component
class CalendarClient {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CalendarClient::class.java)
    }

    @Value("\${api.public.calendar.baseUrl}")
    private val BASE_URL: String? = null

    @Value("\${api.public.calendar.encodingKey}")
    private val ENCODING_KEY: String? = null

    @Value("\${api.public.calendar.decodingKey}")
    private val DECODING_KEY: String? = null
    fun getLunarDateByGregorianSolar(sol: LocalDate): CalendarClientResponse {

        val queryString = "solYear=${sol.year}&solMonth=${sol.monthValue}&solDay=${sol.dayOfMonth}&ServiceKey=$ENCODING_KEY"
        val url = "$BASE_URL/getLunCalInfo?$queryString"
        logger.debug("getLunarDateByGregorianSolar url : $url")
        val client = HttpClient(CIO)

        // API 호출 결과를 받아 저장할 변수
        var lunarDate: LocalDate = LocalDate.now()
        var resultCode = ""
        var resultMsg = ""
        runBlocking {
            try {
                val response: HttpResponse = client.get(url)
                val responseBody: String = response.bodyAsText()
                logger.debug(">>>>>>>>>>>>>>> responseBody : $responseBody")

                // XML 파싱을 위한 DocumentBuilderFactory 생성
                val dbFactory = DocumentBuilderFactory.newInstance() // XML 파서 팩토리 생성
                val dBuilder = dbFactory.newDocumentBuilder() // XML 파서 생성
                val xmlInput = ByteArrayInputStream(responseBody.toByteArray(Charsets.UTF_8)) // XML 입력
                val doc: Document = dBuilder.parse(xmlInput) // XML 파싱
                doc.documentElement.normalize() // XML 정규화

                val root = doc.documentElement
                val header = root.getElementsByTagName("header").item(0) as Element
                resultCode = header.getElementsByTagName("resultCode").item(0).textContent
                resultMsg = header.getElementsByTagName("resultMsg").item(0).textContent

                // XML에서 'item' 태그의 첫 번째 항목을 가져옴
                val item = doc.getElementsByTagName("item").item(0) as Element

                val lunDay = item.getElementsByTagName("lunDay").item(0).textContent.toInt()
                val lunMonth = item.getElementsByTagName("lunMonth").item(0).textContent.toInt()
                val lunYear = item.getElementsByTagName("lunYear").item(0).textContent.toInt()
                // LocalDate 객체 생성
                lunarDate = LocalDate.of(lunYear, lunMonth, lunDay)
            } catch (e: Exception) {
                e.printStackTrace()
                // 예외 처리 로직
            } finally {
                client.close()
            }
        }
        return CalendarClientResponse(resultCode, resultMsg, "LUNAR", lunarDate)
    }
}