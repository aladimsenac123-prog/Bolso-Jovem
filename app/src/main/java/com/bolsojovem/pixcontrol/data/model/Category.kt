package com.bolsojovem.pixcontrol.data.model

enum class ExpenseCategory(
    val displayName: String,
    val iconName: String,
    val defaultColorHex: String = "#0A84FF"
) {
    FOOD("Alimentação", "restaurant", "#10B981"),
    TRANSPORT("Transporte", "directions_bus", "#0284C7"),
    EDUCATION("Educação", "school", "#F59E0B"),
    LEISURE("Lazer", "sports_esports", "#8B5CF6"),
    SHOPPING("Compras", "shopping_bag", "#EC4899"),
    PIX_TRANSFER("Transferência Pix", "bolt", "#30D158"),
    SAVINGS("Cofrinho", "savings", "#14B8A6"),
    STUDIES("Estudos", "school", "#F59E0B"),
    SERVICES("Serviços & Pix", "bolt", "#00D09E"),
    OTHER("Outros", "category", "#64748B");

    val label: String get() = displayName
}
