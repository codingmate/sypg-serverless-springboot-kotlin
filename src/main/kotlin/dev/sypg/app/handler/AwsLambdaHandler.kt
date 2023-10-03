package dev.sypg.app.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import dev.sypg.app.Application
import dev.sypg.app.router.AwsApiGatewayRouter
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

// 클래스를 선언하며, AWS에서 제공하는 RequestHandler 인터페이스를 구현합니다.
@Component
class AwsLambdaHandler
    //(private val router: AwsApiGatewayRouter = AwsApiGatewayRouter() // 빈 생성자 만들지 않으려고))
    : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    companion object {
        val applicationContext: ConfigurableApplicationContext by lazy {
            SpringApplication.run(Application::class.java)
        }
        //val applicationContext by lazy { SpringApplication.run(MySpringBootApplication::class.java) }와 동일
    }
    // null 처리와 관련하여 안전한 호출을 사용하거나 초기화 로직을 분리할 수 있습니다.
    private val router: AwsApiGatewayRouter by lazy {
        applicationContext.getBean(AwsApiGatewayRouter::class.java)
    }


    // handleRequest 메서드는 AWS Lambda가 호출하는 메서드입니다.
    // input: APIGatewayProxyRequestEvent는 API Gateway로부터의 요청을 담고 있습니다.
    // context: Context는 런타임에 대한 다양한 정보와 기능을 제공하는 객체입니다.
    override fun handleRequest(
        input: APIGatewayProxyRequestEvent,
        context: Context
    ): APIGatewayProxyResponseEvent {
        // context의 logger를 사용하여 CloudWatch Logs에 로깅을 수행합니다.
        context.logger.log("Received HTTP method: ${input.httpMethod}")

        // HTTP 메서드에 따라 다른 메서드를 호출하도록 분기처리를 합니다.
        return when (input.httpMethod) {
            "GET" -> router.routing(input)
            "POST" -> router.routing(input)
            "PUT" -> router.routing(input)
            "DELETE" -> router.routing(input)
            else -> APIGatewayProxyResponseEvent()
                // 405 응답 코드는 메서드가 허용되지 않음을 나타냅니다.
                .withStatusCode(405)
                .withBody("Method ${input.httpMethod} not allowed")
        }
    }
}