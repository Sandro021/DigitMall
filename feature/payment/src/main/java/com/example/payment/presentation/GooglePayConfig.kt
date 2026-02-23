package com.example.payment.presentation


import org.json.JSONArray
import org.json.JSONObject

object GooglePayConfig {
    private const val MINIMUM_API_VERSION = 2
    private const val MINIMUM_API_VERSION_MINOR = 0

    private val allowedCardNetworks =
        listOf("AMEX", "DISCOVER", "JCB", "MASTERCARD", "VISA")
    private val allowedCardAuthMethods = listOf("PAN_ONLY", "CRYPTOGRAM_3DS")

    private fun baseCardPaymentMethod(): JSONObject {
        return JSONObject().apply {
            put("type", "CARD")
            put("parameters", JSONObject().apply {
                put("allowedAuthMethods", JSONArray(allowedCardAuthMethods))
                put("allowedCardNetworks", JSONArray(allowedCardNetworks))
            })
        }
    }

    private fun tokenizationSpecification(): JSONObject {
        return JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put("parameters", JSONObject().apply {
                put("gateway", "example")
                put(
                    "gatewayMerchantId",
                    "exampleGatewayMerchantId"
                )
            })
        }
    }

    private fun getBaseRequest(): JSONObject {
        return JSONObject().apply {
            put("apiVersion", MINIMUM_API_VERSION)
            put("apiVersionMinor", MINIMUM_API_VERSION_MINOR)
        }
    }

    fun getIsReadyToPayRequest(): JSONObject {
        return getBaseRequest().apply {
            put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod()))
        }
    }

    fun getPaymentDataRequest(price: String): JSONObject {
        val cardPaymentMethod = baseCardPaymentMethod().apply {
            put("tokenizationSpecification", tokenizationSpecification())
        }

        return getBaseRequest().apply {
            put("allowedPaymentMethods", JSONArray().put(cardPaymentMethod))
            put("transactionInfo", JSONObject().apply {
                put("totalPrice", price)
                put("totalPriceStatus", "FINAL")
                put("currencyCode", "USD")
            })
            put("merchantInfo", JSONObject().apply {
                put("merchantName", "Digit Mall")
            })
        }
    }
}