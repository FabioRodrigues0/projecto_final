package components;

import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.When;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.TipoExpiracoes;

public class ExpiracaoCard {
    private final String icon;
    private final String titulo;
    private final String subTitulo;
    private final int dias;
    private final BadgeEstado estado;

    public ExpiracaoCard(String titulo, String subTitulo, int dias, TipoExpiracoes tipo) {
        this.icon = "";
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.dias = dias;
        this.estado = badgeEstado(tipo);
    }

    public ExpiracaoCard(String icon, String titulo, String subTitulo, int dias) {
        this.icon = icon;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.dias = dias;
        this.estado = new BadgeEstado(dias);
    }

    public ExpiracaoCard(String titulo, String subTitulo, int dias) {
        this.icon = "";
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.dias = dias;
        this.estado = new BadgeEstado(dias);
    }

    private Component renderTipo() {
        return this.estado.render();
    }

    private Component renderTempo() {
        return new Column()
            .gap(0)
            .modifier(new Modifier().fillMaxHeight().alignment(Pos.CENTER))
            .children(new DiasRestantes(this.dias).render());
    }

    private BadgeEstado badgeEstado(TipoExpiracoes tipo) {
        if (tipo == TipoExpiracoes.EXPIRADO) {
            return new BadgeEstado(-1);
        }

        return new BadgeEstado(30);
    }

    public Component render() {
        return new Column()
            .gap(5)
            .modifier(new Modifier().border(Color.rgb(225, 231, 239), 1).borderRadius(10))
            .children(
                new Row()
                    .gap(8)
                    .modifier(new Modifier().alignment(Pos.CENTER).padding(10, 10))
                    .children(
                        new When(!this.icon.isBlank())
                            .children(
                                new IconButton(this.icon)
                                    .modifier(
                                        new Modifier()
                                            .width(35)
                                            .height(36)
                                            .background(Color.web("#f1f5f9"))
                                    )
                            ),
                        new Column()
                            .gap(2)
                            .children(
                                new Text(this.titulo).modifier(new Modifier().bold()),
                                new Text(this.subTitulo)
                                    .modifier(new Modifier().textColor(Color.GRAY))
                            ),
                        new Spacer(),
                        renderTempo(),
                        renderTipo()
                    )
            );
    }
}
