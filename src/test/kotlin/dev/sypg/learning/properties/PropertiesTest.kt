package dev.sypg.learning.properties

import dev.sypg.app.Application
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@TestPropertySource( locations = ["/test-application.yml"] )
@SpringBootTest(classes = [Application::class])
class PropertiesTest {

    @Value("\${app.name}")
    lateinit var appName: String

    @Value("\${app.version}")
    lateinit var appVersion: String
    @Test
    fun `should load properties from test application yml`() {
        assertThat(1).isEqualTo(1)
        assertThat(appName).isEqualTo("sypg")
        assertThat(appVersion).isEqualTo("1.0.0")
    }
}