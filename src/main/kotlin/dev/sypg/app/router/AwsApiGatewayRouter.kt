package dev.sypg.app.router

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import dev.sypg.app.api.CalendarApi
import dev.sypg.app.api.LottoApi
import dev.sypg.app.handler.AwsLambdaHandler
import dev.sypg.app.util.JsonUtil
import org.springframework.stereotype.Component

/*
            // 상태 코드 200은 요청이 성공적으로 이루어졌음을 나타냅니다.
                .withStatusCode(200)
            // 상태 코드 201은 리소스가 성공적으로 생성되었음을 나타냅니다.
                .withStatusCode(201)
            // 상태 코드 204는 서버가 요청을 성공적으로 수행했으나, 응답 페이로드 본문에 보낼 추가 정보가 없음을 나타냅니다.
                .withStatusCode(204)
            // 405 응답 코드는 메서드가 허용되지 않음을 나타냅니다.
                .withStatusCode(405)
         */

//        "req": {
//            "version": 1.0,
//            "resource": "/springboot-kotlin",
//            "path": "/default/springboot-kotlin",
//            "httpMethod": "PUT",
//            "headers": {
//               "Content-Length": "43",
//                "Content-Type": "application/json",
//                "Host": "ek6tyvy11f.execute-api.ap-northeast-2.amazonaws.com",
//                "Postman-Token": "2b53da8e-4775-416f-9490-82edcef49e5a",
//                "User-Agent": "PostmanRuntime/7.33.0",
//                "X-Amzn-Trace-Id": "Root=1-651c0200-64f789d364ae2f41378ba185",
//                "X-Forwarded-For": "106.101.2.200",
//                "X-Forwarded-Port": "443",
//                "X-Forwarded-Proto": "https",
//                "accept": "*/*",
//                "accept-encoding": "gzip, deflate, br"
//            },
//            "multiValueHeaders": {
//                "Content-Length": ["43"],
//                "Content-Type": ["application/json"],
//                "Host": ["ek6tyvy11f.execute-api.ap-northeast-2.amazonaws.com"],
//                "Postman-Token": ["2b53da8e-4775-416f-9490-82edcef49e5a"],
//                "User-Agent": ["PostmanRuntime/7.33.0"],
//                "X-Amzn-Trace-Id": ["Root=1-651c0200-64f789d364ae2f41378ba185"],
//                "X-Forwarded-For": ["106.101.2.200"],
//                "X-Forwarded-Port": ["443"],
//                "X-Forwarded-Proto": ["https"],
//                "accept": ["*/*"],
//                "accept-encoding": ["gzip, deflate, br"]
//            },
//            "requestContext": {
//                "accountId": "678643869281",
//                "resourceId": "ANY /springboot-kotlin",
//                "stage": "default",
//                "requestId": "MOVAJjkfIE0EMkg=",
//                "identity": {
//                    "sourceIp": "106.101.2.200",
//                    "userAgent": "PostmanRuntime/7.33.0"
//                },
//                "resourcePath": "/springboot-kotlin",
//                "httpMethod": "PUT",
//                "apiId": "ek6tyvy11f",
//                "path": "/default/springboot-kotlin"
//            },
//            "body": {
//                "rest": "lotto",
//                "id": "1"
//            },
//            "isBase64Encoded": false
//        },
//        "header": {
//            "Content-Length": "43",
//            "Content-Type": "application/json",
//            "Host": "ek6tyvy11f.execute-api.ap-northeast-2.amazonaws.com",
//            "Postman-Token": "2b53da8e-4775-416f-9490-82edcef49e5a",
//            "User-Agent": "PostmanRuntime/7.33.0",
//            "X-Amzn-Trace-Id": "Root=1-651c0200-64f789d364ae2f41378ba185",
//            "X-Forwarded-For": "106.101.2.200",
//            "X-Forwarded-Port": "443",
//            "X-Forwarded-Proto": "https",
//            "accept": "*/*",
//            "accept-encoding": "gzip, deflate, br"
//        }
@Component
class AwsApiGatewayRouter {

    private val jsonUtil by lazy { AwsLambdaHandler.applicationContext.getBean(JsonUtil::class.java) }
    private val lottoApi by lazy { AwsLambdaHandler.applicationContext.getBean(LottoApi::class.java) }
    private val calendarApi by lazy { AwsLambdaHandler.applicationContext.getBean(CalendarApi::class.java) }
    fun routing(req: APIGatewayProxyRequestEvent): APIGatewayProxyResponseEvent {

        val bodyMap: Map<String, Any> = jsonUtil.jsonToMap(req.body)
        val res: APIGatewayProxyResponseEvent =
            when (bodyMap["rest"]) {
            "lotto" -> lottoApi.index(req)
            "calendar" -> calendarApi.index(req)
            else -> APIGatewayProxyResponseEvent()
                .withStatusCode(400)
                .withBody("restapi(=${bodyMap["rest"]}) not found")
        }
        return res.withHeaders(mapOf("Content-Type" to "application/json"))

        /*
        return APIGatewayProxyResponseEvent()
            .withStatusCode(200)
            .withHeaders(mapOf("Content-Type" to "application/json"))
            .withBody("""
                    req : ${req}
                    header : ${req.headers}
                    body : ${req.body}
                    httpMethod : ${req.httpMethod}
                    version : ${req.version}
                    path : ${req.path}
                    pathParameters : ${req.pathParameters}
                    queryStringParameters : ${req.queryStringParameters}
                    requestContext : ${req.requestContext}
                    """)
         */
    }
}