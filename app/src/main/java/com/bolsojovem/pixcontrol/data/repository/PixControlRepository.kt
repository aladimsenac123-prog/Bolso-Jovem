package com.bolsojovem.pixcontrol.data.repository

import com.bolsojovem.pixcontrol.data.model.AccountSummary
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface PixControlRepository {
    fun getTransactions(): Flow<List<Transaction>>
    fun getAccountSummary(): Flow<AccountSummary>
    suspend fun addTransaction(title: String, amount: Double, category: ExpenseCategory)
    suspend fun deleteTransaction(id: String)
    suspend fun updateDailyLimit(newLimit: Double)
}

class InMemoryPixControlRepository : PixControlRepository {

    private val timeFormat = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    private val initialTransactions = mutableListOf(
        Transaction(
            title = "Açaí Natural",
            amount = 18.00,
            category = ExpenseCategory.FOOD,
            timestamp = "15:42"
        ),
        Transaction(
            title = "Lanche da Tarde",
            amount = 14.50,
            category = ExpenseCategory.FOOD,
            timestamp = "12:15"
        ),
        Transaction(
            title = "Recarga de Celular",
            amount = 10.00,
            category = ExpenseCategory.SERVICES,
            timestamp = "09:30"
        )
    )

    private val _transactionsFlow = MutableStateFlow<List<Transaction>>(initialTransactions)
    private val _dailyLimitFlow = MutableStateFlow(50.00)

    override fun getTransactions(): Flow<List<Transaction>> = _transactionsFlow.asStateFlow()

    override fun getAccountSummary(): Flow<AccountSummary> {
        return _transactionsFlow.map { list ->
            val total = list.sumOf { it.amount }
            AccountSummary(
                dailyBudgetLimit = _dailyLimitFlow.value,
                totalSpentToday = total,
                transactionCount = list.size
            )
        }
    }

    override suspend fun addTransaction(title: String, amount: Double, category: ExpenseCategory) {
        val newTx = Transaction(
            title = title.trim(),
            amount = amount,
            category = category,
            timestamp = timeFormat.format(Date())
        )
        val updated = mutableListOf(newTx).apply { addAll(_transactionsFlow.value) }
        _transactionsFlow.value = updated
    }

    override suspend fun deleteTransaction(id: String) {
        val updated = _transactionsFlow.value.filterNot { it.id == id }
        _transactionsFlow.value = updated
    }

    override suspend fun updateDailyLimit(newLimit: Double) {
        _dailyLimitFlow.value = newLimit
        // Trigger re-emission
        _transactionsFlow.value = _transactionsFlow.value.toList()
    }
}
