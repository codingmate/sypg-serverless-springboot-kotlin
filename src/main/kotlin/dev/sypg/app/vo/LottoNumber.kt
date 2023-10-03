package dev.sypg.app.vo

data class LottoNumber(private val number: Int) {
    init {
        require(number in 1..45) { "LottoNumber must be between 1 and 45." }
    }
    fun getNumber(): Int {
        return number
    }
}