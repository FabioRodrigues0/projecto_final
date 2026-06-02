package views;

import components.DocumentoCard;
import components.Titulo;
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
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import javafx.geometry.Pos;
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
                new Titulo(
                    this.app, "Documentos", "Garantias, contratos, faturas e outros", "fas-plus", "Nova Pessoa", () -> new Column()
                        .gap(8)
                        .children(new TextField().label("Nome").bindTo(vm.nomePessoa)), () -> {
                            vm.novo();
                            vm.carregarPessoas();
                        }, () -> {
                            vm.nomePessoa.set("");
                        }
                ).render(),
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
                                        new IconButton("fas-plus", "Novo Documento").onClick(() -> {
                                            Modal
                                                .showUndecorated(
                                                    app,
                                                    "Documentos",
                                                    500.0,
                                                    400.0,
                                                    modal -> {
                                                        modal.setOnHidden(event -> {
                                                            vm.tituloDocumento.set("");
                                                            vm.categoriaDocumento
                                                                .set(TipoDocumentoPessoal.NONE);
                                                            vm.dataEmissaoDocumento.set(null);
                                                            vm.dataValidadeDocumento.set(null);
                                                            vm.notasDocumento.set("");
                                                        });

                                                        return new Column()
                                                            .gap(8)
                                                            .children(
                                                                new Text("Novo Documento")
                                                                    .fontSize(18),
                                                                new TextField()
                                                                    .label("Titulo")
                                                                    .bindTo(vm.tituloDocumento),
                                                                new Dropdown<>(
                                                                    List
                                                                        .of(
                                                                            TipoDocumentoPessoal
                                                                                .values()
                                                                        )
                                                                )
                                                                    .label("Categoria:")
                                                                    .bindTo(vm.categoriaDocumento),
                                                                new Row()
                                                                    .gap(5)
                                                                    .children(
                                                                        new DatePicker()
                                                                            .label(
                                                                                "Data de Emissao:"
                                                                            )
                                                                            .bindTo(
                                                                                vm.dataEmissaoDocumento
                                                                            ),
                                                                        new DatePicker()
                                                                            .label(
                                                                                "Data de Validacao"
                                                                            )
                                                                            .bindTo(
                                                                                vm.dataValidadeDocumento
                                                                            )
                                                                    ),
                                                                new TextField()
                                                                    .multiline()
                                                                    .label("Notas")
                                                                    .bindTo(vm.notasDocumento),
                                                                new Row()
                                                                    .gap(8)
                                                                    .modifier(
                                                                        new Modifier()
                                                                            .alignment(
                                                                                Pos.BOTTOM_RIGHT
                                                                            )
                                                                    )
                                                                    .children(
                                                                        new Button("Cancelar")
                                                                            .onClick(modal::close),
                                                                        new Button("Adicionar")
                                                                            .onClick(() -> {
                                                                                vm
                                                                                    .novoDocumento(
                                                                                        pessoa
                                                                                            .getId()
                                                                                    );
                                                                                vm
                                                                                    .carregarDocumentos();
                                                                                modal.close();
                                                                            })
                                                                    )
                                                            );
                                                    }
                                                );
                                        })
                                    ),
                                new Divider(),
                                new ItemsColumn<DocumentosPessoal>()
                                    .gap(10)
                                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                                    .emptyState(
                                        new Card().elevation(2).children(new Text("Sem Documentos"))
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
                                    .item(documento -> new DocumentoCard(documento).render())
                            )
                    )
            );
    }
}
