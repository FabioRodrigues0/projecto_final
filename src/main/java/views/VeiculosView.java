package views;

import components.Titulo;
import components.VeiculosCard;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Text;
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
                    this.app, "Veiculos", "Seguro, IUC e inspeção de cada veículo", "fas-plus", "Novo Veiculo"
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
