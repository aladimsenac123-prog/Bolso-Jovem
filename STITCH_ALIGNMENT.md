# Google Stitch ↔ Google Antigravity ↔ Android Studio

## Pipeline de Comunicação e Fidelidade de Design System

Este documento registra a integração direta entre o **Google Stitch** (motor de UI/UX e Design System), o **Google Antigravity** (orquestrador sênior e agente de código) e o **Android Studio** (ambiente de compilação e execução nativa Jetpack Compose).

```
┌─────────────────────────────────────────────────────────┐
│                      GOOGLE STITCH                      │
│   Projeto: projects/9830507317487906579                 │
│   • Tokens de Cores Material Design 3                   │
│   • Telas Mobile: Controle Diário, Metas, Extrato       │
│   • Componentes: Hero Card, Quick Form, Gamification    │
└───────────────────────────┬─────────────────────────────┘
                            │  MCP Protocol (call_mcp_tool)
                            │  download_file / list_screens
┌───────────────────────────▼─────────────────────────────┐
│                    GOOGLE ANTIGRAVITY                   │
│   Orquestrador Sênior & Agente de Engenharia            │
│   • Tradução 1:1 de HTML/Tailwind para Jetpack Compose │
│   • Manutenção de estado MVI imutável (StateFlow)       │
│   • CI/CD Local contínuo com push para GitHub           │
└───────────────────────────┬─────────────────────────────┘
                            │  Gradle 9.3 + Kotlin 2.0.21
                            │  ADB Wireless Debugging
┌───────────────────────────▼─────────────────────────────┐
│                     ANDROID STUDIO                      │
│   • Sincronização Gradle automática (Theme.kt, Color.kt)│
│   • Execução em hardware real (Motorola moto g(20))     │
│   • 0 warnings, 0 crashes, 60/120 fps nativo            │
└─────────────────────────────────────────────────────────┘
```

---

## 1. Mapeamento de Telas do Stitch para o Android Studio

| Tela Stitch | Screen ID Stitch | Arquivo Nativo no Android Studio |
|---|---|---|
| **Controle Diário PixControl (UI/UX Pro)** | `6e063e69b5844fd6a2615c80868530f7` | `ui/screens/BolsoJovemDashboardScreen.kt` |
| **Metas & Caixinhas Pix** | `3a653d2dddcf40bd88357588c9e4c9b5` | `ui/screens/GoalsScreen.kt` |
| **Extrato & Histórico Pix** | `c43c3341a2664e4fb5ad86848df74ab3` | `ui/components/TransactionItemCard.kt` |
| **Dica Pix & Gamificação** | Componente integrado do Stitch | `ui/components/StitchPixTipBanner.kt` |
| **Barra de Navegação Cupertino** | Shared Component do Stitch | `ui/components/CupertinoTabBar.kt` |

---

## 2. Fidelidade Estética Implementada
1. **Tokens de Cores Exatos**:
   - `Primary`: `#006C50`
   - `Primary Container (Pix Mint)`: `#00D09E`
   - `Tertiary (Alert Over-Budget)`: `#B91A24` / `#EF4444`
   - `Surface (Dark Obsidian)`: `#0B0F19` & `#161F30`
2. **Squircles e Cantos Contínuos**:
   - Cartões elevados com raio de 16dp a 24dp e bordas translúcidas de 1px.
3. **Regra de Ouro Dinâmica**:
   - Transição animada de cor e ícone quando o limite diário de R$ 50,00 é atingido ou ultrapassado.
