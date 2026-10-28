---
name: Bolso Jovem (PixControl) — Apple HIG & Grotesk Zero
colors:
  surface: '#faf8ff'
  surface-dim: '#d2d9f4'
  surface-bright: '#faf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f3ff'
  surface-container: '#eaedff'
  surface-container-high: '#e2e7ff'
  surface-container-highest: '#dae2fd'
  on-surface: '#131b2e'
  on-surface-variant: '#3b4a43'
  inverse-surface: '#283044'
  inverse-on-surface: '#eef0ff'
  outline: '#6b7b73'
  outline-variant: '#bacac1'
  surface-tint: '#006c50'
  primary: '#006c50'
  on-primary: '#ffffff'
  primary-container: '#00d09e'
  on-primary-container: '#00533d'
  inverse-primary: '#2fe0ac'
  secondary: '#006398'
  on-secondary: '#ffffff'
  secondary-container: '#5bb8fe'
  on-secondary-container: '#00476e'
  tertiary: '#ef4444'
  on-tertiary: '#ffffff'
  tertiary-container: '#ff9d96'
  on-tertiary-container: '#960014'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  background: '#faf8ff'
  on-background: '#131b2e'
  surface-variant: '#dae2fd'
typography:
  display:
    fontFamily: Plus Jakarta Sans
    fontSize: 2.25rem
    fontWeight: '800'
    lineHeight: 2.75rem
    letterSpacing: -0.03em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 1.75rem
    fontWeight: '700'
    lineHeight: 2.25rem
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 1.25rem
    fontWeight: '700'
    lineHeight: 1.75rem
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 1rem
    fontWeight: '500'
    lineHeight: 1.5rem
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 0.875rem
    fontWeight: '400'
    lineHeight: 1.25rem
  label-md:
    fontFamily: Space Grotesk
    fontSize: 0.75rem
    fontWeight: '600'
    lineHeight: 1rem
    letterSpacing: 0.04em
  label-sm:
    fontFamily: Space Grotesk
    fontSize: 0.6875rem
    fontWeight: '500'
    lineHeight: 0.875rem
    letterSpacing: 0.05em
rounded:
  sm: 0.5rem
  DEFAULT: 0.75rem
  md: 1rem
  lg: 1.25rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  margin: 1rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

# Bolso Jovem (PixControl) — Design System (Apple HIG + Grotesk Zero)

## 1. Visão Geral e Filosofia Visual
O **Bolso Jovem (PixControl)** funde o rigor funcional e a estética refinada do **Apple Human Interface Guidelines (Cupertino / iOS 18)** com o dinamismo tipográfico e o alto contraste editorial do **Grotesk Zero**.

Construído para jovens, estudantes e estagiários que precisam de controle financeiro diário e operações Pix sem atrito:
- **Superfícies & Profundidade (Glassmorphism):** Camadas translúcidas com efeito de vidro fosco (`graphicsLayer` + blur), bordas ultrafinas (`0.5dp` a `1dp`) e sombras ambientais difusas.
- **Geometria & Squircles:** Cantos arredondados contínuos de `16dp` a `24dp` em cards e recipientes, proporcionando fluidez tátil e sensação ergonômica.
- **Microinterações & Física de Mola:** Feedback tátil háptico (`HapticFeedbackType.LongPress` / `TextHandleMove`) e animações baseadas em molas naturais (`Spring.DampingRatioLowBouncy`).

---

## 2. Paleta de Cores e Tokens

### Cores Principais
- **Pix Mint (`#00D09E`)**: Cor primária de destaque, ativação do Pix e saldo saudável.
- **Deep Cobalt (`#0284C7`)**: Cor secundária para categorias bancárias, extratos e metadados.
- **Safe Emerald (`#10B981`)**: Indicador de orçamento dentro do limite diário ($\le \text{R\$} 50{,}00$).
- **Alert Crimson (`#EF4444`)**: Indicador de alerta de gastos diários excedidos ($> \text{R\$} 50{,}00$).

### Superfícies e Fundos
- **Canvas Light:** `#F8FAFC` (iOS System Grouped Background)
- **Canvas Dark:** `#0B0F19` (Obsidian iOS Dark Surface)
- **Card Surface Light:** `#FFFFFF` com borda `rgba(22, 21, 19, 0.08)`
- **Card Surface Dark:** `#161F30` com borda `rgba(255, 255, 255, 0.08)`
- **Bone Paper (`#F4F1EA`)**: Herança do Grotesk Zero para elementos de recibo e nota.
- **Ink Stage (`#161513`)**: Contraste absoluto para textos e modo noturno.

---

## 3. Tipografia
- **Títulos e Display:** `Plus Jakarta Sans` com tracking levemente negativo (`-0.03em`) e pesos Bold (`700`) e ExtraBold (`800`).
- **Corpo e Controles:** `Plus Jakarta Sans` Regular (`400`) e Medium (`500`).
- **Valores Monetários & Chaves Pix:** `Space Grotesk` com numerais tabulares (`tabular figures`) para legibilidade cirúrgica.

---

## 4. Componentes Chave
1. **HigCard (Squircle Inset):** Recipiente elevado com cantos de `20dp` e borda sutil.
2. **DynamicBudgetBanner:** Banner de destaque cujo fundo e ícone transitam suavemente entre Verde Seguro e Vermelho Alerta ao ultrapassar R$ 50,00 de gastos diários.
3. **PixQuickForm:** Formulário minimalista de 1 toque (Descrição + Valor R$) com validação reativa e teclado numérico automático.
4. **TransactionListItem:** Item de lista estilo iOS Inset Grouped com ícone de categoria em squircle, valor com numerais tabulares e ação de remoção rápida com feedback tátil.
