package components;

import fabiorodrigues.bricks.components.Badge;
import fabiorodrigues.bricks.components.BadgeVariant;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.TipoExpiracoes;

public class ExpiracaoCard {

    private final String titulo;
    private final String subTitulo;
    private final int dias;
    private final TipoExpiracoes tipo;

    public ExpiracaoCard(String titulo, String subTitulo, int dias, TipoExpiracoes tipo) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.dias = dias;
        this.tipo = tipo;
    }

    private Component renderTipo() {
        BadgeVariant variant = this.tipo == TipoExpiracoes.BREVE ? BadgeVariant.WARNING : BadgeVariant.DANGER;

        String texto = this.tipo == TipoExpiracoes.BREVE ? "Expira em Breve" : "Expirado";
        return new Badge(texto).variant(variant).soft();
    }

    private Component renderTempo() {
        Color corTexto = this.tipo == TipoExpiracoes.BREVE ? Color.rgb(187, 77, 0, 1) : Color
            .rgb(193, 0, 7, 1);

        String texto;
        if (this.tipo == TipoExpiracoes.BREVE) {
            texto = this.dias + " dias restantes";
        } else {
            texto = this.dias + " dias em atraso";
        }
        return new Column()
            .gap(0)
            .modifier(new Modifier().fillMaxHeight().alignment(Pos.CENTER))
            .children(new Text(texto).modifier(new Modifier().bold().textColor(corTexto).bold()));
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
