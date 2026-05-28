package components;

import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;

public class Titulo {

    private final String titulo;
    private final String subTitulo;
    private final String iconButton;
    private final String textButton;

    public Titulo(String titulo, String subTitulo, String iconButton, String textButton) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.iconButton = iconButton;
        this.textButton = textButton;
    }

    public Component render() {
        return new Row()
            .gap(0)
            .children(
                new Column()
                    .gap(8)
                    .children(
                        new Text(this.titulo).fontSize(24).modifier(new Modifier().bold()),
                        new Text(this.subTitulo)
                            .fontSize(13)
                            .modifier(new Modifier().textColor(Color.GRAY))
                    ),
                new Spacer(),
                new IconButton(this.iconButton, this.textButton)
                    .color(BricksTheme.current().colorScheme().onPrimary())
                    .modifier(new Modifier().height(30))
            );
    }
}
