/**
 * 425-00007280ISTVKOT280K2310MC1.29/NETWORK PRODUCTS
 * 770-77054250AYTSVO/T8K670MC0.7T/PARTSFORMACHINERY
 */
class OrderParser {

    fun parse(orders: String): Result<ParserResult> {
        return runCatching {

            val parsedResult = mutableMapOf<String, Order>()

            orders.split("\n").forEach {
                var data = it
                val orderNumber = getOrderNumber(data)
                data = data.drop(orderNumber.length)

                val firstAirport = data.take(3)
                data = data.drop(3)

                val secondAirport = data.take(3)
                data = data.drop(3)

                val piecesPrefixLength = if (data[0] == '/') 2 else 1

                val pieces = data.substringBefore("K").drop(piecesPrefixLength)

                data = data.drop(pieces.length).drop(piecesPrefixLength)

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

            val parsedString = parsedResult.map { (_, order) ->
                "${order.orderNumber}${order.firstAirport}${order.secondAirport}/T${order.pieces}K${order.weight}MC${order.volume}/${order.products}"
            }.joinToString(separator = "\n")

            ParserResult(
                uniqueOrdersAmount = parsedResult.size,
                parsedString = parsedString,
            )
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