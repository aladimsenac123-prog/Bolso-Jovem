package com.bolsojovem.pixcontrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolsojovem.pixcontrol.data.model.AccountSummary
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.data.model.Transaction
import com.bolsojovem.pixcontrol.data.repository.PixControlRepository
import com.bolsojovem.pixcontrol.data.repository.PixControlRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class DashboardUiState(
    val summary: AccountSummary = AccountSummary(),
    val transactions: List<Transaction> = emptyList(),
    val inputDescription: String = "",
    val inputAmount: String = "",
    val selectedCategory: ExpenseCategory = ExpenseCategory.FOOD,
    val errorMessage: String? = null,
    val isSuccessAnimationActive: Boolean = false
)

class PixControlViewModel(
    private val repository: PixControlRepository = PixControlRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.balanceFlow,
                repository.transactionsFlow
            ) { balance, transactions ->
                val summary = AccountSummary(
                    dailyBudgetLimit = balance.dailyLimit,
                    totalSpentToday = balance.dailySpent,
                    transactionCount = transactions.size
                )
                Pair(summary, transactions)
            }.collect { (summary, transactions) ->
                _uiState.value = _uiState.value.copy(
                    summary = summary,
                    transactions = transactions
                )
            }
        }
    }

    fun onDescriptionChanged(newVal: String) {
        _uiState.value = _uiState.value.copy(
            inputDescription = newVal,
            errorMessage = null
        )
    }

    fun onAmountChanged(newVal: String) {
        val filtered = newVal.filter { it.isDigit() || it == ',' || it == '.' }
        _uiState.value = _uiState.value.copy(
            inputAmount = filtered,
            errorMessage = null
        )
    }

    fun onCategorySelected(category: ExpenseCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun addExpense(): Boolean {
        val desc = _uiState.value.inputDescription.trim()
        val rawAmount = _uiState.value.inputAmount.trim().replace(",", ".")

        if (desc.isEmpty()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Preencha a descrição do gasto!")
            return false
        }

        val amount = rawAmount.toDoubleOrNull()
        if (amount == null || amount <= 0.0) {
            _uiState.value = _uiState.value.copy(errorMessage = "Informe um valor válido em R$!")
            return false
        }

        viewModelScope.launch {
            repository.addExpense(
                title = desc,
                amount = amount,
                category = _uiState.value.selectedCategory
            )
            _uiState.value = _uiState.value.copy(
                inputDescription = "",
                inputAmount = "",
                errorMessage = null,
                isSuccessAnimationActive = true
            )
        }
        return true
    }

    fun deleteExpense(id: String) {
        viewModelScope.launch {
            repository.deleteTransaction(id)
        }
    }
}
