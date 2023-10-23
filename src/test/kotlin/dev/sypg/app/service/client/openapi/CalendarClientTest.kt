package dev.sypg.app.service.client.openapi

import dev.sypg.app.client.openapi.CalendarClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
@SpringBootTest
class CalendarClientTest {

    @Autowired
    private lateinit var calendarClient: CalendarClient

    @Test
    fun testGetLunarDateByGregorianSolar() {
        val solarDate = LocalDate.of(2023, 10, 22)
        val lunarDate = calendarClient.getLunarDateByGregorianSolar(solarDate)

        // 결과에 따라서 검증 로직을 작성합니다.
        // 예를 들어, 2023년 10월 22일에 대한 응답으로 예상되는 응답이 있다면 아래와 같이 검증할 수 있습니다.
        // assertEquals(LocalDate.of(2023, 8, 15), lunarDate)

        println(">>>>>>>>>>>>>>>Lunar Date for $solarDate: $lunarDate")

        // Lunar date의 유효성을 확인합니다.
        assertThat(lunarDate).isNotNull

    }
}