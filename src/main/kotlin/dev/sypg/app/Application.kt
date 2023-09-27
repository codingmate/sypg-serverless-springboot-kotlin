package dev.sypg.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Function

@SpringBootApplication // 이 애노테이션은 이 클래스가 Spring Boot 애플리케이션의 주 진입점임을 나타냅니다.
class Application {
    data class Request(val functionName: String, val params: Map<String, String>)


    @Bean
    fun index(): Function<String, String> = Function {
        "index!!!!!!!!!!1 >>> $it"
    }
    @Bean
    fun index1(): Function<Request, String> = Function {
        println("Request: $it")
        when (it.functionName) {
            "hello" -> "Hello, World!! ${it.params["value"] ?: "????"}"
            "uppercase" -> it.params["value"]?.uppercase() ?: "??????"
            else -> "Unknown function"
        }
    }

    @Bean
    fun index2(): Function<Request, String> = Function {
        "index!!!!!!!!!!22222222222"
    }
}


fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        //addInitializers(FunctionConfiguration())
    }
}