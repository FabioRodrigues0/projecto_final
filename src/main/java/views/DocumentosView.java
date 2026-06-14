package views;

import components.DocumentoCard;
import components.DocumentoImportacao;
import components.FormularioModal;
import components.NotificacoesApp;
import components.Titulo;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Divider;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import javafx.scene.paint.Color;
import models.Pessoal.DocumentosPessoal;
import models.Pessoal.Pessoas;
import models.TipoDocumentoPessoal;
import viewModels.DocumentosViewModel;

public class DocumentosView extends BricksScene {

    private final DocumentosViewModel vm = new DocumentosViewModel();
    private final BricksApplication app;

    public DocumentosView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarDocumentos();
        this.vm.carregarPessoas();
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(this.app, "Documentos")
                    .subtitulo("Garantias, contratos, faturas e outros")
                    .botao("fas-plus", "Nova Pessoa")
                    .onClick(() -> abrirPessoaModal(null))
                    .render(),
                new ItemsColumn<Pessoas>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .children(new Text("Sem Pessoas"))
                    )
                    .items(this.vm.listPessoas)
                    .item(
                        pessoa -> new Column()
                            .gap(8)
                            .children(
                                new Row()
                                    .gap(5)
                                    .children(
                                        new Text(pessoa.getNome())
                                            .fontSize(24)
                                            .modifier(new Modifier().bold()),
                                        new Spacer(),
                                        new IconButton("fas-pen")
                                            .modifier(new Modifier().height(35).padding(0))
                                            .ghost()
                                            .color(BricksTheme.current().colorScheme().onSurface())
                                            .onClick(() -> abrirPessoaModal(pessoa)),
                                        new IconButton("fas-trash-alt")
                                            .modifier(new Modifier().height(35).padding(0))
                                            .ghost()
                                            .color(Color.RED)
                                            .onClick(() -> {
                                                if (!Alert
                                                    .confirm(
                                                        "Confirmar",
                                                        "Tem a certeza que pretende apagar esta pessoa?"
                                                    )) {
                                                    return;
                                                }

                                                vm.apagar(pessoa.getId());
                                                vm.carregarPessoas();
                                                vm.carregarDocumentos();
                                                NotificacoesApp.removido(app, vm);
                                            }),
                                        new IconButton("fas-plus")
                                            .ghost()
                                            .color(BricksTheme.current().colorScheme().primary())
                                            .modifier(new Modifier().height(35).padding(0))
                                            .onClick(() -> abrirDocumentoModal(pessoa, null))
                                    ),
                                new Divider(),
                                new ItemsColumn<DocumentosPessoal>()
                                    .gap(10)
                                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                                    .emptyState(
                                        new Card()
                                            .elevation(2)
                                            .padding(15)
                                            .background(
                                                BricksTheme.current().colorScheme().surface()
                                            )
                                            .children(new Text("Sem Documentos"))
                                    )
                                    .items(
                                        this.vm.listDocumentos
                                            .get()
                                            .stream()
                                            .filter(
                                                documento -> documento.getPessoaId() == pessoa
                                                    .getId()
                                            )
                                            .toList()
                                    )
                                    .item(
                                        documento -> new DocumentoCard(
                                            documento, () -> abrirDocumentoModal(
                                                pessoa,
                                                documento
                                            ), () -> {
                                                if (!Alert
                                                    .confirm(
                                                        "Confirmar",
                                                        "Tem a certeza que pretende apagar este documento?"
                                                    )) {
                                                    return;
                                                }

                                                vm.apagarDocumento(documento.getId());
                                                vm.carregarDocumentos();
                                                NotificacoesApp.documentoRemovido(app, vm);
                                            }
                                        ).render()
                                    )
                            )
                    )
            );
    }

    private void abrirPessoaModal(Pessoas pessoa) {
        boolean update = pessoa != null;

        if (update) {
            preencherPessoa(pessoa);
        } else {
            limparPessoa();
        }

        new FormularioModal(app, "Documentos")
            .size(500.0, 250.0)
            .update(update)
            .titles("Nova Pessoa", "Editar Pessoa")
            .content(new TextField().label("Nome").bindTo(vm.nomePessoa))
            .onClear(this::limparPessoa)
            .onSubmit(() -> {
                if (update) {
                    vm.update(pessoa.getId());
                    NotificacoesApp.atualizado(app, vm);
                } else {
                    vm.novo();
                    NotificacoesApp.criado(app, vm);
                }
                vm.carregarPessoas();
            })
            .show();
    }

    private void abrirDocumentoModal(Pessoas pessoa, DocumentosPessoal documento) {
        boolean update = documento != null;

        if (update) {
            preencherDocumento(documento);
        } else {
            limparDocumento();
        }

        new FormularioModal(app, "Documentos")
            .size(500.0, 400.0)
            .update(update)
            .titles("Novo Documento", "Editar Documento")
            .content(documentoForm())
            .onFileImport((file, content) -> preencherDocumentoImportado(file, content))
            .onClear(this::limparDocumento)
            .onSubmit(() -> {
                if (update) {
                    vm.updateDocumento(documento.getId());
                    NotificacoesApp.documentoAtualizado(app, vm);
                } else {
                    vm.novoDocumento(pessoa.getId());
                    NotificacoesApp.documentoCriado(app, vm);
                }
                vm.carregarDocumentos();
            })
            .show();
    }

    private Component documentoForm() {
        return new Column()
            .gap(8)
            .children(
                new TextField().label("Titulo").bindTo(vm.tituloDocumento),
                new Dropdown<>(List.of(TipoDocumentoPessoal.values()))
                    .label("Categoria:")
                    .bindTo(vm.categoriaDocumento),
                new Row()
                    .gap(5)
                    .children(
                        new DatePicker().label("Data de Emissao:").bindTo(vm.dataEmissaoDocumento),
                        new DatePicker().label("Data de Validacao").bindTo(vm.dataValidadeDocumento)
                    ),
                new TextField().multiline().label("Notas").bindTo(vm.notasDocumento)
            );
    }

    private void preencherDocumentoImportado(java.io.File file, String content) {
        vm.tituloDocumento.set(DocumentoImportacao.titulo(file, content));
        vm.categoriaDocumento
            .set(
                DocumentoImportacao
                    .categoria(TipoDocumentoPessoal.class, content, TipoDocumentoPessoal.OUTRO)
            );
        DocumentoImportacao.primeiraData(content).ifPresent(vm.dataEmissaoDocumento::set);
        DocumentoImportacao.segundaData(content).ifPresent(vm.dataValidadeDocumento::set);
        vm.notasDocumento.set(DocumentoImportacao.texto(content));
    }

    private void preencherDocumento(DocumentosPessoal documento) {
        vm.tituloDocumento.set(documento.getTitulo());
        vm.categoriaDocumento.set(documento.getTipo());
        vm.dataEmissaoDocumento.set(documento.getDataEmissao());
        vm.dataValidadeDocumento.set(documento.getDataValidade());
        vm.notasDocumento.set(documento.getNotas() == null ? "" : documento.getNotas());
    }

    private void limparDocumento() {
        vm.tituloDocumento.set("");
        vm.categoriaDocumento.set(TipoDocumentoPessoal.NONE);
        vm.dataEmissaoDocumento.set(null);
        vm.dataValidadeDocumento.set(null);
        vm.notasDocumento.set("");
    }

    private void preencherPessoa(Pessoas pessoa) {
        vm.nomePessoa.set(pessoa.getNome());
    }

    private void limparPessoa() {
        vm.nomePessoa.set("");
    }
}
