package com.bolsojovem.pixcontrol.data.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class SavingGoal(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val emoji: String,
    val deadlineMonth: String
) {
    val progress: Float
        get() = if (targetAmount > 0) ((currentAmount / targetAmount).toFloat()).coerceIn(0f, 1f) else 0f

    val remainingAmount: Double
        get() = (targetAmount - currentAmount).coerceAtLeast(0.0)
}

@Immutable
data class AccountBalance(
    val totalBalance: Double,
    val dailySpent: Double,
    val dailyLimit: Double = 50.0,
    val monthlyIncome: Double,
    val monthlyExpenses: Double
) {
    val isOverDailyLimit: Boolean
        get() = dailySpent > dailyLimit

    val dailyLimitRemaining: Double
        get() = (dailyLimit - dailySpent).coerceAtLeast(0.0)

    val dailyProgress: Float
        get() = if (dailyLimit > 0) ((dailySpent / dailyLimit).toFloat()).coerceIn(0f, 1f) else 0f
}
