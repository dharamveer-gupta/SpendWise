package com.dharamveer.spendwise.presentation.state

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
    object ExpenseSaved : UiEvent()
}