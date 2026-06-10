package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Checkbox;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;

public class SettingsView extends BricksScene {

    private final BricksApplication app;

    public SettingsView(BricksApplication app) {
        super(app);
        this.app = app;
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(this.app, "Settings")
                    .subtitulo("Preferências e configuração da aplicação")
                    .render(),
                new Card()
                    .padding(15)
                    .elevation(2)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new Checkbox("Notificacões"),
                        new Dropdown<>(List.of("Light", "Dark")).label("Tema")

                    )
            );
    }
}
