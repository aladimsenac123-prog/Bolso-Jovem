package com.bolsojovem.pixcontrol.data.model

data class AccountSummary(
    val dailyBudgetLimit: Double = 50.0,
    val totalSpentToday: Double = 0.0,
    val transactionCount: Int = 0
) {
    val isOverBudget: Boolean
        get() = totalSpentToday > dailyBudgetLimit

    val remainingBudget: Double
        get() = (dailyBudgetLimit - totalSpentToday).coerceAtLeast(0.0)

    val budgetPercentage: Float
        get() = if (dailyBudgetLimit <= 0) 1f else (totalSpentToday / dailyBudgetLimit).toFloat().coerceIn(0f, 1f)

    val formattedTotalSpent: String
        get() = String.format("R$ %.2f", totalSpentToday).replace(".", ",")

    val formattedRemaining: String
        get() = String.format("R$ %.2f", remainingBudget).replace(".", ",")

    val formattedLimit: String
        get() = String.format("R$ %.2f", dailyBudgetLimit).replace(".", ",")
}
