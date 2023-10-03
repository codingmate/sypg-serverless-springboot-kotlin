package dev.sypg.app.api

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import dev.sypg.app.handler.AwsLambdaHandler
import dev.sypg.app.service.LottoService
import dev.sypg.app.util.JsonUtil
import org.springframework.stereotype.Component

@Component
class LottoApi {
    private val jsonUtil by lazy { AwsLambdaHandler.applicationContext.getBean(JsonUtil::class.java) }
    private val lottoService by lazy { AwsLambdaHandler.applicationContext.getBean(LottoService::class.java) }

    fun index(req: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {
        var statusCode = 200
        var body = ""
        when(req.httpMethod) {
            "GET" -> { body = getRandomLottoNumbers() }
            else -> {
                statusCode = 405
                body = "lotto Method ${req.httpMethod} not found"
            }
        }
        return APIGatewayProxyResponseEvent()
            .withStatusCode(statusCode)
            .withBody(body)
    }
    fun getRandomLottoNumbers(): String {
        val lottoNumbers = lottoService.getRandomLottoNumbers()
        val numbers = lottoNumbers.map { it.getNumber() }
        return jsonUtil.mapToJson(mapOf("numbers" to jsonUtil.listToJson(numbers) ))
    }
}