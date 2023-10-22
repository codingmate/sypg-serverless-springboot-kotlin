import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer

// 플러그인 섹션 : 프로젝트에 필요한 플러그인을 정의
plugins {
    id("org.springframework.boot") version "3.1.4" // Sppring Boot 플러그인과 버전 적용
    id("io.spring.dependency-management") version "1.1.3" // Spring Dependency Management 플러그인과 버전 적용
    kotlin("jvm") version "1.8.22" // Kotlin JVM 플러그인과 버전 적용
    kotlin("plugin.spring") version "1.8.22" // Kotlin Spring 플러그인과 버전 적용

    id("com.github.johnrengelman.shadow") version "8.1.1" // Shadow 플러그인과 버전 적용
    id("org.springframework.boot.experimental.thin-launcher") version "1.0.31.RELEASE" // Spring Boot Thin Launcher 플러그인과 버전 적용
}

group = "dev.sypg" // 프로젝트 그룹 ID
version = "0.0.1-SNAPSHOT" // 프로젝트 버전

java {
    sourceCompatibility = JavaVersion.VERSION_17 // Java 소스 호환성 버전을 17로 설정
}

// 리포지토리 섹션 : 의존성을 다운로드 받을 저장소 정의
repositories {
    mavenCentral() // Maven Central 저장소 추가
    // mavenLocal() // 로컬 Maven 저장소
}

dependencies {

    implementation ("com.amazonaws:aws-lambda-java-core:1.2.2") // AWS Lambda를 위한 의존성 추가
    implementation ("com.amazonaws:aws-lambda-java-events:3.11.1") // AWS Lambda 이벤트를 위한 의존성 추가
    implementation("com.amazonaws.serverless:aws-serverless-java-container-spring:2.0.0-M2")    // AWS Lambda를 위한 Spring Boot 컨테이너 의존성 추가
    implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot3:2.0.0-M2")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.5") // AWS Lambda를 위한 Spring Boot 2 컨테이너 의존성 추가
    runtimeOnly ("com.amazonaws:aws-lambda-java-log4j2:1.5.1") // 로깅을 위한 런타임 의존성 추가

    implementation("com.google.code.gson:gson:2.8.9") // Gson 의존성 추가

    implementation("io.ktor:ktor-client-core-jvm:2.3.5") // Ktor 클라이언트 의존성 추가


    developmentOnly("org.springframework.cloud:spring-cloud-starter-function-web:4.0.5") // Spring Cloud Function의 웹 기능을 지원하는 스타터. 함수를 웹 엔드포인트로 노출
    implementation("org.springframework.cloud:spring-cloud-function-kotlin:4.0.5")  // Kotlin을 위한 Spring Cloud Function 지원
    implementation("org.springframework.cloud:spring-cloud-function-context:4.0.5") // Spring Cloud Function의 컨텍스트 기능 제공. 함수 빈들을 관리 및 실행
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:4.0.5")  // AWS Lambda를 위한 Spring Cloud Function 어댑터. 함수를 AWS Lambda에서 실행할 수 있게 설정
    implementation("com.amazonaws:aws-lambda-java-events:3.11.3") // AWS Lambda 이벤트를 위한 의존성 추가


    implementation("org.springframework.boot:spring-boot-starter")  // 스프링 부트 기본 스타터. 웹 개발, 설정, AOP 등 다양한 기능 제공
    testImplementation("org.springframework.boot:spring-boot-starter-test") // 스프링 부트 테스트 스타터. JUnit, TestNG 등 테스트 프레임워크와 통합 제공.
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict") // Kotlin 컴파일러 옵션 설정. JSR-305 애노테이션을 strict 모드로 처리합니다.
        jvmTarget = "17" // 컴파일된 바이트코드의 타겟 Java 버전을 17로 설정합니다.
    }
}

tasks.withType<Test> { // 프로젝트 내의 모든 'Test' 타입의 Gradle 태스크를 대상으로 설정
    useJUnitPlatform() // Gradle의 Task를 구성하는 방식으로, JUnit 5를 사용하여 테스트를 실행하도록 설정
}

tasks.assemble {
    dependsOn("thinJar", "shadowJar")
}

tasks {
    shadowJar {
        mustRunAfter(thinJar)
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("aws")
    dependencies {
        exclude("org.springframework.cloud:spring-cloud-function-web")
    }

    manifest {
        from(tasks.named<Jar>("thinJar").get().manifest)
    }

    mergeServiceFiles()
    append("META-INF/spring.handlers")
    append("META-INF/spring.schemas")
    append("META-INF/spring.tooling ")
    append("META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports")
    append("META-INF/spring/org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.imports")

    transform(PropertiesFileTransformer::class.java) {
        paths = listOf("META-INF/spring.factories")
        mergeStrategy = "append"
    }
}