package com.bolsojovem.pixcontrol.data.model

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime
import java.util.UUID

enum class TransactionType {
    EXPENSE,
    INCOME,
    PIX_SENT,
    PIX_RECEIVED,
    GOAL_DEPOSIT
}

@Immutable
data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val type: TransactionType = TransactionType.EXPENSE,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val pixKey: String? = null,
    val note: String? = null
) {
    val formattedAmount: String
        get() = String.format("R$ %.2f", amount).replace(".", ",")
}
