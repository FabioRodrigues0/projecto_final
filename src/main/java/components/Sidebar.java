package components;

import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;
import views.DashboardView;
import views.DocumentosView;
import views.SubscricaoView;
import views.VeiculosView;

public class Sidebar {

    private final BricksApplication app;
    private final boolean isOpen;

    public Sidebar(BricksApplication app, boolean isOpen) {
        this.app = app;
        this.isOpen = isOpen;
    }

    public Component render() {
        return new Column()
            .gap(5)
            .modifier(new Modifier().visible(isOpen).border(Color.DARKGRAY, 2))
            .children(
                new Button("Dashboard")
                    .modifier(new Modifier().size(150, 50))
                    .onClick(() -> app.navigateTo(new DashboardView(app))),
                new Button("Documentos")
                    .modifier(new Modifier().size(150, 50))
                    .onClick(() -> app.navigateTo(new DocumentosView(app))),
                new Button("Veiculos")
                    .modifier(new Modifier().size(150, 50))
                    .onClick(() -> app.navigateTo(new VeiculosView(app))),
                new Button("Subscricoes")
                    .modifier(new Modifier().size(150, 50))
                    .onClick(() -> app.navigateTo(new SubscricaoView(app)))
            );
    }
}
