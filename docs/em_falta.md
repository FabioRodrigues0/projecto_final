# Em Falta — Visual + Backend

Mapa do que falta implementar por view. Base: comparação entre screenshots alvo (LifeBinder+) e estado atual do código.

---

## Dashboard (`views/DashboardView.java` + `viewModels/DashboardViewModel.java`)

### Visual
- [ ] Botão **"Ativar Notificações"** no canto superior direito do título.
- [ ] **Ícones** dentro dos 4 cards de contadores (documento / carro / cartão / alerta).
- [ ] Card **Subscrições**: subtexto com custo mensal total (ex.: `€83.44/mês`).
- [ ] Card **Alertas**: subtexto com breakdown (ex.: `4 expirados · 1 urgentes`).
- [ ] Ícone de aviso (⚠) ao lado de "Próximas Expirações (30 dias)".
- [ ] Cards de atalho do fundo (Gerir Documentos / Veículos / Subscrições) precisam de ícone + estilo hover/clicável.
- [ ] Sidebar: falta entrada **Calendário** (5º item).
- [ ] Acertar paleta para a do alvo (sidebar branca, cards com sombra subtil, fundo `#f7f8fa`).

### Backend / Queries
- [ ] `carregarDocumentos()` faz `COUNT` em `pessoas` em vez de documentos — corrigir para somar `documentos_pessoal + documentos_veiculo + documentos_subscricao` ou só `documentos_pessoal` (decidir semântica).
- [ ] `qntAlertas` nunca é carregado — falta método `carregarAlertas()` (contar expirações com `diasReais <= X`).
- [ ] Falta cálculo do **custo total mensal** das subscrições ativas para o subtexto do card.
- [ ] Falta breakdown de alertas (expirados vs urgentes) para subtexto do card.
- [ ] Falta flag/coluna "ativa" nas subscrições para filtrar inativas nos contadores.

---

## Documentos (`views/DocumentosView.java` + `viewModels/DocumentosViewModel.java`)

Estado: só renderiza título. **Tudo o resto em falta.**

### Model
- ✓ `models/Pessoal/DocumentosPessoal.java` existe.

### Visual
- [ ] Botão **"+ Novo Documento"** no topo direito.
- [ ] **Tabs de filtro**: Todos / Garantia / Contrato / Fatura / Outro.
- [ ] **Lista** de cards de documento com:
  - barra colorida lateral por categoria (vermelho expirado, amarelo breve, verde válido)
  - ícone por categoria (fatura, garantia, contrato)
  - título + linha "Categoria · Validade: YYYY-MM-DD"
  - badge de estado à direita (`Expirado` / `Expira Em Breve` / `Válido`)
  - texto de dias (em atraso / restantes)
  - botões editar (lápis) + apagar (lixo)
- [ ] **Modal "Novo Documento"** com campos: Título, Categoria (dropdown), Data Emissão, Data Validade, Notas.
- [ ] Estado vazio.

### Backend / Queries
- [ ] `DocumentosViewModel` está vazio — adicionar:
  - `StateList<DocumentosPessoal> listDocumentos`
  - `State<String> filtroCategoria`
  - `carregarDocumentos()` — SELECT em `documentos_pessoal`.
  - `criar(DocumentosPessoal)` — INSERT.
  - `atualizar(DocumentosPessoal)` — UPDATE por id.
  - `apagar(int id)` — DELETE.
  - `filtrar(TipoDocumentoPessoal)` — re-query ou filtro em memória.

---

## Veículos (`views/VeiculosView.java` + `viewModels/VeiculosViewModel.java`)

Estado: só renderiza título. **Tudo o resto em falta.**

### Model
- ✓ `models/Veiculo/Veiculos.java` + `DocumentosVeiculo.java` existem.

### Visual
- [ ] Botão **"+ Novo Veículo"** no topo direito.
- [ ] **Grid de cards** (3 colunas) por veículo:
  - área de foto no topo (placeholder com ícone de carro se sem foto)
  - botão câmara para upload de foto
  - nome (marca + modelo)
  - linha "Ano · Matrícula"
  - botão **"Ver Documentos →"** (abre detalhe / lista docs do veículo)
  - botões editar + apagar
- [ ] **Modal "Novo Veículo"** com: Marca, Modelo, Ano, Matrícula, Notas.
- [ ] Vista de detalhe / sub-página com documentos do veículo (seguro, IUC, inspeção).
- [ ] Estado vazio.

### Backend / Queries
- [ ] `VeiculosViewModel` está vazio — adicionar:
  - `StateList<Veiculos> listVeiculos`
  - `carregarVeiculos()` — SELECT em `veiculos`.
  - `criar / atualizar / apagar`.
  - `carregarDocumentosVeiculo(int veiculoId)` para sub-vista.
  - upload de foto (guardar path/blob, decidir estratégia).
- [ ] Veiculos só tem campo `nome` — separar em `marca` + `modelo` (model + schema) ou parse string.

---

## Subscrições (`views/SubscricaoView.java` + `viewModels/SubscricaoViewModel.java`)

Estado: só renderiza título. **Tudo o resto em falta.**

### Model
- ✓ `models/Subscricao/Subscricoes.java` + `DocumentosSubscricao.java` existem.
- [ ] Falta campo `custoMensal` (double/BigDecimal) no model `Subscricoes` ou em `DocumentosSubscricao`.
- [ ] Falta campo `categoria` (Streaming / Software / Serviço Online) — `TipoDocumentoSubscricao` existe, validar.
- [ ] Falta campo `ativa` (boolean).
- [ ] Falta campo `plano` (string, ex.: "Premium 4K").

### Visual
- [ ] Botão **"+ Nova Subscrição"** topo direito.
- [ ] **Banner azul** no topo com:
  - "Custo Total Mensal" + `€XX.XX` grande
  - direita: "N subscrições ativas" + `€XX.XX/ano`
- [ ] **Lista** de cards de subscrição com:
  - logo/avatar (círculo com iniciais ou logo do serviço)
  - nome + linha "Categoria · Plano"
  - custo `€X.XX/mês` à direita
  - dias restantes / em atraso por baixo (cor por estado)
  - botões editar + apagar
- [ ] **Modal "Nova Subscrição"**: Serviço, Categoria, Custo Mensal, Plano, Data Renovação, toggle Ativa, Notas.
- [ ] Estado vazio.

### Backend / Queries
- [ ] `SubscricaoViewModel` está vazio — adicionar:
  - `StateList<Subscricoes> listSubscricoes`
  - `State<Double> custoTotalMensal`
  - `State<Integer> qntAtivas`
  - `carregarSubscricoes()` — SELECT join com `documentos_subscricao` para custo + plano + renovação.
  - `calcularCustoTotal()` — SUM custo_mensal WHERE ativa = 1.
  - `criar / atualizar / apagar`.
  - `alternarAtiva(int id)`.

---

## Calendário (NÃO EXISTE)

Sidebar do alvo tem entrada "Calendário" mas:
- [ ] `views/CalendarioView.java` não existe.
- [ ] `viewModels/CalendarioViewModel.java` não existe.
- [ ] Definir scope: vista mensal agregando expirações de docs + veículos + subscrições?
- [ ] Adicionar rota na sidebar (`App.java`).

---

## Geral / Cross-Cutting

- [ ] Branding: trocar "ISPGAYA" da sidebar atual por **"LifeBinder+ / Gestor Inteligente"** com logo escudo azul.
- [ ] Componente reutilizável `EstatisticaCard` (ícone + label + valor + subtexto) — atualmente repetido 4× inline no Dashboard.
- [ ] Componente reutilizável `Modal` para os formulários CRUD.
- [ ] Sistema de notificações (botão "Ativar Notificações" implica permissão OS + scheduling).
- [ ] Toast/feedback após criar/editar/apagar.
- [ ] Validação de inputs nos modais.
- [ ] Confirmação antes de apagar (dialog).
