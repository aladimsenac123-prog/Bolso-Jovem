package com.bolsojovem.pixcontrol.data.model

import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val category: ExpenseCategory = ExpenseCategory.OTHER,
    val timestamp: String,
    val isPix: Boolean = true
) {
    val formattedAmount: String
        get() = String.format("R$ %.2f", amount).replace(".", ",")
}
