package dev.sypg.app.handler

import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import dev.sypg.app.Application
import org.springframework.stereotype.Component

@Component
class ProxyHandler : RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    companion object {
        val handler:SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> by lazy {
            SpringBootLambdaContainerHandler.getAwsProxyHandler(Application::class.java)
        }
    }
    override fun handleRequest(input: AwsProxyRequest, context: Context): AwsProxyResponse {
        return handler.proxy(input, context)
    }
}