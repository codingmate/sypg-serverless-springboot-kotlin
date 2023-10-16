package dev.sypg.learning.xml

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilderFactory

class XmlToDocumentTest {
    @Test
    fun `should parse XML string to DOM correctly`() {
        val xmlString = """
            <response>
                <header>
                    <resultCode>00</resultCode>
                    <resultMsg>NORMAL SERVICE.</resultMsg>
                </header>
                <body>
                    <items>
                        <item>
                            <lunDay>11</lunDay>
                            <!-- ... (다른 XML 요소들) -->
                            <solYear>2015</solYear>
                        </item>
                    </items>
                    <numOfRows>10</numOfRows>
                    <pageNo>1</pageNo>
                    <totalCount>1</totalCount>
                </body>
            </response>
        """.trimIndent()

        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc: Document = dBuilder.parse(xmlString.byteInputStream())

        // 검증
        Assertions.assertThat(doc.getElementsByTagName("resultCode").item(0).textContent).isEqualTo("00")
        Assertions.assertThat(doc.getElementsByTagName("resultMsg").item(0).textContent).isEqualTo("NORMAL SERVICE.")
        Assertions.assertThat(doc.getElementsByTagName("lunDay").item(0).textContent).isEqualTo("11")
        // ... (다른 XML 요소들에 대한 검증도 추가)
        Assertions.assertThat(doc.getElementsByTagName("solYear").item(0).textContent).isEqualTo("2015")
    }
}