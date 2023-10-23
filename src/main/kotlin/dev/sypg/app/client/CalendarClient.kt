package dev.sypg.app.client
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/calendar")
class CalendarController(private val calendarService: CalendarService) {

    companion object {
        val logger = LoggerFactory.getLogger(CalendarController::class.java)
    }
    data class SolarDateParams(
        val solYear: Int,
        val solMonth: Int,
        val solDay: Int
    )
    @GetMapping("/lunar")
    fun getLunarDateBySolarDate(params: SolarDateParams): LocalDate {
        logger.info("getLunarDateBySolarDate")
        logger.info("solYear: ${params.solYear}, solMonth: ${params.solMonth}, solDay: ${params.solDay}")

        val solarDate = calendarService.getLunarDateBySolarDate(LocalDate.of(params.solYear, params.solMonth, params.solDay))
        logger.info("solarDate: $solarDate")

        return solarDate
    }

    data class LunarDateParams(
        val lunYear: Int,
        val lunMonth: Int,
        val lunDay: Int
    )
    @GetMapping("/solar")
    fun getSolarDateByLunarDate(params: LunarDateParams): LocalDate {
        logger.info("getSolarDateByLunarDate")
        logger.info("lunYear: ${params.lunYear}, lunMonth: ${params.lunMonth}, lunDay: ${params.lunDay}")

        val lunarDate = calendarService.getSolarDateByLunarDate(LocalDate.of(params.lunYear, params.lunMonth, params.lunDay))
        logger.info("lunarDate: $lunarDate")

        return lunarDate
    }

}
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
@FeignClient(name = "calendarClient", url = "http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService")
interface CalendarClient {
    @GetMapping(value = ["/getLunCalInfo"], headers = ["Accept=application/xml"])
    fun getLunCalInfo(
        @RequestParam("solYear") solYear: String,
        @RequestParam("solMonth") solMonth: String,
        @RequestParam("solDay") solDay: String,
        @RequestParam("serviceKey") serviceKey: String
    ): CalendarResponse

    @GetMapping(value = ["/getSolCalInfo"], headers = ["Accept=application/xml"])
    fun getSolCalInfo(
        @RequestParam("lunYear") lunYear: String,
        @RequestParam("lunMonth") lunMonth: String,
        @RequestParam("lunDay") lunDay: String,
        @RequestParam("serviceKey") serviceKey: String
    ): CalendarResponse
}

@JacksonXmlRootElement(localName = "response")
data class CalendarResponse(
    val header: Header,
    val body: Body
)

data class Header(
    @JacksonXmlProperty(localName = "resultCode")
    val resultCode: String,

    @JacksonXmlProperty(localName = "resultMsg")
    val resultMsg: String
)

data class Body(
    val items: Items,
    @JacksonXmlProperty(localName = "numOfRows")
    val numOfRows: Int,

    @JacksonXmlProperty(localName = "pageNo")
    val pageNo: Int,

    @JacksonXmlProperty(localName = "totalCount")
    val totalCount: Int
)

data class Items(
    @JacksonXmlProperty(localName = "item")
    val item: Item
//    @JacksonXmlElementWrapper(useWrapping = false) // 없으면 item이 배열로 감싸져서 나옴
//    val item: List<Item>
)

data class Item(
    @JacksonXmlProperty(localName = "lunDay")
    val lunDay: Int,

    @JacksonXmlProperty(localName = "lunIljin")
    val lunIljin: String,

    @JacksonXmlProperty(localName = "lunLeapmonth")
    val lunLeapmonth: String,

    @JacksonXmlProperty(localName = "lunMonth")
    val lunMonth: Int,

    @JacksonXmlProperty(localName = "lunNday")
    val lunNday: String,

    @JacksonXmlProperty(localName = "lunSecha")
    val lunSecha: String,

    @JacksonXmlProperty(localName = "lunWolgeon")
    val lunWolgeon: String,

    @JacksonXmlProperty(localName = "lunYear")
    val lunYear: Int,

    @JacksonXmlProperty(localName = "solDay")
    val solDay: Int,

    @JacksonXmlProperty(localName = "solJd")
    val solJd: String,

    @JacksonXmlProperty(localName = "solLeapyear")
    val solLeapyear: String,

    @JacksonXmlProperty(localName = "solMonth")
    val solMonth: Int,

    @JacksonXmlProperty(localName = "solWeek")
    val solWeek: String,

    @JacksonXmlProperty(localName = "solYear")
    val solYear: Int
)