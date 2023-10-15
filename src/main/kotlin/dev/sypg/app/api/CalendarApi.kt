package dev.sypg.app.api

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import dev.sypg.app.handler.AwsLambdaHandler
import dev.sypg.app.service.LottoService
import dev.sypg.app.util.JsonUtil
import org.springframework.stereotype.Component

@Component
class CalendarApi {
    private val API_NAME: String = "calendar"
    private val jsonUtil by lazy { AwsLambdaHandler.applicationContext.getBean(JsonUtil::class.java) }
    private val lottoService by lazy { AwsLambdaHandler.applicationContext.getBean(LottoService::class.java) }
    fun index(req: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {

        var statusCode = 200
        var body = ""
        when(req.httpMethod) {
            "GET" -> { body }
            else -> {
                statusCode = 405
                body = "$API_NAME Method ${req.httpMethod} not found"
            }
        }
        return APIGatewayProxyResponseEvent()
            .withStatusCode(statusCode)
            .withBody(body)
    }

}

