# Bricks AI — Documentacao da Biblioteca Bricks

Este documento explica como funciona o **Bricks**, a biblioteca usada como base de UI e dados no projeto final. O objetivo e servir como fonte de consulta para perceber a arquitetura, o ciclo de vida, o estado reativo, os componentes visuais e a camada de base de dados.

> Nota: no repositorio analisado, o `pom.xml` da biblioteca indica a versao `0.6.3.8`. Alguns exemplos antigos do README ainda apontam para `0.4.3`; para projetos novos, usar a versao publicada mais recente que estiver disponivel no JitPack.

## 1. O que e o Bricks

O Bricks e uma biblioteca Java para construir aplicacoes desktop com uma API declarativa inspirada no Jetpack Compose, usando **JavaFX** como motor grafico.

Em vez de criar janelas com FXML ou montar componentes imperativamente, a app descreve a interface como uma arvore de objetos `Component`. Cada componente sabe transformar-se num `javafx.scene.Node` atraves do metodo `render()`.

Exemplo minimo:

```java
public class MinhaApp extends BricksApplication {

    private final State<Integer> contador = state(0);

    {
        setTitle("Contador");
        setSize(800, 600);
    }

    @Override
    public Component root() {
        return new Column()
            .padding(20)
            .gap(12)
            .children(
                new Text("Valor: " + contador.get()).fontSize(24),
                new Button("Incrementar")
                    .onClick(() -> contador.update(v -> v + 1))
            );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Quando `contador` muda, o Bricks volta a chamar `root()` e reconstrui a interface.

## 2. Estrutura da biblioteca

Pacotes principais:

- `fabiorodrigues.bricks.core`: ciclo de vida da app, estado reativo, scenes, viewmodels e efeitos.
- `fabiorodrigues.bricks.components`: componentes visuais prontos a usar.
- `fabiorodrigues.bricks.style`: temas e `Modifier` para layout/estilo.
- `fabiorodrigues.bricks.data`: acesso a base de dados com query builder.
- `fabiorodrigues.bricks.data.config`: configuracoes SQLite, MySQL e PostgreSQL.

Modulo Java:

```java
module fabiorodrigues.bricks {
    requires javafx.controls;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    exports fabiorodrigues.bricks.core;
    exports fabiorodrigues.bricks.components;
    exports fabiorodrigues.bricks.style;
    exports fabiorodrigues.bricks.data;
    exports fabiorodrigues.bricks.data.config;
}
```

## 3. Dependencias e instalacao

Requisitos:

- Java 17 ou superior.
- JavaFX 21 na biblioteca analisada.
- Maven ou Gradle.
- JitPack para consumir a dependencia publicada.

Exemplo Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.fabiorodrigues0</groupId>
        <artifactId>bricks</artifactId>
        <version>0.6.3.8</version>
    </dependency>
</dependencies>
```

Em apps modulares:

```java
module projeto.final {
    requires fabiorodrigues.bricks;
}
```

## 4. Ciclo de vida da aplicacao

A classe principal deve extender `BricksApplication`.

Responsabilidades de `BricksApplication`:

- cria o `Stage` e a `Scene` JavaFX;
- aplica o tema visual;
- chama `root()` para obter a arvore de componentes;
- regista listeners nos `State`;
- faz `rerender()` quando o estado muda;
- suporta navegacao por `BricksScene`;
- expoe `getStage()` para componentes como `Modal`.

Fluxo simplificado:

1. A app chama `launch(args)`.
2. JavaFX chama `start(Stage stage)`.
3. O Bricks cria um `StackPane` container.
4. `root()` e chamado.
5. O componente raiz e convertido para JavaFX com `render()`.
6. Quando um `State` muda, o Bricks agenda novo render com `Platform.runLater`.

Codigo importante:

```java
@Override
public Component root() {
    return new Column().children(...);
}
```

`root()` deve ser tratado como uma funcao declarativa: le estado atual e devolve a UI correspondente.

## 5. Estado reativo

### `State<T>`

`State<T>` guarda um valor e notifica listeners quando o valor muda.

```java
private final State<String> filtro = state("");

new TextField()
    .placeholder("Pesquisar...")
    .bindTo(filtro);
```

Metodos principais:

- `get()`: le o valor atual.
- `set(valor)`: substitui o valor e dispara re-render.
- `update(fn)`: calcula novo valor a partir do anterior.
- `setQuietly(valor)`: atualiza sem re-render; usado internamente por inputs.

Usar `state(...)` dentro de `BricksApplication` ou `BricksViewModel` para que fique ligado ao render automatico.

### `StateList<T>`

`StateList<T>` e uma lista reativa. Mutacoes disparam re-render sem ser necessario trocar a lista inteira.

```java
private final StateList<Documento> documentos = stateList(List.of());

documentos.add(novoDocumento);
documentos.remove(documento);
documentos.clear();
documentos.addAll(listaDaBaseDados);
```

Leitura:

```java
documentos.get();      // snapshot imutavel
documentos.get(0);     // item por indice
documentos.size();
documentos.isEmpty();
```

### `DerivedState<T>`

`DerivedState<T>` calcula um valor a partir de outros estados e guarda cache ate alguma dependencia mudar.

```java
private final State<String> filtro = state("");
private final State<List<Documento>> documentos = state(List.of());

private final DerivedState<List<Documento>> visiveis = derived(
    () -> documentos.get().stream()
        .filter(d -> d.titulo().contains(filtro.get()))
        .toList(),
    filtro,
    documentos
);
```

Usar para filtros, totais, contadores ou qualquer valor calculado a partir de estado existente.

### `Effect`

`Effect` executa uma acao quando alguma dependencia muda. Ao ser criado, executa imediatamente uma vez.

```java
private final Effect initDb = effect(() -> {
    DB.query()
        .createTableIfNotExists("documentos")
        .column("id", "INTEGER PRIMARY KEY AUTOINCREMENT")
        .column("titulo", "TEXT NOT NULL")
        .execute();
});
```

Uso tipico:

- inicializar schema;
- carregar dados no arranque;
- guardar automaticamente;
- executar logica secundaria quando um filtro muda.

Importante: `Effect` nao devolve valor e nao e um componente visual.

## 6. Scenes e ViewModels

### `BricksScene`

`BricksScene` permite separar ecras em ficheiros independentes.

```java
public class DashboardScene extends BricksScene {

    public DashboardScene(BricksApplication app) {
        super(app);
    }

    @Override
    public Component render() {
        return new Column().children(
            new Text("Dashboard").fontSize(24)
        );
    }
}
```

Na app:

```java
{
    setInitialScene(new DashboardScene(this));
}

@Override
public Component root() {
    return currentScene().render();
}
```

Navegacao:

```java
app.navigateTo(new DocumentosScene(app));
```

Cuidados:

- `State` criado diretamente em `BricksScene` e local e nao fica automaticamente ligado ao re-render, exceto `StateList`.
- Para estado persistente entre navegacoes, guardar no `BricksApplication` ou num `BricksViewModel`.
- Quando uma scene e recriada, o estado local dessa scene e perdido.

### `BricksViewModel`

`BricksViewModel` separa estado e logica de negocio da UI.

```java
public class DocumentosViewModel extends BricksViewModel {
    public final StateList<Documento> documentos = stateList(List.of());
    public final State<String> filtro = state("");

    public void carregar() {
        List<Documento> lista = DB.query()
            .select("id", "titulo", "validade")
            .from("documentos")
            .execute(Documento.class);

        documentos.clear();
        documentos.addAll(lista);
    }
}
```

Na scene:

```java
public class DocumentosScene extends BricksScene {
    private final DocumentosViewModel vm;

    public DocumentosScene(BricksApplication app, DocumentosViewModel vm) {
        super(app);
        this.vm = vm;
        use(vm);
        vm.carregar();
    }

    @Override
    public Component render() {
        return new LazyColumn<Documento>()
            .items(vm.documentos)
            .item(d -> new Text(d.titulo()));
    }
}
```

`use(vm)` liga os states do ViewModel ao ciclo de render da app.

## 7. Componentes visuais

Todos os componentes implementam `Component`:

```java
public interface Component {
    Node render();
}
```

### Layout

Componentes principais:

- `Column`: layout vertical.
- `Row`: layout horizontal.
- `Box`: container simples.
- `Card`: container com fundo, padding, elevacao, imagem de capa e clique.
- `Spacer`: espaco vazio fixo.
- `Divider`: linha horizontal ou vertical.
- `ScrollView`: area com scroll.
- `LazyColumn<T>`: lista virtualizada para muitos itens.
- `AppLayout`: estrutura principal com sidebar, navbar e conteudo.

Exemplo:

```java
new Column()
    .padding(24)
    .gap(12)
    .children(
        new Text("Documentos").fontSize(24).bold(),
        new Row().gap(8).children(
            new Button("Novo"),
            new Button("Atualizar")
        )
    );
```

### Texto, botoes e inputs

Componentes comuns:

- `Text`: texto simples.
- `Button`: botao com `onClick`, `enabled` e `onDisabledClick`.
- `IconButton`: botao com icone Ikonli.
- `TextField`: input de texto, uma linha ou multiline.
- `Checkbox`: booleano com `bindTo(State<Boolean>)`.
- `Dropdown<T>`: escolha entre opcoes.
- `Slider`: valor numerico.
- `ProgressBar`: progresso determinado ou indeterminado.
- `FilePicker`: abre seletor de ficheiro.

Exemplo de formulario:

```java
private final State<String> titulo = state("");
private final State<Boolean> valido = state(false);
private final State<Boolean> podeGuardar = state(false);

new Column()
    .gap(12)
    .children(
        new TextField()
            .label("Titulo")
            .placeholder("Nome do documento")
            .bindTo(titulo)
            .onChange(v -> podeGuardar.set(!v.isBlank())),

        new Checkbox("Valido")
            .bindTo(valido),

        new Button("Guardar")
            .enabled(podeGuardar)
            .onClick(this::guardar)
    );
```

Nota: no codigo atual, `Button.enabled(...)` aceita `boolean` ou `State<Boolean>`, nao uma lambda.

### Listas e tabelas

`LazyColumn<T>` e recomendada para listas grandes:

```java
new LazyColumn<Documento>()
    .items(documentos)
    .gap(8)
    .padding(12)
    .itemHeight(90)
    .emptyState(new Text("Sem documentos."))
    .item(d -> new Card()
        .padding(16)
        .children(
            new Text(d.titulo()).bold(),
            new Text(d.validade().toString())
        )
    );
```

Para preservar o scroll entre re-renders:

```java
private final ScrollState scroll = rememberScrollState();

new LazyColumn<Documento>()
    .scrollState(scroll)
    .items(documentos)
    .item(d -> ...);
```

`DataTable<T>` suporta:

- pesquisa;
- mostrar/esconder colunas;
- ordenacao;
- paginacao;
- selecao;
- acoes por linha;
- acoes na toolbar.

Exemplo:

```java
DataTable<Documento> tabela = new DataTable<Documento>()
    .items(documentos)
    .searchable()
    .columnToggle()
    .selectable()
    .pageSize(20)
    .column("Titulo", Documento::titulo).bold()
    .column("Categoria", Documento::categoria)
    .column("Validade", d -> d.validade().toString())
    .actionColumn(new TableAction<Documento>()
        .icon("fas-pencil")
        .tooltip("Editar")
        .onClick(this::editar))
    .actionColumn(new TableAction<Documento>()
        .icon("fas-trash")
        .tooltip("Apagar")
        .danger()
        .onClick(this::apagar));
```

### Navegacao visual

`AppLayout` combina:

- `Sidebar`;
- `Navbar`;
- conteudo central.

Deve ser criado como campo, nao dentro de `root()` ou `render()`, para evitar recriar a estrutura toda a cada re-render.

```java
private final AppLayout layout = new AppLayout()
    .sidebar(new Sidebar()
        .logo(new Text("LifeBinder+"))
        .item(new SidebarItem("fas-home", "Dashboard",
            () -> app.navigateTo(new DashboardScene(app)))))
    .navbar(new Navbar());

@Override
public Component render() {
    return layout.content(conteudoAtual);
}
```

## 8. Estilo, tema e Modifier

### `Modifier`

`Modifier` aplica estilo e layout de forma reutilizavel:

```java
Modifier cardStyle = new Modifier()
    .padding(16)
    .background(Color.WHITE)
    .borderRadius(8)
    .border(Color.web("#DDDDDD"), 1);

new Card().modifier(cardStyle).children(...);
```

Propriedades principais:

- layout: `padding`, `margin`, `width`, `height`, `size`, `gap`, `fillMaxWidth`, `fillMaxHeight`, `alignment`;
- texto: `fontSize`, `fontFamily`, `bold`, `italic`, `textColor`;
- visual: `background`, `backgroundGradient`, `border`, `borderRadius`, `opacity`, `visible`.

### `BricksTheme`

O Bricks aplica automaticamente um tema Material 3 light. Tambem existe `BricksTheme.dark()` e personalizacao fluente:

```java
{
    setTheme(
        BricksTheme.material()
            .colorScheme()
                .primary(Color.web("#2563EB"))
                .onPrimary(Color.WHITE)
                .and()
            .shapes()
                .medium(8)
                .and()
    );
}
```

Para usar cores do tema:

```java
new Text("Titulo").modifier(
    new Modifier().textColor(
        BricksTheme.current().colorScheme().primary()
    )
);
```

## 9. Modal, Alert e dialogos

### `Modal`

Abre uma janela modal ligada ao `Stage` principal e herda os estilos da app.

```java
Modal.show(app, "Novo documento", 500, 420, modal ->
    new Column()
        .gap(12)
        .children(
            new Text("Novo documento").fontSize(20).bold(),
            new TextField().label("Titulo").bindTo(titulo),
            new Button("Guardar").onClick(() -> {
                guardar();
                modal.close();
            })
        )
);
```

### `Alert`

Atalhos:

```java
Alert.info("Info", "Operacao concluida.");
Alert.warning("Aviso", "Preenche os campos obrigatorios.");
Alert.error("Erro", "Nao foi possivel guardar.");

boolean confirmou = Alert.confirm("Confirmar", "Apagar este item?");
```

## 10. Base de dados

O Bricks inclui uma camada simples por cima de JDBC.

Entrada principal:

```java
DB.query()
```

Configuracao por defeito:

- SQLite;
- ficheiro `./data/database.db`;
- a pasta `data` e criada automaticamente.

### Configuracao automatica

`DB.autoConfig()` tenta encontrar a classe `config.database.DatabaseConfig` no classpath. Se nao existir, usa SQLite.

```java
package config.database;

import fabiorodrigues.bricks.data.config.*;

public class DatabaseConfig {
    public DbConfig getConfig() {
        return new SQLiteConfig("./data/database.db");
    }
}
```

Para MySQL:

```java
return new MySQLConfig()
    .host("localhost")
    .port(3306)
    .database("app")
    .user("root")
    .password("pass");
```

Para PostgreSQL:

```java
return new PostgreSQLConfig()
    .host("localhost")
    .port(5432)
    .database("app")
    .user("postgres")
    .password("pass");
```

MySQL e PostgreSQL exigem que o driver JDBC respetivo esteja nas dependencias da app.

### Criar tabelas

```java
DB.query()
    .createTableIfNotExists("documentos")
    .column("id", DB.getConfig().autoIncrementSyntax())
    .column("titulo", "TEXT NOT NULL")
    .column("categoria", "TEXT")
    .column("data_validade", "TEXT")
    .execute();
```

### Inserir

```java
int id = DB.query()
    .insertInto("documentos")
    .value("titulo", "Seguro automovel")
    .value("categoria", "Contrato")
    .value("data_validade", "2026-12-31")
    .execute();
```

Tambem pode usar `values(Map.of(...))`.

### Selecionar e mapear para records

```java
public record Documento(int id, String titulo, String categoria, LocalDate dataValidade) {}

List<Documento> docs = DB.query()
    .select("id", "titulo", "categoria", "data_validade")
    .from("documentos")
    .orderBy("data_validade", "ASC")
    .execute(Documento.class);
```

O mapper tenta casar nomes `camelCase` com colunas `snake_case`, por exemplo `dataValidade` com `data_validade`.

Tipos suportados pelo mapper incluem objetos comuns, `LocalDate` e `LocalDateTime`, dependendo do valor vindo do JDBC.

### Filtros

```java
DB.query()
    .select("*")
    .from("documentos")
    .where("categoria", "=", "Contrato")
    .where("data_validade", "IS NOT NULL", null)
    .execute(Documento.class);
```

Tambem existe `WhereOperator`:

```java
.where("id", WhereOperator.GTE, 10)
```

Operadores especiais:

- `IN` e `NOT IN` recebem uma colecao;
- `IS NULL` e `IS NOT NULL` ignoram o valor.

### Filtros condicionais

```java
DB.query()
    .select("*")
    .from("documentos")
    .when(filtroCategoria != null, q ->
        q.where("categoria", "=", filtroCategoria)
    )
    .execute(Documento.class);
```

O metodo chama-se `when` porque `if` e palavra reservada em Java.

### Atualizar

```java
int afetados = DB.query()
    .update("documentos")
    .set(Map.of("titulo", "Novo titulo"))
    .where("id", "=", id)
    .execute();
```

### Apagar

```java
int afetados = DB.query()
    .deleteFrom("documentos")
    .where("id", "=", id)
    .execute();
```

### Resultado bruto

```java
QueryResult result = DB.query()
    .select("COUNT(*) as total")
    .from("documentos")
    .executeRaw();

Object total = result.first().get("total");
```

### Joins

```java
List<DocumentoComPessoa> rows = DB.query()
    .select("d.id", "d.titulo", "p.nome as pessoa_nome")
    .from("documentos d")
    .join("pessoas p", "p.id = d.pessoa_id")
    .execute(DocumentoComPessoa.class);
```

### Agrupamento 1:N

Para mapear linhas duplicadas de um join para um objeto pai com lista de filhos:

```java
public record Pessoa(int id, String nome, List<Documento> documentos) {}
public record Documento(int id, String titulo) {}

List<Pessoa> pessoas = DB.query()
    .select("p.id", "p.nome", "d.id", "d.titulo")
    .from("pessoas p")
    .leftJoin("documentos d", "d.pessoa_id = p.id")
    .groupParent(Pessoa.class, "id")
    .groupChild("documentos", Documento.class, "id")
    .execute(Pessoa.class);
```

## 11. Padrao recomendado para o projeto final

Para cada ecrã:

1. Criar um `ViewModel` com estados e metodos de carregamento/CRUD.
2. Criar uma `Scene` ou `View` que recebe o ViewModel.
3. Chamar `use(vm)` se estiver a usar `BricksScene`.
4. Carregar dados no construtor ou num `Effect`.
5. Renderizar sempre a partir dos states do ViewModel.

Exemplo para documentos:

```java
public class DocumentosViewModel extends BricksViewModel {
    public final StateList<Documento> documentos = stateList(List.of());
    public final State<String> categoria = state("Todos");

    public void carregar() {
        List<Documento> lista = DB.query()
            .select("id", "titulo", "categoria", "data_validade")
            .from("documentos_pessoal")
            .when(!categoria.get().equals("Todos"),
                q -> q.where("categoria", "=", categoria.get()))
            .orderBy("data_validade", "ASC")
            .execute(Documento.class);

        documentos.clear();
        documentos.addAll(lista);
    }

    public void apagar(int id) {
        DB.query()
            .deleteFrom("documentos_pessoal")
            .where("id", "=", id)
            .execute();
        carregar();
    }
}
```

View:

```java
new Column()
    .padding(24)
    .gap(16)
    .children(
        new Row().children(
            new Text("Documentos").fontSize(24).bold(),
            new Button("+ Novo Documento").onClick(this::abrirModal)
        ),
        new LazyColumn<Documento>()
            .items(vm.documentos)
            .emptyState(new Text("Sem documentos."))
            .item(d -> documentoCard(d))
    );
```

## 12. Cuidados e limitacoes importantes

- No projeto final, a dependencia atual em `build.gradle` e `com.github.FabioRodrigues0:bricks:v0.6.3.8`.
- `root()` e `render()` podem ser chamados muitas vezes. Evitar fazer queries pesadas diretamente dentro deles.
- Carregar dados em metodos do ViewModel, construtores controlados ou `Effect`.
- Para listas grandes, preferir `LazyColumn` em vez de montar centenas de `Card` dentro de `Column`.
- Guardar `AppLayout` como campo para evitar flicker e perda de estado da sidebar.
- Guardar `DataTable` como campo se for importante manter selecao/paginacao entre re-renders.
- `StateList.get()` devolve snapshot imutavel; mutar esse snapshot nao altera o estado.
- `Button.enabled(...)` no codigo atual aceita `boolean` ou `State<Boolean>`.
- MySQL/PostgreSQL precisam dos drivers JDBC adicionados no projeto consumidor.
- O query builder usa prepared statements para valores, mas nomes de tabelas/colunas entram como strings SQL. Nao construir nomes de tabelas/colunas a partir de input do utilizador.
- Em PostgreSQL, `onDuplicateUpdate(...)` deve ser acompanhado por `conflictOn("campo")`.
- `Effect` executa imediatamente ao ser criado; garantir que as dependencias e a base de dados ja estao configuradas.

## 13. Uso atual no projeto final

Esta secao descreve como o projeto final esta a usar o Bricks depois do commit `b81f41c feat: novos ajustes gerais`.

### Dependencia Bricks

O projeto final passou a usar a versao:

```gradle
implementation 'com.github.FabioRodrigues0:bricks:v0.6.3.8'
```

Isto e relevante porque os exemplos deste documento devem ser lidos contra a API dessa versao.

### Padrao View + ViewModel em uso

As views continuam a seguir o padrao:

```java
public class AlgumaView extends BricksScene {
    private final AlgumaViewModel vm = new AlgumaViewModel();

    public AlgumaView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.vm.carregarDados();
    }

    @Override
    public Component render() {
        return ...
    }
}
```

O ponto importante e `use(this.vm)`: sem isto, os `State` e `StateList` criados dentro do ViewModel nao ficam ligados ao re-render automatico da app.

### Documentos pessoais

`DocumentosViewModel` agora usa um `StateList<DocumentosPessoal>`:

```java
public final StateList<DocumentosPessoal> listDocumentos = stateList(List.of());

public void carregarDocumentos() {
    listDocumentos.clear();
    listDocumentos.addAll(verDocumentos());
}
```

`DocumentosView` chama `vm.carregarDocumentos()` no construtor e renderiza a lista com `LazyColumn`:

```java
new LazyColumn<DocumentosPessoal>()
    .gap(10)
    .columns(3)
    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
    .emptyState(new Card().elevation(2).children(new Text("Sem Documentos")))
    .items(this.vm.listDocumentos.get())
    .item(documento -> new DocumentoCard(documento).render());
```

Este e o padrao recomendado para listas ligadas a BD:

- carregar dados no ViewModel;
- guardar num `StateList`;
- renderizar a partir do snapshot atual (`listDocumentos.get()`);
- usar um componente de card dedicado (`DocumentoCard`) para nao deixar a View crescer demasiado.

### Veiculos e documentos de veiculo

`VeiculosView` usa a mesma ideia com `StateList<Veiculos>`:

```java
new LazyColumn<Veiculos>()
    .gap(10)
    .columns(3)
    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
    .emptyState(new Card().elevation(2).children(new Text("Sem veiculos")))
    .items(this.vm.listVeiculos.get())
    .item(veiculo -> ...);
```

Cada item e apresentado por `VeiculosCard`. O card usa funcionalidades do Bricks como:

- `Card.coverImage(imagePath, 160)` para imagem no topo;
- `IconButton` para editar/apagar;
- `Button.onClick(...)` para navegar;
- `BricksApplication.navigateTo(...)` para abrir `VeiculosDocumentosView`.

Exemplo de navegacao:

```java
new Button("Ver Documentos")
    .onClick(() -> app.navigateTo(
        new VeiculosDocumentosView(app, id, nome, ano, matricula, foto)
    ));
```

`VeiculosDocumentosView` recebe os dados do veiculo selecionado, chama `vm.carregarDocumentos(id)` e mostra os documentos com `LazyColumn<DocumentosVeiculo>`.

Nota tecnica: neste momento `VeiculosDocumentosViewModel.carregarDocumentos(int veiculoId)` carrega todos os documentos e filtra em memoria:

```java
verDocumentos().stream()
    .filter(doc -> doc.getVeiculoId() == veiculoId)
    .toList();
```

Funciona para poucos dados, mas quando houver muitos documentos deve passar para query com `where("veiculo_id", "=", veiculoId)`.

### Cards reutilizaveis

O projeto usa componentes proprios por cima do Bricks:

- `DocumentoCard`: recebe `DocumentosPessoal` ou `DocumentosVeiculo` e transforma o modelo num card visual.
- `VeiculosCard`: recebe `BricksApplication` e `Veiculos` para poder mostrar o card e navegar para os documentos do veiculo.
- `Titulo`: centraliza o titulo, subtitulo, icone e botao principal do ecra.

Isto e uma boa pratica com Bricks: usar os componentes nativos (`Card`, `Row`, `Column`, `Text`, `IconButton`, `Badge`) como blocos base, e criar componentes da app para padroes repetidos.

### Datas e SQLite

Foi criado o helper `DateValues`:

```java
final class DateValues {
    static Timestamp atStartOfDay(LocalDate date) {
        return Timestamp.valueOf(date.atStartOfDay());
    }
}
```

Ele e usado antes de inserir datas em documentos pessoais, documentos de veiculo e documentos de subscricao:

```java
.value("data_validade", DateValues.atStartOfDay(doc.getDataValidade()))
.value("data_renovacao", DateValues.atStartOfDay(doc.getDataRenovacao()))
```

Motivo: o projeto guarda datas em SQLite atraves do query builder do Bricks/JDBC. Ao converter `LocalDate` para `Timestamp` no inicio do dia, a data fica persistida de forma consistente com hora `00:00:00`.

Consequencia pratica: quando a app le datas para calcular expiracoes, pode receber valores com data e hora. Por isso o `DashboardViewModel` faz:

```java
LocalDate dataExpiracao = LocalDate.parse(row.dataExpiracao().substring(0, 10));
```

Isto extrai apenas `YYYY-MM-DD` antes de calcular os dias restantes.

### Expiracoes no Dashboard

`DashboardViewModel.carregarExpiracoes()` usa o query builder do Bricks com `unionAll(...)` para juntar tres origens:

- `documentos_veiculo`;
- `documentos_subscricao`;
- `documentos_pessoal`.

Depois converte cada linha para `Expiracoes`, filtra itens com `diasReais <= 30`, ordena por proximidade e atualiza `listExpiracoes`.

Este e um exemplo importante de uso da camada `data` do Bricks:

```java
DB.query()
    .select(...)
    .from("documentos_veiculo dv")
    .join("veiculos v", "v.id = dv.veiculo_id")
    .unionAll(
        DB.query()
            .select(...)
            .from("documentos_subscricao ds")
            .join("subscricoes s", "s.id = ds.subscricao_id")
    )
    .unionAll(
        DB.query()
            .select(...)
            .from("documentos_pessoal dp")
            .join("pessoas p", "p.id = dp.pessoa_id")
    )
    .execute(ExpiracaoRow.class);
```

### Recursos visuais

O commit adicionou `src/main/resources/car.png` e removeu `shield.svg`.

No `VeiculosCard`, a imagem usada e:

```java
boolean hasFoto = this.foto != null && !this.foto.isBlank();
String imagePath = hasFoto ? this.foto : "/car.png";
```

Depois e aplicada com:

```java
new Card()
    .coverImage(imagePath, 160)
```

Isto mostra como o Bricks permite combinar recursos JavaFX do classpath com componentes declarativos.

### Cuidados especificos observados no projeto

- Evitar queries dentro de `render()`: o projeto esta a carregar dados nos construtores das views via ViewModel, que e o caminho correto.
- Quando se usa `StateList`, preferir mutar a lista (`clear`, `addAll`) em vez de criar um novo objeto.
- Para documentos do veiculo, trocar filtro em memoria por `WHERE veiculo_id = ?` quando a base de dados crescer.
- Os botoes visuais de editar/apagar existem em cards, mas a logica ainda deve ser ligada a metodos do ViewModel.
- `DateValues.atStartOfDay(...)` deve ser usado sempre que um `LocalDate` for persistido em campos de data da app.

## 14. Mapa rapido de classes

Core:

- `BricksApplication`: classe base da app.
- `Component`: contrato de componente visual.
- `State<T>`: valor reativo.
- `StateList<T>`: lista reativa.
- `DerivedState<T>`: valor calculado com cache.
- `Effect`: efeito colateral ligado a states.
- `BricksScene`: separacao de ecras.
- `BricksViewModel`: estado/logica fora da UI.
- `ScrollState`: preserva scroll de `LazyColumn`.

Componentes:

- Layout: `Column`, `Row`, `Box`, `Card`, `Spacer`, `Divider`, `ScrollView`, `LazyColumn`, `AppLayout`.
- Texto/input: `Text`, `TextField`, `Button`, `IconButton`, `Checkbox`, `Dropdown`, `Slider`, `ProgressBar`.
- Navegacao: `Sidebar`, `SidebarItem`, `Navbar`, `NavbarItem`.
- Dados: `DataTable`, `DataTableColumn`, `TableAction`, `SelectionMode`, `Align`.
- Dialogos: `Modal`, `Alert`, `FilePicker`, `DropdownMenu`.
- Visual: `Icon`, `Image`, `Badge`.

Style:

- `Modifier`: layout e estilo fluente.
- `BricksTheme`: tema Material/custom.
- `ThemeRegistry`: tema ativo global.

Data:

- `DB`: configuracao e ponto de entrada.
- `Query`: query builder.
- `QueryResult`: resultado bruto.
- `WhereOperator`: operadores type-safe.
- `SQLiteConfig`, `MySQLConfig`, `PostgreSQLConfig`: configuracoes de base de dados.
