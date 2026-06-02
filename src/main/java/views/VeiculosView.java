package views;

import components.Titulo;
import components.VeiculosCard;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
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
                new Titulo(
                    this.app, "Veiculos", "Seguro, IUC e inspeção de cada veículo", "fas-plus", "Novo Veiculo", () -> new Column()
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
                        ), () -> {
                            vm.novo();
                            vm.carregarVeiculos();
                        }, () -> {
                            vm.marcaVeiculo.set("");
                            vm.modeloVeiculo.set("");
                            vm.anoVeiculo.set(null);
                            vm.matriculaVeiculo.set("");
                            vm.notasVeiculo.set("");
                        }
                ).render(),
                new ItemsColumn<Veiculos>()
                    .gap(10)
                    .columns(3)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem veiculos")))
                    .items(this.vm.listVeiculos)
                    .item(veiculo -> new VeiculosCard(this.app, veiculo).render())
            );
    }
}
