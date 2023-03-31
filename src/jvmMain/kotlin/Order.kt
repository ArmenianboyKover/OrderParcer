import java.math.BigDecimal

data class Order(
    val orderNumber: String,
    val firstAirport: String,
    val secondAirport: String,
    val pieces: Int,
    val weight: BigDecimal,
    val volume: BigDecimal,
    val products: String,
    val ordersAmount: Int,
)
