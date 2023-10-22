import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import org.w3c.dom.Element

class DocumentTest {

    private fun createSampleDocument(): Document {
        val xmlContent = """
            <response>
                <header>
                    <resultCode>00</resultCode>
                    <resultMsg>NORMAL SERVICE.</resultMsg>
                </header>
                <body>
                    <items>
                        <item>
                            <lunDay>11</lunDay>
                            <lunMonth>11</lunMonth>
                            <lunYear>2014</lunYear>
                        </item>
                    </items>
                </body>
            </response>
        """.trimIndent()

        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val xmlInput = xmlContent.byteInputStream(Charsets.UTF_8)
        return dBuilder.parse(xmlInput)
    }

    @Test
    fun testGetElementsByTagName() {
        val document = createSampleDocument()

        val items = document.getElementsByTagName("item")
        assertEquals(1, items.length)

        val lunDay = (items.item(0) as Element).getElementsByTagName("lunDay").item(0).textContent
        assertEquals("11", lunDay)
    }

    @Test
    fun testGetDocumentElement() {
        val document = createSampleDocument()

        val rootElement = document.documentElement
        assertEquals("response", rootElement.tagName)
    }

    // 여기에 다른 메서드 테스트들을 추가할 수 있습니다.
}
