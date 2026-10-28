package com.bolsojovem.pixcontrol.data.model

enum class ExpenseCategory(
    val displayName: String,
    val iconName: String,
    val defaultColorHex: String
) {
    FOOD("Alimentação", "restaurant", "#10B981"),
    TRANSPORT("Transporte", "directions_bus", "#0284C7"),
    LEISURE("Lazer", "sports_esports", "#8B5CF6"),
    STUDIES("Estudos", "school", "#F59E0B"),
    SERVICES("Serviços & Pix", "bolt", "#00D09E"),
    OTHER("Outros", "category", "#64748B")
}
