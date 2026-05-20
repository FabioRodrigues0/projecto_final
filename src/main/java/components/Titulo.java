package components;

import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;

public class Titulo {

    private final String titulo;
    private final String subTitulo;

    public Titulo(String titulo, String subTitulo) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
    }

    public Component render() {
        return new Column().gap(8).children(
                new Text(this.titulo).fontSize(24).modifier(new Modifier().bold()),
                new Text(this.subTitulo).fontSize(13)
                        .modifier(new Modifier().textColor(Color.GRAY)));
    }
}
