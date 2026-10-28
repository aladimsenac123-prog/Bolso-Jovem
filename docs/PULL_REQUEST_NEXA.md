# Sugestão de Pull Request / Issue para o Repositório do Nexa

**Repositório Destino:** [notyalC0/nexa-app](https://github.com/notyalC0/nexa-app)  
**Autor:** @aladimsenac123-prog  
**Projeto Relacionado:** [Bolso-Jovem (PixControl)](https://github.com/aladimsenac123-prog/Bolso-Jovem.git)

---

### Título Sugerido:
`💡 Feedback de Engenharia, Inspiração Mútua & Benchmark: Nexa vs. Bolso Jovem (PixControl)`

---

### Conteúdo do Pull Request / Issue:

Fala Clayton (@notyalC0), tudo beleza? 👋

Passei para dar uma olhada detalhada no repositório do **Nexa** e queria te parabenizar! O trabalho que você fez na arquitetura com Flutter + Riverpod 3, especialmente na aba de **Metas**, no cálculo de competência dos cartões e na organização das releases, está de altíssimo nível.

Inspirado nas soluções de produto do Nexa, eu desenvolvi um novo aplicativo mobile chamado **Bolso Jovem (PixControl)**, voltado especificamente para o público jovem/estudantil que precisa gerenciar mesadas, gastos diários e Pix instantâneo:
🔗 **Repositório do Bolso Jovem:** [https://github.com/aladimsenac123-prog/Bolso-Jovem](https://github.com/aladimsenac123-prog/Bolso-Jovem)

---

### 🔍 O que aproveitei de inspiração do Nexa:
1. **Estrutura de Metas & Cofrinhos:** Adicionamos uma aba dedicada de metas com barras de progresso contínuas e cálculo de prazo estimado.
2. **Separação de Aportes vs. Despesas:** Garantir que o dinheiro guardado não seja visto como gasto punitivo.

---

### 🚀 O que implementei no Bolso Jovem e gostaria de te sugerir como melhoria para o Nexa:
1. **Design System com Estética Apple HIG (Cupertino):**
   - Utilizamos cantos suaves em squircle contínuo (`16dp` a `24dp`) e superfícies com *glassmorphism* translúcido. No Flutter, isso pode ser alcançado com `BackdropFilter` refinado e `ClipRRect(borderRadius: BorderRadius.circular(...))`.
2. **Regra de Teto de Gastos com Alerta Cromático Dinâmico:**
   - No Bolso Jovem, criamos uma regra onde o card principal muda suavemente de cor (Verde Esmeralda $\le \text{R\$} 50{,}00$ para Vermelho Alerta $> \text{R\$} 50{,}00$). Essa microinteração causa um impacto visual imediato para evitar compras por impulso.
3. **Aportes em 1 Toque com Feedback Háptico:**
   - Em vez de abrir um formulário completo toda vez, incluímos atalhos rápidos de aporte (+R$ 10, +R$ 25, +R$ 50) com feedback vibratório suave (`HapticFeedbackType`).
4. **Tipografia com Numerais Tabulares (`tabular figures`):**
   - Para valores financeiros e chaves Pix, fixar a largura dos números impede que a interface "trema" quando os valores são alterados.

---

Dá uma olhada no projeto lá em [https://github.com/aladimsenac123-prog/Bolso-Jovem](https://github.com/aladimsenac123-prog/Bolso-Jovem) quando puder! Seria muito massa trocarmos uma ideia sobre as diferenças de abordagem entre **Flutter (Riverpod)** e **Android Nativo (Jetpack Compose + Clean MVI)**.

Parabéns pelo projeto do Nexa! 🚀
