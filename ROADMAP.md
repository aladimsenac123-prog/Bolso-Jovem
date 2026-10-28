# Bolso Jovem (PixControl) — Plano Estratégico de Melhorias & Integrações Futuras

## 1. Visão Geral do Roadmap
O **Bolso Jovem (PixControl)** tem como missão ser a principal plataforma de educação e controle financeiro para jovens, universitários e estagiários. Para elevar o aplicativo do estágio atual de MVP nativo para um produto comercial de alta escala, desenhou-se este plano diretor estruturado em 4 pilares: **Integrações de Pagamentos & Open Finance**, **Hardware & Segurança**, **Inteligência Artificial & Gamificação** e **Experiência de Usuário (OS-Level)**.

---

## 2. Matriz de Melhores Integrações

### 2.1. Pagamentos & Open Finance
| Integração | Tecnologia / Parceiro | Benefício para o Usuário | Complexidade |
|---|---|---|:---:|
| **Leitura Instantânea de Pix Copia e Cola & QR Code** | **Google ML Kit Barcode Scanning + CameraX** | Permite ler QR Codes Pix estáticos e dinâmicos em menos de 200ms com mira tátil e feedback háptico. | Média |
| **Agregação Bancária (Open Finance Brasil)** | **Pluggy / Belvo SDK** | Conecta as contas bancárias reais do estudante (Nubank, Inter, PicPay, Mercado Pago) para puxar extratos automaticamente sem lançamento manual. | Média-Alta |
| **Geração de Cobrança Pix & Webhook** | **API Asaas / Celcoin / Gerencianet (Efí)** | Permite receber depósitos, mesadas e rateios de amigos diretamente pelo app via chave Pix gerada dinamicamente. | Alta |

### 2.2. Segurança e Hardware Nativo
| Integração | Tecnologia | Benefício para o Usuário | Complexidade |
|---|---|---|:---:|
| **Biometria Apple-Style (Face/Fingerprint)** | **AndroidX BiometricPrompt API** | Autenticação por impressão digital ou reconhecimento facial antes de autorizar transações acima do limite diário. | Baixa-Média |
| **Criptografia de Hardware** | **Android Keystore + Jetpack Security EncryptedSharedPreferences** | Protege chaves Pix, tokens de autenticação e histórico financeiro local contra extração de dados. | Média |
| **Modo Discrição (Anti-Roubo / Olhar na Rua)** | **Sensors / Proximity / Shake Gesture** | Agitar o celular ou cobrir o sensor para ocultar valores e saldos instantaneamente em locais públicos. | Baixa |

### 2.3. Persistência de Dados & Arquitetura Offline-First
| Integração | Tecnologia | Benefício para o Usuário | Complexidade |
|---|---|---|:---:|
| **Banco de Dados Local com Cache** | **Jetpack Room Database + Flow** | O app funciona 100% offline; todas as movimentações são salvas instantaneamente e sincronizadas quando houver conexão. | Média |
| **Sincronização em Nuvem** | **Supabase / Firebase Firestore & Auth** | Backup em nuvem das metas e histórico com login social (Google Sign-In). | Média |

### 2.4. Inteligência Artificial e UX de Sistema Operacional
| Integração | Tecnologia | Benefício para o Usuário | Complexidade |
|---|---|---|:---:|
| **Classificador Inteligente de Gastos** | **Google Gemini Flash API / Gemini Nano (AICore)** | Usuário dita ou digita em gírias (ex.: *"15 conto no podrão do tio"*), e a IA extrai valor, categoria e data automaticamente. | Média |
| **Widgets de Tela Inicial (Estilo iOS 18)** | **Jetpack Glance (AppWidget Compose)** | Widget translúcido com estado do limite diário (Verde: Seguro / Vermelho: Alerta) e botão de Pix rápido sem abrir o app. | Média |
| **Live Notifications & Alertas em Tempo Real** | **Firebase Cloud Messaging (FCM)** | Notificação push tátil instantânea caso um gasto ameace estourar a meta semanal ou o limite de R$ 50,00. | Baixa-Média |

---

## 3. Plano de Melhorias em Fases (Sprints Futuras)

```
┌────────────────────────────────────────────────────────────────────────┐
│ Fase 1: Fundação Local & Persistência (Sprint 1-2)                      │
│ • Migração de memória volátil para Room DB + DataStore                 │
│ • Validação de segurança com BiometricPrompt (Touch/Face ID)           │
│ • Exportação de relatórios em PDF / Comprovantes compartilháveis       │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼────────────────────────────────────┐
│ Fase 2: Hardware & Câmera Pix (Sprint 3-4)                              │
│ • Integração CameraX + ML Kit para scanner de QR Code Pix              │
│ • Parser do padrão EMVCo BACEN (Copia e Cola com validação de checksum)│
│ • Widgets para tela inicial via Jetpack Glance                         │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼────────────────────────────────────┐
│ Fase 3: Conectividade Real & Open Finance (Sprint 5-6)                 │
│ • Integração com agregador Open Finance (Pluggy / Belvo)               │
│ • Sincronização em nuvem e login seguro social                         │
│ • Módulo de Caixinhas Compartilhadas ("Vaquinhas Estudantis")         │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼────────────────────────────────────┐
│ Fase 4: Inteligência Artificial Financeira (Sprint 7+)                 │
│ • Assistente 'BolsoBot' com Gemini para projeções e alertas de gastos  │
│ • Gamificação com conquistas e badges de educação financeira           │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 4. Métricas de Sucesso (KPIs Técnicos e de Negócio)
- **Tempo de Inicialização a Frio (Cold Start)**: < 600ms (Jetpack Compose Baseline Profiles).
- **Taxa de Conversão de Metas**: Mais de 70% dos usuários atingindo o objetivo do primeiro cofrinho.
- **Redução de Estouro de Orçamento**: Queda de 40% na frequência de gastos acima de R$ 50/dia após 3 semanas de uso das notificações de alerta.
