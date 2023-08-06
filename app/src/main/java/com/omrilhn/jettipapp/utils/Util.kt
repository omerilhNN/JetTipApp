package com.omrilhn.jettipapp.utils

public fun calculateTotalTip(totalBill: Double, tipPercentage: Int): Double {

    return if (totalBill > 1 && totalBill.toString().isNotEmpty()) (totalBill * tipPercentage   ) / 100 else 0.0
}
public calculateTotalPerPerson(totalBill: Double, splitBy: Int, tipPercentage: Int): Double {
    val bill = calculateTotalTip(totalBill, tipPercentage = tipPercentage) + totalBill
    return (bill/splitBy)
}