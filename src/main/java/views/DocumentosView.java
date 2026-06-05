package views;

import components.DocumentoCard;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Divider;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Modal;
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
import javafx.geometry.Pos;
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
                new Row()
                    .gap(0)
                    .children(
                        new Column()
                            .gap(8)
                            .children(
                                new Text("Documentos").fontSize(24).modifier(new Modifier().bold()),
                                new Text("Garantias, contratos, faturas e outros")
                                    .fontSize(13)
                                    .modifier(new Modifier().textColor(Color.GRAY))
                            ),
                        new Spacer(),
                        new IconButton("fas-plus", "Nova Pessoa")
                            .color(BricksTheme.current().colorScheme().onPrimary())
                            .modifier(new Modifier().height(35).padding(0))
                            .onClick(() -> abrirPessoaModal(null))
                    ),
                new ItemsColumn<Pessoas>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem Pessoas")))
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

        Modal.showUndecorated(app, "Documentos", 500.0, 250.0, modal -> {
            modal.setOnHidden(event -> limparPessoa());

            return new Column()
                .gap(8)
                .children(
                    new Text(update ? "Editar Pessoa" : "Nova Pessoa").fontSize(18),
                    new TextField().label("Nome").bindTo(vm.nomePessoa),
                    new Row()
                        .gap(8)
                        .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                        .children(
                            new Button("Cancelar").onClick(modal::close),
                            new Button(update ? "Atualizar" : "Adicionar").onClick(() -> {
                                if (update) {
                                    vm.update(pessoa.getId());
                                } else {
                                    vm.novo();
                                }

                                vm.carregarPessoas();
                                modal.close();
                            })
                        )
                );
        });
    }

    private void abrirDocumentoModal(Pessoas pessoa, DocumentosPessoal documento) {
        boolean update = documento != null;

        if (update) {
            preencherDocumento(documento);
        } else {
            limparDocumento();
        }

        Modal.showUndecorated(app, "Documentos", 500.0, 400.0, modal -> {
            modal.setOnHidden(event -> limparDocumento());

            return new Column()
                .gap(8)
                .children(
                    new Text(update ? "Editar Documento" : "Novo Documento").fontSize(18),
                    new TextField().label("Titulo").bindTo(vm.tituloDocumento),
                    new Dropdown<>(List.of(TipoDocumentoPessoal.values()))
                        .label("Categoria:")
                        .bindTo(vm.categoriaDocumento),
                    new Row()
                        .gap(5)
                        .children(
                            new DatePicker()
                                .label("Data de Emissao:")
                                .bindTo(vm.dataEmissaoDocumento),
                            new DatePicker()
                                .label("Data de Validacao")
                                .bindTo(vm.dataValidadeDocumento)
                        ),
                    new TextField().multiline().label("Notas").bindTo(vm.notasDocumento),
                    new Row()
                        .gap(8)
                        .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                        .children(
                            new Button("Cancelar").onClick(modal::close),
                            new Button(update ? "Atualizar" : "Adicionar").onClick(() -> {
                                if (update) {
                                    vm.updateDocumento(documento.getId());
                                } else {
                                    vm.novoDocumento(pessoa.getId());
                                }

                                vm.carregarDocumentos();
                                modal.close();
                            })
                        )
                );
        });
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
