package com.example.payment.presentation

import androidx.lifecycle.ViewModel
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {

    private val _isGooglePayAvailable = MutableStateFlow(false)
    val isGooglePayAvailable: StateFlow<Boolean> = _isGooglePayAvailable.asStateFlow()

    private val _paymentCompleted = MutableStateFlow(false)
    val paymentCompleted: StateFlow<Boolean> = _paymentCompleted.asStateFlow()

    fun determineGooglePayAvailability(paymentsClient: PaymentsClient) {
        val requestJson = GooglePayConfig.getIsReadyToPayRequest().toString()
        val request = IsReadyToPayRequest.fromJson(requestJson)

        paymentsClient.isReadyToPay(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _isGooglePayAvailable.value = task.result
            } else {
                _isGooglePayAvailable.value = false
            }
        }
    }

    fun processPaymentToken(paymentMethodToken: String) {

        println("Success! Token received: $paymentMethodToken")

        _paymentCompleted.value = true
    }
}