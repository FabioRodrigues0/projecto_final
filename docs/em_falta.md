# Em Falta — Visual + Backend

## Dashboard (`views/DashboardView.java` + `viewModels/DashboardViewModel.java`)

### Visual
- [x] Botão **"Ativar Notificações"** no canto superior direito do título.
- [ ] **Ícones** dentro dos 4 cards de contadores (documento / carro / cartão / alerta).
- [ ] Card **Subscrições**: subtexto com custo mensal total (ex.: `€83.44/mês`).
- [ ] Card **Alertas**: subtexto com breakdown (ex.: `4 expirados · 1 urgentes`).
- [ ] Ícone de aviso (⚠) ao lado de "Próximas Expirações (30 dias)".
- [ ] Cards de atalho do fundo (Gerir Documentos / Veículos / Subscrições) precisam de ícone + estilo hover/clicável.
- [ ] Sidebar: falta entrada **Calendário** (5º item).
- [ ] Acertar paleta para a do alvo 
  - [x] sidebar branca, 
  - [ ] cards com sombra subtil, 
  - [x] fundo `#f7f8fa

### Backend / Queries
- [x] `carregarDocumentos()` faz `COUNT` em `pessoas` em vez de documentos — corrigir para somar `documentos_pessoal + documentos_veiculo + documentos_subscricao` ou só `documentos_pessoal` (decidir semântica).
- [ ] `qntAlertas` nunca é carregado — falta método `carregarAlertas()` (contar expirações com `diasReais <= X`).
- [x] Falta cálculo do **custo total mensal** das subscrições ativas para o subtexto do card.
- [ ] Falta breakdown de alertas (expirados vs urgentes) para subtexto do card.
- [ ] Falta flag/coluna "ativa" nas subscrições para filtrar inativas nos contadores.

---

## Documentos (`views/DocumentosView.java` + `viewModels/DocumentosViewModel.java`)

Estado: base implementada; faltam filtros e operações de edição/remoção.

### Model
- [x] `models/Pessoal/DocumentosPessoal.java`

### Visual
- [x] Botão **"+ Novo Documento"** no topo direito.
- [ ] **Tabs de filtro**: Todos / Garantia / Contrato / Fatura / Outro.
- [ ] **Lista** de cards de documento com:
  - [ ] barra colorida lateral por categoria (vermelho expirado, amarelo breve, verde válido)
  - [ ] ícone por categoria (fatura, garantia, contrato)
  - [x] título + linha "Categoria · Validade: YYYY-MM-DD"
  - [x] badge de estado à direita (`Expirado` / `Expira Em Breve` / `Válido`)
  - [x] texto de dias (em atraso / restantes)
  - [x] botões editar (lápis) + apagar (lixo)
- [x] **Modal "Novo Documento"** com campos: Título, Categoria (dropdown), Data Emissão, Data Validade, Notas.
- [x] Estado vazio.

### Backend / Queries
- [ ] Completar `DocumentosViewModel`:
  - [x] `StateList<DocumentosPessoal> listDocumentos`
  - [ ] `State<String> filtroCategoria`
  - [x] `carregarDocumentos()` — SELECT em `documentos_pessoal`.
  - [x] `criar(DocumentosPessoal)` — INSERT.
  - [ ] `atualizar(DocumentosPessoal)` — UPDATE por id.
  - [ ] `apagar(int id)` — DELETE.
  - [ ] `filtrar(TipoDocumentoPessoal)` — re-query ou filtro em memória.

---

## Veículos (`views/VeiculosView.java` + `viewModels/VeiculosViewModel.java`)

Estado: base implementada; faltam upload de foto e operações de edição/remoção.

### Model
- [x] `models/Veiculo/Veiculos.java` + `DocumentosVeiculo.java`

### Visual
- [x] Botão **"+ Novo Veículo"** no topo direito.
- [x] **Grid de cards** (3 colunas) por veículo:
  - [x] área de foto no topo (placeholder com ícone de carro se sem foto)
  - [ ] botão câmara para upload de foto
  - [x] nome (marca + modelo)
  - [x] linha "Ano · Matrícula"
  - [x] botão **"Ver Documentos →"** (abre detalhe / lista docs do veículo)
  - [x] botões editar + apagar
- [x] **Modal "Novo Veículo"** com: Marca, Modelo, Ano, Matrícula, Notas.
- [x] Vista de detalhe / sub-página com documentos do veículo (seguro, IUC, inspeção).
- [x] Estado vazio.

### Backend / Queries
- [ ] Completar `VeiculosViewModel`:
  - [x] `StateList<Veiculos> listVeiculos`
  - [x] `carregarVeiculos()` — SELECT em `veiculos`.
  - [x] criar veiculo.
  - [ ] atualizar veiculo
  - [ ] apagar veiculo
  - [x] `carregarDocumentosVeiculo(int veiculoId)` para sub-vista via `VeiculosDocumentosViewModel`.
  - [ ] upload de foto (guardar path/blob, decidir estratégia).
- [ ] Veiculos só tem campo `nome` — separar em `marca` + `modelo` (model + schema) ou parse string.

---

## Documentos do Veículo (`views/VeiculosDocumentosView.java` + `viewModels/VeiculosDocumentosViewModel.java`)

Estado: base implementada; faltam operações de edição/remoção.

### Model
- [x] `models/Veiculo/DocumentosVeiculo.java`
- [x] `models/TipoDocumentoVeiculo.java`

### Visual
- [x] Vista de detalhe aberta a partir do botão **"Ver Documentos"** no card do veículo.
- [x] Título com nome do veículo, ano e matrícula.
- [x] Botão **"Adicionar"** no topo direito.
- [x] Modal com campos: Tipo, Título, Data de Validade, Valor, Notas.
- [x] Campos condicionais para seguro: Seguradora e Cobertura.
- [x] Lista de documentos usando `DocumentoCard`.
- [x] Estado vazio "Sem Documentos".
- [x] Limpar campos ao adicionar/cancelar/fechar modal.
- [ ] Filtros por tipo: Todos / Seguro / IUC / Inspeção / Outro.

### Backend / Queries
- [x] `StateList<DocumentosVeiculo> listDocumentos`
- [x] States do formulário de documento do veículo.
- [x] `carregarDocumentos(int veiculoId)` filtra documentos pelo veículo.
- [x] `verDocumentos()` — SELECT em `documentos_veiculo`.
- [x] `novoDocumento(int veiculoId)` — INSERT em `documentos_veiculo`.
- [ ] `updateDocumento(int id)` — UPDATE por id.
- [ ] `apagarDocumento(int id)` — DELETE por id.

---

## Subscrições (`views/SubscricaoView.java` + `viewModels/SubscricaoViewModel.java`)

Estado: base implementada; faltam operações de edição/remoção e alguns acabamentos visuais.

### Model
- [x] `models/Subscricao/Subscricoes.java` + `DocumentosSubscricao.java`
- [x] Falta campo `custoMensal` (double/BigDecimal) no model `Subscricoes` ou em `DocumentosSubscricao`.
- [x] Falta campo `categoria` (Streaming / Software / Serviço Online) — `TipoDocumentoSubscricao` existe, validar.
- [x] Falta campo `ativa` (boolean).
- [x] Falta campo `plano` (string, ex.: "Premium 4K").

### Visual
- [x] Botão **"+ Nova Subscrição"** topo direito.
- [x] **Banner azul** no topo com:
  - [x] "Custo Total Mensal" + `€XX.XX` grande
  - [x] direita: "N subscrições ativas" + `€XX.XX/ano`
- [ ] **Lista** de cards de subscrição com:
  - [x] ícone por plataforma/serviço quando reconhecido
  - [x] nome + linha "Categoria · Plano"
  - [x] custo `€X.XX/mês` à direita
  - [x] dias restantes / em atraso por baixo (cor por estado)
  - [ ] botões editar + apagar
- [x] **Modal "Nova Subscrição"**: Serviço, Categoria, Custo Mensal, Plano, Data Renovação, toggle Ativa.
- [ ] Notas no modal de subscrição.
- [x] Estado vazio.

### Backend / Queries
- [ ] Completar `SubscricaoViewModel`:
  - [x] `StateList<Subscricoes> listSubscricoes`
  - [x] `StateList<DocumentosSubscricao> listDocumentosSubscricao`
  - [x] `State<Double> custoTotalMensal`
  - [x] `State<Integer> qntAtivas`
  - [x] `carregarSubscricoes()` — SELECT em `subscricoes`.
  - [x] `carregarDocumentos()` — SELECT em `documentos_subscricao`.
  - [x] `calcularCustoTotal()` — SUM custo_mensal WHERE ativa = 1.
  - [x] criar subscricao.
  - [ ] atualizar subscricao
  - [ ] apagar subscricao
  - [ ] `alternarAtiva(int id)`.

---

## Calendário

Sidebar do alvo tem entrada "Calendário" mas:
- [x] `views/CalendarioView.java` não existe.
- [x] `viewModels/CalendarioViewModel.java` não existe.
- [x] Definir scope: vista mensal agregando expirações de docs + veículos + subscrições?
- [x] Adicionar rota na sidebar (`App.java`).

---

## Geral / Cross-Cutting

- [x] Branding: trocar "ISPGAYA" da sidebar atual por **"LifeBinder+ / Gestor Inteligente"** com logo escudo azul.
- [ ] Componente reutilizável `EstatisticaCard` (ícone + label + valor + subtexto) — atualmente repetido 4× inline no Dashboard.
- [x] Componente reutilizável `Modal` para os formulários CRUD.
- [ ] Sistema de notificações (botão "Ativar Notificações" implica permissão OS + scheduling).
- [ ] Toast/feedback após criar/editar/apagar.
- [ ] Validação de inputs nos modais.
- [ ] Confirmação antes de apagar (dialog).
