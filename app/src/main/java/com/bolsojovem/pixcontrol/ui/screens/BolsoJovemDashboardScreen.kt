package com.bolsojovem.pixcontrol.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.ui.components.CupertinoTabBar
import com.bolsojovem.pixcontrol.ui.components.NavItem
import com.bolsojovem.pixcontrol.ui.components.HeroSummaryCard
import com.bolsojovem.pixcontrol.ui.components.QuickExpenseForm
import com.bolsojovem.pixcontrol.ui.components.TopBar
import com.bolsojovem.pixcontrol.ui.components.TransactionItemCard
import com.bolsojovem.pixcontrol.ui.theme.DarkBackground
import com.bolsojovem.pixcontrol.ui.theme.PixMint
import com.bolsojovem.pixcontrol.ui.theme.TextPrimaryDark
import com.bolsojovem.pixcontrol.ui.theme.TextSecondaryDark
import com.bolsojovem.pixcontrol.viewmodel.PixControlViewModel
import kotlinx.coroutines.launch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.border
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.ui.theme.DarkBorderHairline
import com.bolsojovem.pixcontrol.ui.theme.DarkSurfaceInput

import com.bolsojovem.pixcontrol.ui.components.QuickTipsBanner

@Composable
fun BolsoJovemDashboardScreen(
    viewModel: PixControlViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateTab: (NavItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var filterCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    val filteredTransactions = remember(uiState.transactions, filterCategory) {
        if (filterCategory == null) uiState.transactions
        else uiState.transactions.filter { it.category == filterCategory }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBar()
        },
        bottomBar = {
            CupertinoTabBar(
                selectedItem = NavItem.Home,
                onItemSelected = onNavigateTab
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Card Hero de Resumo Diário (Muda de cor com teto de R$ 50)
            item(key = "hero_summary") {
                HeroSummaryCard(summary = uiState.summary)
            }

            // 2. Formulário Rápido de Lançamento
            item(key = "quick_form") {
                QuickExpenseForm(
                    description = uiState.inputDescription,
                    amount = uiState.inputAmount,
                    selectedCategory = uiState.selectedCategory,
                    errorMessage = uiState.errorMessage,
                    onDescriptionChange = { viewModel.onDescriptionChanged(it) },
                    onAmountChange = { viewModel.onAmountChanged(it) },
                    onCategorySelect = { viewModel.onCategorySelected(it) },
                    onSubmit = {
                        val success = viewModel.addExpense()
                        if (success) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Gasto cadastrado com sucesso!")
                            }
                        }
                    }
                )
            }

            // 3. Cabeçalho da Seção Extrato de Hoje
            item(key = "feed_header") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Extrato de Hoje",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimaryDark,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(PixMint.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${filteredTransactions.size} itens",
                                    color = PixMint,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Chips de Filtro Rápido
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Opção "Todas"
                        val isAllSelected = filterCategory == null
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isAllSelected) PixMint else DarkSurfaceInput)
                                .border(
                                    width = 1.dp,
                                    color = if (isAllSelected) PixMint else DarkBorderHairline,
                                    shape = CircleShape
                                )
                                .clickable { filterCategory = null }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Todas",
                                color = if (isAllSelected) DarkBackground else TextSecondaryDark,
                                fontSize = 12.sp,
                                fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }

                        // Categorias dinâmicas
                        listOf(
                            ExpenseCategory.FOOD,
                            ExpenseCategory.TRANSPORT,
                            ExpenseCategory.EDUCATION,
                            ExpenseCategory.LEISURE,
                            ExpenseCategory.PIX_TRANSFER
                        ).forEach { cat ->
                            val isSelected = filterCategory == cat
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (isSelected) PixMint else DarkSurfaceInput)
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) PixMint else DarkBorderHairline,
                                        shape = CircleShape
                                    )
                                    .clickable { filterCategory = if (isSelected) null else cat }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = cat.displayName,
                                    color = if (isSelected) DarkBackground else TextSecondaryDark,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // 4. Lista de Transações Cadastradas
            if (filteredTransactions.isEmpty()) {
                item(key = "empty_state") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (filterCategory != null) "Nenhum gasto nesta categoria." else "Nenhum gasto cadastrado hoje. Você está economizando!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondaryDark
                        )
                    }
                }
            } else {
                items(
                    items = filteredTransactions,
                    key = { it.id }
                ) { tx ->
                    TransactionItemCard(
                        transaction = tx,
                        onDelete = { id ->
                            viewModel.deleteExpense(id)
                            scope.launch {
                                snackbarHostState.showSnackbar("Gasto removido!")
                            }
                        }
                    )
                }
            }

            // 5. Quick Tips Banner (Fiel ao Google Stitch)
            item(key = "quick_tips") {
                QuickTipsBanner()
            }

            // Espaçamento final
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
