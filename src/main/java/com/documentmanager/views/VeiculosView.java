package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.LazyColumn;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import models.Veiculo.Veiculos;
import viewModels.VeiculosViewModel;

public class VeiculosView extends BricksScene {

    private final VeiculosViewModel vm = new VeiculosViewModel();

    public VeiculosView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.vm.carregarVeiculos();
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(
                    "Veiculos", "Seguro, IUC e inspeção de cada veículo", "fas-plus", "Novo Veiculo"
                ).render(),

                new Column()
                    .gap(8)
                    .children(
                        new LazyColumn<Veiculos>()
                            .emptyState(new Card().elevation(2).children(new Text("Sem veiculos")))
                            .items(this.vm.listVeiculos.get())
                    )
            );
    }
}
