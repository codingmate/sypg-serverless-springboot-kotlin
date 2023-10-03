package dev.sypg.app.domain.lotto

import dev.sypg.app.vo.LottoNumber
import org.springframework.stereotype.Component

@Component
class LottoGenerator {
    fun generateLottoNumberSet(): Set<LottoNumber> {
        return (1..45).shuffled().take(6).sorted().map { LottoNumber(it) }.toSet()
    }
}