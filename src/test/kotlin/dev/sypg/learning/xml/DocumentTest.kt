import javax.xml.parsers.DocumentBuilderFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.w3c.dom.Element
import org.w3c.dom.Document

class DocumentTest {
    private fun getSampleDocument(): Document {
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
                            <lunIljin>정축(丁丑)</lunIljin>
                            <lunLeapmonth>평</lunLeapmonth>
                            <lunMonth>11</lunMonth>
                            <lunNday>29</lunNday>
                            <lunSecha>갑오(甲午)</lunSecha>
                            <lunWolgeon>병자(丙子)</lunWolgeon>
                            <lunYear>2014</lunYear>
                            <solDay>01</solDay>
                            <solJd>2457024</solJd>
                            <solLeapyear>평</solLeapyear>
                            <solMonth>01</solMonth>
                            <solWeek>목</solWeek>
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
        return dBuilder.parse(xmlString.byteInputStream())
    }

    @Test
    fun `getElementsByTagName should retrieve elements with matching tag names`() {
        // 이름으로 요소를 검색
        val doc = getSampleDocument()
        val items = doc.getElementsByTagName("item")
        assertThat(items.length).isEqualTo(1)
    }

    @Test
    fun `getElementById should retrieve element with matching id`() {
        // ID로 요소를 검색
        val doc = getSampleDocument()
        val itemElement = doc.getElementById("1234")
        assertThat(itemElement).isNotNull
        assertThat((itemElement as Element).tagName).isEqualTo("item")
    }

    @Test
    fun `getDocumentElement should retrieve the root element`() {
        // 루트 요소를 검색
        val doc = getSampleDocument()
        val rootElement = doc.documentElement
        assertThat(rootElement.tagName).isEqualTo("response")
    }

    @Test
    fun `createElement should create a new element with the given tag name`() {
        // 새 요소를 생성
        val doc = getSampleDocument()
        val newItem = doc.createElement("item")
        assertThat(newItem.tagName).isEqualTo("item")
    }

    @Test
    fun `createTextNode should create a new text node with the given data`() {
        // 새 텍스트 노드를 생성
        val doc = getSampleDocument()
        val textNode = doc.createTextNode("Hello")
        assertThat(textNode.textContent).isEqualTo("Hello")
    }

    @Test
    fun `importNode should import a node from another document`() {
        // 다른 문서에서 노드를 가져오기
        val doc1 = getSampleDocument()
        val doc2 = getSampleDocument()

        val itemFromDoc1 = doc1.getElementsByTagName("item").item(0)
        val importedItem = doc2.importNode(itemFromDoc1, true)

        assertThat(importedItem.isEqualNode(itemFromDoc1)).isTrue
        assertThat(importedItem.ownerDocument).isEqualTo(doc2)
    }

    @Test
    fun `createDocumentFragment should create an empty DocumentFragment`() {
        // 새 DocumentFragment를 생성
        val doc = getSampleDocument()
        val fragment = doc.createDocumentFragment()
        assertThat(fragment.childNodes.length).isEqualTo(0)
    }

    @Test
    fun `createComment should create a new comment node with the given data`() {
        // 새 댓글 노드를 생성
        val doc = getSampleDocument()
        val commentNode = doc.createComment("This is a comment")
        assertThat(commentNode.data).isEqualTo("This is a comment")
    }
}
