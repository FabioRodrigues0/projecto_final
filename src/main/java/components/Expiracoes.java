package components;

import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;
import models.TipoExpiracoes;

public class Expiracoes {

    private final BricksApplication app;
    private final String titulo;
    private final String subTitule;
    private final int dias;
    private final TipoExpiracoes tipo;

    public Expiracoes(BricksApplication app, String titulo, String subTitulo, int dias, TipoExpiracoes tipo) {
        this.app = app;
        this.titulo = titulo;
        this.subTitule = subTitulo;
        this.dias = dias;
        this.tipo = tipo;
    }

    public Component render() {
        return new Column()
            .gap(5)
            .modifier(new Modifier().border(Color.DARKGRAY, 2))
            .children(
                new Row()
                    .gap(8)
                    .children(
                        new Column()
                            .gap(2)
                            .children(new Text(this.titulo), new Text(this.subTitule)),
                        new Spacer()
                    )
            );
    }
}
