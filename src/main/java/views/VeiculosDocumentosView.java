package views;

import components.DocumentoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.components.When;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
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
                new Titulo(
                    this.app, this.nome, String
                        .valueOf(
                            this.ano
                        ) + "." + this.matricula, "fas-plus", "Adicionar", () -> new Column()
                            .gap(8)
                            .children(
                                new Dropdown<>(List.of(TipoDocumentoVeiculo.values()))
                                    .label("Tipo")
                                    .bindTo(vm.tipoDocumentoVeiculo)
                                    .onChange(tipo -> {
                                        if (tipo == TipoDocumentoVeiculo.SEGURO) {
                                            vm.eSeguro.set(true);
                                            vm.seguradoraDocumentoVeiculo.set("");
                                            vm.coberturaDocumentoVeiculo.set("");
                                        } else {
                                            vm.eSeguro.set(false);
                                            vm.seguradoraDocumentoVeiculo.set("");
                                            vm.coberturaDocumentoVeiculo.set("");
                                        }
                                    }),
                                new TextField().label("Titulo").bindTo(vm.tituloDocumentoVeiculo),
                                new DatePicker()
                                    .label("Data de Validade")
                                    .bindTo(vm.dataValidadeDocumentoVeiculo),
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
                                new TextField()
                                    .label("Valor(€)")
                                    .decimal()
                                    .bindTo(vm.valorDocumentoVeiculo),
                                new TextField()
                                    .label("Notas")
                                    .multiline()
                                    .bindTo(vm.notasDocumentoVeiculo)
                            ), () -> {
                                vm.novoDocumento(this.id);
                                vm.tipoDocumentoVeiculo.set(TipoDocumentoVeiculo.NONE);
                                vm.tituloDocumentoVeiculo.set("");
                                vm.dataValidadeDocumentoVeiculo.set(null);
                                vm.seguradoraDocumentoVeiculo.set("");
                                vm.coberturaDocumentoVeiculo.set("");
                                vm.valorDocumentoVeiculo.set(0.00);
                                vm.notasDocumentoVeiculo.set("");
                                vm.carregarDocumentos(this.id);
                            }, () -> {
                                vm.tipoDocumentoVeiculo.set(TipoDocumentoVeiculo.NONE);
                                vm.tituloDocumentoVeiculo.set("");
                                vm.dataValidadeDocumentoVeiculo.set(null);
                                vm.seguradoraDocumentoVeiculo.set("");
                                vm.coberturaDocumentoVeiculo.set("");
                                vm.valorDocumentoVeiculo.set(0.00);
                                vm.notasDocumentoVeiculo.set("");
                            }, 550.0, 620.0
                ).render(),
                new ItemsColumn<DocumentosVeiculo>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem Documentos")))
                    .items(this.vm.listDocumentos.get())
                    .item(documento -> new DocumentoCard(documento).render())
            );
    }
}
