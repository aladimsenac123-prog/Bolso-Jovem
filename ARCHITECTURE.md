# Bolso Jovem (PixControl) — Arquitetura e Engenharia de Software

## 1. Visão Executiva e Posicionamento
O **Bolso Jovem (PixControl)** é uma aplicação mobile nativa Android desenvolvida em **Kotlin & Jetpack Compose**, projetada para capacitar jovens, universitários e estagiários no controle inteligente de suas finanças, gestão de bolsas/mesadas e pagamentos instantâneos via Pix com fricção zero.

A interface une o rigor funcional das **Apple Human Interface Guidelines (HIG)** (segmentações translúcidas, cartões em squircle, tipografia com alto peso visual e microinterações táteis) aos conceitos editoriais do design system **Grotesk Zero**.

---

## 2. Padrões Arquiteturais

```
┌────────────────────────────────────────────────────────┐
│                    UI Layer (Compose)                  │
│   Screens (Dashboard, Pix, Goals, Statement, Settings) │
│   ViewModels (StateFlow / UIEvents / UIState)          │
│   Components & HIG Design Tokens                       │
└───────────────────────────┬────────────────────────────┘
                            │
┌───────────────────────────▼────────────────────────────┐
│                   Domain Layer                         │
│   UseCases (SendPixUseCase, CalculateGoalsUseCase)     │
│   Domain Entities & Business Rules                     │
└───────────────────────────┬────────────────────────────┘
                            │
┌───────────────────────────▼────────────────────────────┐
│                    Data Layer                          │
│   PixControlRepository (Interface & Impl)              │
│   Data Sources (Local Mock, Room, Remote API Client)   │
└────────────────────────────────────────────────────────┘
```

### Princípios Adotados:
- **Clean Architecture & Unidirectional Data Flow (UDF)**: Estado flui de cima para baixo; eventos fluem de baixo para cima.
- **Single Source of Truth**: Repositório centraliza cache e fontes de dados.
- **Imutabilidade de Estado**: Telas consom estados imutáveis (`data class UiState`).

---

## 3. Estrutura de Pacotes Proposta

```
com.bolsojovem.pixcontrol/
├── data/
│   ├── model/
│   │   ├── Transaction.kt
│   │   ├── PixKey.kt
│   │   ├── AccountBalance.kt
│   │   ├── SavingGoal.kt
│   │   └── Category.kt
│   └── repository/
│       ├── PixControlRepository.kt
│       └── PixControlRepositoryImpl.kt
├── domain/
│   ├── usecase/
│   │   ├── SendPixUseCase.kt
│   │   ├── GetTransactionsUseCase.kt
│   │   └── ManageGoalUseCase.kt
│   └── model/
├── ui/
│   ├── components/
│   │   ├── HigCard.kt
│   │   ├── HigSegmentedControl.kt
│   │   ├── HigButton.kt
│   │   ├── BalanceDisplay.kt
│   │   └── TransactionListItem.kt
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   ├── Shape.kt
│   │   └── Theme.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── screens/
│       ├── dashboard/
│       ├── pix/
│       ├── goals/
│       ├── statement/
│       └── settings/
└── MainActivity.kt
```

---

## 4. Matriz de Cores e Tokens Visuais (Apple HIG + Grotesk Zero)

| Token | Valor Hex | Finalidade |
|---|---|---|
| `Primary` | `#0A84FF` | Ações principais, destaques ativos (Apple System Blue) |
| `PixGreen` | `#30D158` | Ícone Pix, entradas financeiras, badges de sucesso |
| `ExpenseRed` | `#FF453A` | Saídas e débitos no extrato |
| `BackgroundLight` | `#F4F1EA` | Fundo quente editorial (Grotesk Zero Bone) |
| `BackgroundDark` | `#0D0E11` | Fundo modo escuro (Deep Obsidian) |
| `CardSurface` | `#FFFFFF` / `#1C1C1E` | Superfícies com raio de curvatura 20dp |
| `TextPrimary` | `#111111` / `#F5F5F7` | Títulos e números em destaque |

---

## 5. Fluxos de Entrega e CI/CD Local
- **Auto-Deploy Watcher**: Sincronização automática para GitHub via `auto_deploy.ps1`.
- **Branch Oficial**: `main`.
- **Repositório**: `https://github.com/aladimsenac123-prog/Bolso-Jovem.git`.
