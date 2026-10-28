# Benchmark & Comparativo Técnico: Nexa vs. Bolso Jovem (PixControl)

Este documento apresenta uma análise técnica e de experiência de usuário (UX) comparando o **Nexa** (desenvolvido por Clayton Reis / @notyalC0) com o **Bolso Jovem (PixControl)**, destacando onde nos inspiramos e como elevamos o padrão de engenharia mobile.

---

## 1. Quadro Comparativo Direto

| Dimensão | **Nexa** (@notyalC0) | **Bolso Jovem (PixControl)** |
| :--- | :--- | :--- |
| **Tecnologia & Stack** | Flutter (Dart) | **100% Nativo Kotlin + Jetpack Compose** |
| **Gerenciamento de Estado** | Riverpod 3 | **StateFlow + MVI / Unidirectional Data Flow (UDF)** |
| **Estética & Design System** | Minimalista Flat / Material Adaptado | **Apple Human Interface Guidelines (HIG) + Grotesk Zero** |
| **Bordas & Geometria** | Rounded Corners convencionais | **Squircles contínuos (16dp a 24dp) com bordas ultrafinas** |
| **Superfícies & Profundidade** | Cards opacos | **Glassmorphism (translucidez + blur nativo + luz difusa)** |
| **Física de Animações** | Animações implícitas padrão Flutter | **Física de Molas Reais (`Spring.DampingRatioLowBouncy`)** |
| **Feedback Sensorial** | Não enfatizado | **Haptics Nativo (`HapticFeedbackType.LongPress`)** |
| **Regra de Negócio Chave** | Saldo por competência e Metas | **Teto dinâmico de R$ 50,00 com transição de cor instantânea** |
| **Aporte em Metas** | Fluxo de formulário com `goal_id` | **Aporte instantâneo em 1 toque (+R$ 10, +R$ 25, +R$ 50)** |
| **Código-Fonte** | Privado (Apenas APKs distribuídos) | **100% Aberto e versionado no GitHub** |

---

## 2. O que Aprendemos & Nos Inspiramos do Nexa
O projeto Nexa possui excelentes sacadas de produto que incorporamos ao Bolso Jovem:
1. **Aba dedicada de Metas & Cofrinhos:** A ideia de categorizar sonhos de médio prazo com acompanhamento visual de porcentagem e valores restantes.
2. **Separação de Contexto Financeiro:** Distinguir despesas rotineiras de aportes em metas para não desestimular o usuário ao economizar.
3. **Clareza nos Vencimentos:** Consciência de que o jovem precisa saber exatamente o que já foi gasto hoje versus o que sobra para o resto do mês.

---

## 3. Onde Fizemos Melhor (Engenharia Staff Android)
1. **Zero Sobrecarga de Runtime:** O Bolso Jovem é compilado diretamente para código nativo Android com o novo compilador Kotlin K2, eliminando os drops de frame que podem ocorrer na thread de renderização do Flutter.
2. **Regra de Impacto Visual para Apresentações (Efeito UAU):** A transição de cor do `HeroSummaryCard` (de Verde Esmeralda `#00D09E` para Vermelho Alerta `#EF4444` no exato momento em que os gastos do dia ultrapassam R$ 50,00) fornece feedback visual imediato e gamificado.
3. **Acabamento Cupertino de Alta Fidelidade:** Menus estilo Inset Grouped, tipografia com tracking levemente negativo (`-0.03em`) e numerais tabulares que não dançam na tela quando os centavos são atualizados.
