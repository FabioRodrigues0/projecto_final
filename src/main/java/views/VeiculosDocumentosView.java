package views;

import components.DocumentoCard;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Modal;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.components.When;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import javafx.geometry.Pos;
import models.TipoDocumentoVeiculo;
import models.Veiculo.DocumentosVeiculo;
import viewModels.VeiculosDocumentosViewModel;

public class VeiculosDocumentosView extends BricksScene {

    private final VeiculosDocumentosViewModel vm = new VeiculosDocumentosViewModel();
    private final BricksApplication app;
    private final int id;
    private final String nome;
    private final int ano;
    private final String matricula;
    private final String foto;

    public VeiculosDocumentosView(BricksApplication app, int id, String nome, int ano, String matricula, String foto) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarDocumentos(id);
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.matricula = matricula;
        this.foto = foto;
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight().fillMaxWidth())
            .children(
                new Row()
                    .gap(0)
                    .children(
                        new Column()
                            .gap(8)
                            .children(
                                new Text(this.nome).fontSize(24).modifier(new Modifier().bold()),
                                new Text(String.valueOf(this.ano) + "." + this.matricula)
                            ),
                        new Spacer(),
                        new IconButton("fas-plus", "Adicionar")
                            .color(BricksTheme.current().colorScheme().onPrimary())
                            .modifier(new Modifier().height(35).padding(0))
                            .onClick(() -> abrirDocumentoModal(null))
                    ),
                new ItemsColumn<DocumentosVeiculo>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem Documentos")))
                    .items(this.vm.listDocumentos.get())
                    .item(
                        documento -> new DocumentoCard(
                            documento, () -> abrirDocumentoModal(documento), () -> {
                                if (!Alert
                                    .confirm(
                                        "Confirmar",
                                        "Tem a certeza que pretende apagar este documento?"
                                    )) {
                                    return;
                                }

                                vm.apagarDocumento(documento.getId());
                                vm.carregarDocumentos(this.id);
                            }
                        ).render()
                    )
            );
    }

    private void abrirDocumentoModal(DocumentosVeiculo documento) {
        boolean update = documento != null;

        if (update) {
            preencherDocumento(documento);
        } else {
            limparDocumento();
        }

        Modal.showUndecorated(app, "Documentos", 550.0, 620.0, modal -> {
            modal.setOnHidden(event -> limparDocumento());

            return new Column()
                .gap(8)
                .children(
                    new Text(update ? "Editar Documento" : "Novo Documento").fontSize(18),
                    documentoForm(),
                    new Row()
                        .gap(8)
                        .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                        .children(
                            new Button("Cancelar").onClick(modal::close),
                            new Button(update ? "Atualizar" : "Adicionar").onClick(() -> {
                                if (update) {
                                    vm.updateDocumento(documento.getId());
                                } else {
                                    vm.novoDocumento(this.id);
                                }

                                vm.carregarDocumentos(this.id);
                                modal.close();
                            })
                        )
                );
        });
    }

    private Component documentoForm() {
        return new Column()
            .gap(8)
            .children(
                new Dropdown<>(List.of(TipoDocumentoVeiculo.values()))
                    .label("Tipo")
                    .bindTo(vm.tipoDocumentoVeiculo)
                    .onChange(tipo -> {
                        boolean seguro = tipo == TipoDocumentoVeiculo.SEGURO;

                        vm.eSeguro.set(seguro);
                        if (!seguro) {
                            vm.seguradoraDocumentoVeiculo.set("");
                            vm.coberturaDocumentoVeiculo.set("");
                        }
                    }),
                new TextField().label("Titulo").bindTo(vm.tituloDocumentoVeiculo),
                new DatePicker().label("Data de Validade").bindTo(vm.dataValidadeDocumentoVeiculo),
                new When(vm.eSeguro)
                    .children(
                        new TextField()
                            .label("Seguradora")
                            .modifier(new Modifier().visible(vm.eSeguro.get()))
                            .bindTo(vm.seguradoraDocumentoVeiculo),
                        new TextField()
                            .label("Cobertura")
                            .modifier(new Modifier().visible(vm.eSeguro.get()))
                            .bindTo(vm.coberturaDocumentoVeiculo)
                    ),
                new TextField().label("Valor(€)").decimal().bindTo(vm.valorDocumentoVeiculo),
                new TextField().label("Notas").multiline().bindTo(vm.notasDocumentoVeiculo)
            );
    }

    private void preencherDocumento(DocumentosVeiculo documento) {
        vm.tipoDocumentoVeiculo.set(documento.getTipo());
        vm.tituloDocumentoVeiculo.set(documento.getTitulo());
        vm.dataValidadeDocumentoVeiculo.set(documento.getDataValidade());
        vm.eSeguro.set(documento.getTipo() == TipoDocumentoVeiculo.SEGURO);
        vm.seguradoraDocumentoVeiculo
            .set(documento.getSeguradora() == null ? "" : documento.getSeguradora());
        vm.coberturaDocumentoVeiculo
            .set(documento.getCobertura() == null ? "" : documento.getCobertura());
        vm.valorDocumentoVeiculo.set(documento.getValor());
        vm.notasDocumentoVeiculo.set(documento.getNotas() == null ? "" : documento.getNotas());
    }

    private void limparDocumento() {
        vm.tipoDocumentoVeiculo.set(TipoDocumentoVeiculo.NONE);
        vm.tituloDocumentoVeiculo.set("");
        vm.dataValidadeDocumentoVeiculo.set(null);
        vm.eSeguro.set(false);
        vm.seguradoraDocumentoVeiculo.set("");
        vm.coberturaDocumentoVeiculo.set("");
        vm.valorDocumentoVeiculo.set(0.00);
        vm.notasDocumentoVeiculo.set("");
    }
}
