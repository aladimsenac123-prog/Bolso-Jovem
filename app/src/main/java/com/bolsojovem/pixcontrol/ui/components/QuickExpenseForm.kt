package com.bolsojovem.pixcontrol.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bolsojovem.pixcontrol.data.model.ExpenseCategory
import com.bolsojovem.pixcontrol.ui.theme.AlertCrimson
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimary
import com.bolsojovem.pixcontrol.ui.theme.StitchPrimaryContainer

@Composable
fun QuickExpenseForm(
    description: String,
    amount: String,
    selectedCategory: ExpenseCategory,
    errorMessage: String?,
    onDescriptionChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategorySelect: (ExpenseCategory) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val cardBg = if (isDark) Color(0xFF161F30) else Color(0xFFFFFFFF)
    val cardBorder = if (isDark) Color.White.copy(alpha = 0.08f) else Color(0xFFBACAC1).copy(alpha = 0.35f)
    val inputBg = if (isDark) Color(0xFF1E293B) else Color(0xFFFFFFFF)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(cardBg)
            .border(1.dp, cardBorder, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(StitchPrimaryContainer.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = if (isDark) StitchPrimaryContainer else StitchPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Novo Gasto Rápido",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Badge Pix Instantâneo
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isDark) Color(0xFF1E293B) else Color(0xFFEAEDFF))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Pix Instantâneo",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Campo 1: Descrição do Gasto
            Text(
                text = "Descrição do gasto",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholder = {
                    Text("Ex: Café, Açaí, Uber...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = StitchPrimaryContainer,
                    unfocusedBorderColor = cardBorder,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Campo 2: Valor R$
            Text(
                text = "Valor R$",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                placeholder = {
                    Text("0,00", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                },
                leadingIcon = {
                    Text(
                        text = "R$",
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isDark) StitchPrimaryContainer else StitchPrimary,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = StitchPrimaryContainer,
                    unfocusedBorderColor = cardBorder,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onSubmit()
                    }
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Chips (Stitch Style)
            Text(
                text = "Categoria",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    ExpenseCategory.FOOD,
                    ExpenseCategory.TRANSPORT,
                    ExpenseCategory.LEISURE,
                    ExpenseCategory.SERVICES,
                    ExpenseCategory.EDUCATION
                ).forEach { cat ->
                    val isSelected = cat == selectedCategory
                    val chipBg = if (isSelected) {
                        StitchPrimary
                    } else {
                        if (isDark) Color(0xFF1E293B) else Color(0xFFFFFFFF)
                    }
                    val chipText = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    val chipBorder = if (isSelected) StitchPrimary else cardBorder

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(chipBg)
                            .border(1.dp, chipBorder, CircleShape)
                            .clickable { onCategorySelect(cat) }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = cat.displayName,
                            color = chipText,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            // Error validation banner
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AlertCrimson.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = AlertCrimson,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = errorMessage,
                        color = AlertCrimson,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // CTA Button (Stitch Mint Style)
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onSubmit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = StitchPrimaryContainer,
                    contentColor = Color(0xFF002116)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Adicionar Gasto",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
