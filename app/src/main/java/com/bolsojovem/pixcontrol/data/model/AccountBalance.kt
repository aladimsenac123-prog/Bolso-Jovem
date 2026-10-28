package com.bolsojovem.pixcontrol.data.model

data class AccountBalance(
    val totalBalance: Double = 840.0,
    val dailySpent: Double = 0.0,
    val dailyLimit: Double = 50.0,
    val monthlyIncome: Double = 1200.0,
    val monthlyExpenses: Double = 360.0
) {
    val isOverDailyLimit: Boolean
        get() = dailySpent > dailyLimit

    val remainingDailyBudget: Double
        get() = (dailyLimit - dailySpent).coerceAtLeast(0.0)

    val formattedTotalBalance: String
        get() = String.format("R$ %.2f", totalBalance).replace(".", ",")

    val formattedDailySpent: String
        get() = String.format("R$ %.2f", dailySpent).replace(".", ",")
}
