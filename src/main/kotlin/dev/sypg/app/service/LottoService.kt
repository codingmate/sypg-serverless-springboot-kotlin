package dev.sypg.app.service

import dev.sypg.app.domain.lotto.LottoGenerator
import dev.sypg.app.handler.AwsLambdaHandler
import dev.sypg.app.vo.LottoNumber
import org.springframework.stereotype.Service

@Service
class LottoService {

    private val lottoGenerator by lazy {
        AwsLambdaHandler.applicationContext.getBean(LottoGenerator::class.java)
    }
    fun getRandomLottoNumbers(): Set<LottoNumber> {
        return lottoGenerator.generateLottoNumberSet()
    }
}