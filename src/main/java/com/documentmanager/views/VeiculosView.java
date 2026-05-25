package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import viewModels.VeiculosViewModel;

public class VeiculosView extends BricksScene {

    private final VeiculosViewModel vm = new VeiculosViewModel();

    public VeiculosView(BricksApplication app) {
        super(app);
        use(this.vm);
    }

    @Override
    public Component render() {
        return new Column()
            .gap(2)
            .modifier(new Modifier().padding(30, 0))
            .children(new Titulo("Veiculos", "Seguro, IUC e inspeção de cada veículo").render());
    }
}
