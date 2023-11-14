package dev.sypg.app.client.publicapi
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
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