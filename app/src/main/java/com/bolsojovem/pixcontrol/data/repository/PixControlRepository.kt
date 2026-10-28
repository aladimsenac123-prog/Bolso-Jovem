package com.bolsojovem.pixcontrol.data.repository

import com.bolsojovem.pixcontrol.data.model.AccountBalance
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.data.model.SavingGoal
import com.bolsojovem.pixcontrol.data.model.Transaction
import com.bolsojovem.pixcontrol.data.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

interface PixControlRepository {
    val balanceFlow: StateFlow<AccountBalance>
    val transactionsFlow: StateFlow<List<Transaction>>
    val goalsFlow: StateFlow<List<SavingGoal>>

    fun addExpense(title: String, amount: Double, category: ExpenseCategory)
    fun sendPix(title: String, amount: Double, pixKey: String, note: String?)
    fun deleteTransaction(transactionId: String)
    fun contributeToGoal(goalId: String, amount: Double)
    fun createGoal(name: String, targetAmount: Double, emoji: String, deadlineMonth: String)
}

class PixControlRepositoryImpl : PixControlRepository {

    private val _transactions = MutableStateFlow<List<Transaction>>(
        listOf(
            Transaction(
                title = "Açaí do Senac",
                amount = 16.50,
                category = ExpenseCategory.FOOD,
                type = TransactionType.EXPENSE,
                timestamp = LocalDateTime.now().minusHours(2)
            ),
            Transaction(
                title = "Metrô Ida/Volta",
                amount = 9.80,
                category = ExpenseCategory.TRANSPORT,
                type = TransactionType.EXPENSE,
                timestamp = LocalDateTime.now().minusHours(4)
            ),
            Transaction(
                title = "Impressão de Apostila",
                amount = 7.50,
                category = ExpenseCategory.EDUCATION,
                type = TransactionType.EXPENSE,
                timestamp = LocalDateTime.now().minusHours(6)
            )
        )
    )
    override val transactionsFlow: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _goals = MutableStateFlow<List<SavingGoal>>(
        listOf(
            SavingGoal(
                name = "Setup Dev / Teclado Mecânico",
                targetAmount = 450.0,
                currentAmount = 280.0,
                emoji = "⌨️",
                deadlineMonth = "Nov 2026"
            ),
            SavingGoal(
                name = "Curso Certificação Android",
                targetAmount = 300.0,
                currentAmount = 210.0,
                emoji = "📱",
                deadlineMonth = "Dez 2026"
            ),
            SavingGoal(
                name = "Reserva de Emergência",
                targetAmount = 1000.0,
                currentAmount = 420.0,
                emoji = "🛡️",
                deadlineMonth = "Jan 2027"
            )
        )
    )
    override val goalsFlow: StateFlow<List<SavingGoal>> = _goals.asStateFlow()

    private val _balance = MutableStateFlow(
        recalculateBalance(_transactions.value)
    )
    override val balanceFlow: StateFlow<AccountBalance> = _balance.asStateFlow()

    override fun addExpense(title: String, amount: Double, category: ExpenseCategory) {
        val newTx = Transaction(
            title = title,
            amount = amount,
            category = category,
            type = TransactionType.EXPENSE,
            timestamp = LocalDateTime.now()
        )
        _transactions.update { current -> listOf(newTx) + current }
        _balance.value = recalculateBalance(_transactions.value)
    }

    override fun sendPix(title: String, amount: Double, pixKey: String, note: String?) {
        val newTx = Transaction(
            title = title,
            amount = amount,
            category = ExpenseCategory.PIX_TRANSFER,
            type = TransactionType.PIX_SENT,
            timestamp = LocalDateTime.now(),
            pixKey = pixKey,
            note = note
        )
        _transactions.update { current -> listOf(newTx) + current }
        _balance.value = recalculateBalance(_transactions.value)
    }

    override fun deleteTransaction(transactionId: String) {
        _transactions.update { current -> current.filterNot { it.id == transactionId } }
        _balance.value = recalculateBalance(_transactions.value)
    }

    override fun contributeToGoal(goalId: String, amount: Double) {
        _goals.update { current ->
            current.map { goal ->
                if (goal.id == goalId) {
                    goal.copy(currentAmount = goal.currentAmount + amount)
                } else goal
            }
        }
        val goalName = _goals.value.find { it.id == goalId }?.name ?: "Cofrinho"
        val newTx = Transaction(
            title = "Aporte: $goalName",
            amount = amount,
            category = ExpenseCategory.SAVINGS,
            type = TransactionType.GOAL_DEPOSIT,
            timestamp = LocalDateTime.now()
        )
        _transactions.update { current -> listOf(newTx) + current }
        _balance.value = recalculateBalance(_transactions.value)
    }

    override fun createGoal(name: String, targetAmount: Double, emoji: String, deadlineMonth: String) {
        val newGoal = SavingGoal(
            name = name,
            targetAmount = targetAmount,
            currentAmount = 0.0,
            emoji = emoji,
            deadlineMonth = deadlineMonth
        )
        _goals.update { it + newGoal }
    }

    private fun recalculateBalance(transactions: List<Transaction>): AccountBalance {
        val todaySpent = transactions
            .filter { it.type == TransactionType.EXPENSE || it.type == TransactionType.PIX_SENT }
            .sumOf { it.amount }

        return AccountBalance(
            totalBalance = 840.0 - todaySpent,
            dailySpent = todaySpent,
            dailyLimit = 50.0,
            monthlyIncome = 1200.0,
            monthlyExpenses = 360.0 + todaySpent
        )
    }
}
