package dev.sypg.app.vo


class Lotto(private val numbers: Set<LottoNumber>) {
    init {
        require(numbers.size == 6) { "Lotto must have exactly 6 numbers." }
    }
}