package com.example.ui.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State, Event>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    open fun onEvent(event: Event){}

    protected fun updateState(update: (State) -> State) {
        _state.value = update(_state.value)
    }


}
