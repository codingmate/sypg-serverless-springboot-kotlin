package dev.sypg.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication // 이 애노테이션은 이 클래스가 Spring Boot 애플리케이션의 주 진입점임을 나타냅니다.
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        //addInitializers(FunctionConfiguration())
    }
}