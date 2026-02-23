package com.example.payment.presentation


import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.pay.button.PayButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    totalAmount: Float,
    onNavigateBack: () -> Unit,
    onPaymentSuccess: () -> Unit,
    viewModel: CheckoutViewModel = viewModel()
) {
    val context = LocalContext.current
    val isGooglePayAvailable by viewModel.isGooglePayAvailable.collectAsState()
    val paymentCompleted by viewModel.paymentCompleted.collectAsState()

    val paymentsClient = remember {
        Wallet.getPaymentsClient(
            context,
            Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                .build()
        )
    }

    LaunchedEffect(Unit) {
        viewModel.determineGooglePayAvailability(paymentsClient)
    }
    LaunchedEffect(paymentCompleted) {
        if (paymentCompleted) {
            onPaymentSuccess()
        }
    }

    val paymentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val paymentData = PaymentData.getFromIntent(intent)
                paymentData?.toJson()?.let { jsonString ->
                    try {
                        val token = org.json.JSONObject(jsonString)
                            .getJSONObject("paymentMethodData")
                            .getJSONObject("tokenizationData")
                            .getString("token")
                        viewModel.processPaymentToken(token)
                    } catch (e: org.json.JSONException) {
                        Log.e("GooglePay", "Failed to parse payment token", e)
                    }
                }
            }
        } else {
            Log.e("GooglePay", "Payment failed or was canceled.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (paymentCompleted) {

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Thanks for shopping!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your payment was successful.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "Total Amount: $${totalAmount}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (isGooglePayAvailable) {
                    val allowedPaymentMethods = remember {
                        GooglePayConfig.getIsReadyToPayRequest()
                            .getJSONArray("allowedPaymentMethods")
                            .toString()
                    }

                    PayButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        allowedPaymentMethods = allowedPaymentMethods,
                        onClick = {
                            val requestJson =
                                GooglePayConfig.getPaymentDataRequest(totalAmount.toString())
                                    .toString()
                            val request = PaymentDataRequest.fromJson(requestJson)
                            val task = paymentsClient.loadPaymentData(request)

                            task.addOnCompleteListener { completedTask ->
                                if (completedTask.isSuccessful) {
                                    val paymentData = completedTask.result
                                    paymentData?.toJson()?.let { jsonString ->
                                        try {
                                            val token = org.json.JSONObject(jsonString)
                                                .getJSONObject("paymentMethodData")
                                                .getJSONObject("tokenizationData")
                                                .getString("token")
                                            viewModel.processPaymentToken(token)
                                        } catch (e: org.json.JSONException) {
                                            Log.e("GooglePay", "Parse fail", e)
                                        }
                                    }
                                } else {
                                    val exception = completedTask.exception
                                    if (exception is ResolvableApiException) {
                                        paymentLauncher.launch(
                                            IntentSenderRequest.Builder(exception.resolution)
                                                .build()
                                        )
                                    }
                                }
                            }
                        }
                    )
                } else {
                    Text(text = "Google Pay is not available on this device.", color = Color.Red)
                }
            }
        }
    }
}