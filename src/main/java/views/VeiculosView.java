package views;

import components.VeiculosCard;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
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
import javafx.geometry.Pos;
import models.Veiculo.Veiculos;
import viewModels.VeiculosViewModel;

public class VeiculosView extends BricksScene {

    private final VeiculosViewModel vm = new VeiculosViewModel();
    private final BricksApplication app;

    public VeiculosView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarVeiculos();
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
                                new Text("Veiculos").fontSize(24).modifier(new Modifier().bold()),
                                new Text("Seguro, IUC e inspeção de cada veículo").fontSize(13)
                            ),
                        new Spacer(),
                        new IconButton("fas-plus", "Novo Veiculo")
                            .color(BricksTheme.current().colorScheme().onPrimary())
                            .modifier(new Modifier().height(35).padding(0))
                            .onClick(() -> abrirVeiculoModal(null))
                    ),
                new ItemsColumn<Veiculos>()
                    .gap(10)
                    .columns(3)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem veiculos")))
                    .items(this.vm.listVeiculos)
                    .item(
                        veiculo -> new VeiculosCard(
                            this.app, veiculo, () -> abrirVeiculoModal(veiculo), () -> {
                                if (!Alert
                                    .confirm(
                                        "Confirmar",
                                        "Tem a certeza que pretende apagar este veículo?"
                                    )) {
                                    return;
                                }

                                vm.apagar(veiculo.getId());
                                vm.carregarVeiculos();
                            }
                        ).render()
                    )
            );
    }

    private void abrirVeiculoModal(Veiculos veiculo) {
        boolean update = veiculo != null;

        if (update) {
            preencherVeiculo(veiculo);
        } else {
            limparVeiculo();
        }

        Modal.showUndecorated(app, "Veiculos", 520.0, 480.0, modal -> {
            modal.setOnHidden(event -> limparVeiculo());

            return new Column()
                .gap(8)
                .children(
                    new Text(update ? "Editar Veiculo" : "Novo Veiculo").fontSize(18),
                    veiculoForm(),
                    new Row()
                        .gap(8)
                        .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                        .children(
                            new Button("Cancelar").onClick(modal::close),
                            new Button(update ? "Atualizar" : "Adicionar").onClick(() -> {
                                if (update) {
                                    vm.update(veiculo.getId());
                                } else {
                                    vm.novo();
                                }

                                vm.carregarVeiculos();
                                modal.close();
                            })
                        )
                );
        });
    }

    private Component veiculoForm() {
        return new Column()
            .gap(8)
            .children(
                new Row()
                    .gap(5)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new TextField()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Marca")
                            .bindTo(vm.marcaVeiculo),
                        new TextField()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Modelo")
                            .bindTo(vm.modeloVeiculo)
                    ),
                new Row()
                    .gap(5)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new TextField()
                            .number()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Ano")
                            .bindTo(vm.anoVeiculo),
                        new TextField()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Matricula")
                            .bindTo(vm.matriculaVeiculo)
                    ),
                new TextField().multiline().label("Notas").bindTo(vm.notasVeiculo)
            );
    }

    private void preencherVeiculo(Veiculos veiculo) {
        vm.marcaVeiculo.set(veiculo.getNome());
        vm.modeloVeiculo.set("");
        vm.anoVeiculo.set(veiculo.getAno());
        vm.matriculaVeiculo.set(veiculo.getMatricula());
        vm.notasVeiculo.set("");
    }

    private void limparVeiculo() {
        vm.marcaVeiculo.set("");
        vm.modeloVeiculo.set("");
        vm.anoVeiculo.set(null);
        vm.matriculaVeiculo.set("");
        vm.notasVeiculo.set("");
    }
}
