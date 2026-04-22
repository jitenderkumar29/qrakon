//package com.example.qrakon.components.restaurants
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//
//class CheckoutViewModel : ViewModel() {
//    private val _showPaymentOptions = MutableStateFlow(false)
//    val showPaymentOptions: StateFlow<Boolean> = _showPaymentOptions.asStateFlow()
//
//    fun onPaymentClick() {
//        _showPaymentOptions.value = true
//    }
//
//    fun onPaymentOptionsDismissed() {
//        _showPaymentOptions.value = false
//    }
//}