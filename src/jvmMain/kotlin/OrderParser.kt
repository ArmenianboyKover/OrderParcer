import java.math.BigDecimal

/**
 * 425-00007280ISTVKOT280K2310MC1.29/NETWORK PRODUCTS
 */
class OrderParser {

    fun parse(orders: List<String>) {

        val parsedResult = mutableMapOf<String, Order>()

        orders.forEach {
            var data = it
            val orderNumber = getOrderNumber(data)
            data = data.drop(orderNumber.length)

            val firstAirport = data.take(3)
            data = data.drop(3)

            val secondAirport = data.take(3)
            data = data.drop(3)

            val pieces = data.substringBefore("K").drop(1)

            data = data.drop(pieces.length).drop(1)

            val weight = data.substringBefore("M").drop(1)
            data = data.drop(weight.length).drop(1)

            val volume = data.substringBefore("/").drop(2)
            data = data.drop(volume.length).drop(2)

            val products = data.substringAfter("/")

            if (parsedResult.contains(orderNumber)) {
                val order = parsedResult[orderNumber]
                val updatedOrder = order!!.copy(
                    pieces = order.pieces + pieces.toInt(),
                    weight = order.weight + weight.toBigDecimal(),
                    volume = order.volume + volume.toBigDecimal(),
                )

                parsedResult[orderNumber] = updatedOrder

            } else {
                parsedResult[orderNumber] = Order(
                    orderNumber = orderNumber,
                    firstAirport = firstAirport,
                    secondAirport = secondAirport,
                    pieces = pieces.toInt(),
                    weight = weight.toBigDecimal(),
                    volume = volume.toBigDecimal(),
                    products = products,
                )
            }
        }
        parsedResult.forEach { (key, value) ->
            println(value)
        }
    }

    private fun getOrderNumber(data: String): String {
        var orderNumber = ""
        data.forEach { symbol ->
            if (symbol.isLetter()) {
                return orderNumber
            } else {
                orderNumber += symbol
            }
        }
        return orderNumber
    }
}