import app.cash.turbine.test
import com.example.payment.presentation.CheckoutViewModel
import com.example.payment.presentation.GooglePayConfig
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CheckoutViewModelTest {

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        viewModel = CheckoutViewModel()
    }

    @After
    fun tearDown() {
        // Clean up static and object mocks to prevent leaking into other tests
        unmockkAll()
    }

    @Test
    fun `Given successful Task result, When determine availability, Then flow emits true`() = runTest {
        // --- GIVEN ---
        val paymentsClient = mockk<PaymentsClient>()
        val mockTask = mockk<Task<Boolean>>()
        val mockRequest = mockk<IsReadyToPayRequest>()

        mockkObject(GooglePayConfig)
        every { GooglePayConfig.getIsReadyToPayRequest().toString() } returns "{}"

        mockkStatic(IsReadyToPayRequest::class)
        every { IsReadyToPayRequest.fromJson(any()) } returns mockRequest

        every { mockTask.isSuccessful } returns true
        every { mockTask.result } returns true

        val listenerSlot = slot<OnCompleteListener<Boolean>>()
        every { paymentsClient.isReadyToPay(mockRequest) } returns mockTask

        every { mockTask.addOnCompleteListener(capture(listenerSlot)) } answers {
            listenerSlot.captured.onComplete(mockTask)
            mockTask
        }

        // --- WHEN & THEN ---
        viewModel.isGooglePayAvailable.test {
            assertEquals(false, awaitItem())

            viewModel.determineGooglePayAvailability(paymentsClient)

            assertEquals(true, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given failed Task result, When determine availability, Then flow remains false`() = runTest {
        // --- GIVEN ---
        val paymentsClient = mockk<PaymentsClient>()
        val mockTask = mockk<Task<Boolean>>()
        val mockRequest = mockk<IsReadyToPayRequest>()

        mockkObject(GooglePayConfig)
        every { GooglePayConfig.getIsReadyToPayRequest().toString() } returns "{}"

        mockkStatic(IsReadyToPayRequest::class)
        every { IsReadyToPayRequest.fromJson(any()) } returns mockRequest

        every { mockTask.isSuccessful } returns false

        val listenerSlot = slot<OnCompleteListener<Boolean>>()
        every { paymentsClient.isReadyToPay(mockRequest) } returns mockTask
        every { mockTask.addOnCompleteListener(capture(listenerSlot)) } answers {
            listenerSlot.captured.onComplete(mockTask)
            mockTask
        }

        // --- WHEN & THEN ---
        viewModel.isGooglePayAvailable.test {
            assertEquals(false, awaitItem())

            viewModel.determineGooglePayAvailability(paymentsClient)


            expectNoEvents()

            assertEquals(false, viewModel.isGooglePayAvailable.value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Given token string, When processPaymentToken is called, Then paymentCompleted emits true`() = runTest {
        // --- WHEN & THEN ---
        viewModel.paymentCompleted.test {
            assertEquals(false, awaitItem())

            // Trigger the function
            viewModel.processPaymentToken("dummy_token_123")

            assertEquals(true, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
