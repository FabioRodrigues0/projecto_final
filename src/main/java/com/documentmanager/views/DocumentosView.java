package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import viewModels.DocumentosViewModel;

public class DocumentosView extends BricksScene {

    private final DocumentosViewModel vm = new DocumentosViewModel();

    public DocumentosView(BricksApplication app) {
        super(app);
        use(this.vm);
    }

    @Override
    public Component render() {
        return new Column().gap(2).modifier(new Modifier().padding(30, 0)).children(
                new Titulo("Documentos", "Garantias, contratos, faturas e outros").render());
    }
}
