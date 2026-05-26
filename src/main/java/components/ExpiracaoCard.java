package components;

import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
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
        Color corBackgroud = this.tipo == TipoExpiracoes.BREVE ? Color.rgb(187, 77, 0, 0.5) : Color
            .rgb(193, 0, 7, 0.5);
        Color corBorder = this.tipo == TipoExpiracoes.BREVE ? Color.rgb(187, 77, 0, 1) : Color
            .rgb(193, 0, 7, 1);

        String texto = this.tipo == TipoExpiracoes.BREVE ? "Expira em Breve" : "Expirado";
        return new Column()
            .gap(0)
            .modifier(new Modifier().border(corBorder, 1).background(corBackgroud))
            .children(new Text(texto));
    }

    private Component renderTempo() {
        Color corTexto = this.tipo == TipoExpiracoes.BREVE ? Color.rgb(187, 77, 0, 1) : Color
            .rgb(193, 0, 7, 1);

        String texto;
        if (this.tipo == TipoExpiracoes.BREVE) {
            texto = this.dias + "dias restantes";
        } else {
            texto = this.dias + "dias em atraso";
        }
        return new Text(texto).modifier(new Modifier().textColor(corTexto));
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
                            .children(new Text(this.titulo), new Text(this.subTitulo)),
                        new Spacer(),
                        renderTempo(),
                        renderTipo()
                    )
            );
    }
}
