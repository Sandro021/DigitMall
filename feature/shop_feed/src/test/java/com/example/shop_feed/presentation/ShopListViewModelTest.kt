package com.example.shop_feed.presentation

import app.cash.turbine.test
import com.example.shop_feed.domain.model.Shop
import com.example.shop_feed.domain.usecase.GetShopsUseCase
import com.example.shop_feed.presentation.common.MainDispatcherRule
import com.example.shop_feed.presentation.contract.ShopListIntent
import com.example.shop_feed.presentation.contract.ShopListState
import com.example.shop_feed.presentation.mapper.toUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShopListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getShopsUseCase: GetShopsUseCase = mockk()

    private lateinit var viewModel: ShopListViewModel

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given successful response, When ViewModel is initialized, Then state emits loading and mapped shops`() =
        runTest {

            // --- GIVEN ---
            val realDomainShop = Shop(id = "1", name = "Test Shop", "", "", "", "")

            val expectedUiShop = realDomainShop.toUiModel()

            coEvery { getShopsUseCase() } returns Result.success(listOf(realDomainShop))

            // --- WHEN ---
            viewModel = ShopListViewModel(getShopsUseCase)

            // --- THEN ---
            viewModel.state.test {
                assertEquals(ShopListState(), awaitItem()) // Initial
                assertEquals(ShopListState(isLoading = true), awaitItem()) // Loading

                val successState = awaitItem()
                assertEquals(false, successState.isLoading)
                // 4. Compare the state against the expected real UI model
                assertEquals(listOf(expectedUiShop), successState.shops)
                assertEquals(null, successState.error)

                cancelAndIgnoreRemainingEvents()
            }

        }


    @Test
    fun `Given failed response, When ViewModel is initialized, Then state emits loading and error message`() =
        runTest {
            // --- GIVEN ---
            val errorMessage = "Network timeout"
            coEvery { getShopsUseCase() } returns Result.failure(Exception(errorMessage))

            // --- WHEN ---
            viewModel = ShopListViewModel(getShopsUseCase)

            // --- THEN ---
            viewModel.state.test {
                // Emission 1: Initial State
                assertEquals(ShopListState(), awaitItem())

                assertEquals(ShopListState(isLoading = true), awaitItem())

                val errorState = awaitItem()
                assertEquals(false, errorState.isLoading)
                assertEquals(errorMessage, errorState.error)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Given OnShopClicked intent, When handled, Then no new state is emitted`() = runTest {
        // --- GIVEN ---
        coEvery { getShopsUseCase() } returns Result.success(emptyList())
        viewModel = ShopListViewModel(getShopsUseCase)

        advanceUntilIdle()

        // --- WHEN ---
        viewModel.handleIntent(ShopListIntent.OnShopClicked("1"))

        advanceUntilIdle()

        // --- THEN ---
        coVerify(exactly = 1) { getShopsUseCase() }
    }
}