package views;

import components.DocumentoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import models.Pessoal.DocumentosPessoal;
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
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(
                    this.app, "Documentos", "Garantias, contratos, faturas e outros", "fas-plus", "Novo Documento", () -> new Column()
                        .gap(8)
                        .children(
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
                            new TextField().multiline().label("Notas").bindTo(vm.notasDocumento)
                        ), () -> {
                            vm.carregarDocumentos();
                        }, () -> {
                            vm.tituloDocumento.set("");
                            vm.categoriaDocumento.set(TipoDocumentoPessoal.NONE);
                            vm.dataEmissaoDocumento.set(null);
                            vm.dataValidadeDocumento.set(null);
                            vm.notasDocumento.set("");
                        }

                ).render(),
                new ItemsColumn<DocumentosPessoal>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem Documentos")))
                    .items(this.vm.listDocumentos)
                    .item(documento -> new DocumentoCard(documento).render())
            );
    }
}
