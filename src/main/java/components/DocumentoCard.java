package components;

import fabiorodrigues.bricks.components.Badge;
import fabiorodrigues.bricks.components.BadgeVariant;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Icon;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.Pessoal.DocumentosPessoal;
import models.Veiculo.DocumentosVeiculo;

public class DocumentoCard {
    private final String titulo;
    private final String tipoDocumento;
    private final LocalDate dataValidade;

    public DocumentoCard() {
        this("Titulo", "Tipo", null);
    }

    public DocumentoCard(DocumentosVeiculo documento) {
        this(documento.getTitulo(), formatTipo(documento.getTipo()), documento.getDataValidade());
    }

    public DocumentoCard(DocumentosPessoal documento) {
        this(documento.getTitulo(), formatTipo(documento.getTipo()), documento.getDataValidade());
    }

    public DocumentoCard(String titulo, String tipoDocumento, LocalDate dataValidade) {
        this.titulo = titulo;
        this.tipoDocumento = tipoDocumento;
        this.dataValidade = dataValidade;
    }

    private static String formatTipo(Enum<?> tipo) {
        if (tipo == null) {
            return "Sem tipo";
        }

        String texto = tipo.name().replace("_", " ").toLowerCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    private EstadoDocumento estado() {
        if (this.dataValidade == null) {
            return new EstadoDocumento(
                "Sem validade", "Sem validade", BadgeVariant.INFO, Color.GRAY
            );
        }

        int diasReais = (int) ChronoUnit.DAYS.between(LocalDate.now(), this.dataValidade);
        int dias = Math.abs(diasReais);

        if (diasReais < 0) {
            return new EstadoDocumento(
                dias + " dias em atraso", "Expirado", BadgeVariant.DANGER, Color.rgb(193, 0, 7)
            );
        }

        if (diasReais <= 30) {
            return new EstadoDocumento(
                dias + " dias restantes", "Expira em breve", BadgeVariant.WARNING, Color
                    .rgb(187, 77, 0)
            );
        }

        return new EstadoDocumento(
            dias + " dias restantes", "Valido", BadgeVariant.SUCCESS, Color.rgb(20, 120, 55)
        );
    }

    public Component render() {
        EstadoDocumento estado = estado();

        return new Card()
            .elevation(2)
            .padding(15)
            .modifier(new Modifier().fillMaxWidth())
            .children(
                new Row()
                    .gap(10)
                    .modifier(new Modifier().alignment(Pos.CENTER))
                    .children(
                        new Icon("fas-file-alt"),
                        new Column()
                            .gap(1)
                            .children(
                                new Text(this.titulo).modifier(new Modifier().bold()),
                                new Row()
                                    .gap(0)
                                    .children(
                                        new Text(this.tipoDocumento),
                                        new Text(" validade: " + dataValidadeTexto())
                                    )
                            ),
                        new Spacer(),
                        new Text(estado.tempo())
                            .modifier(new Modifier().bold().textColor(estado.corTexto())),
                        new Badge(estado.badge()).soft().variant(estado.variant()),
                        new IconButton("fas-pen").ghost(),
                        new IconButton("fas-trash-alt").ghost().color(Color.RED)
                    )
            );
    }

    private String dataValidadeTexto() {
        return this.dataValidade == null ? "-" : this.dataValidade.toString();
    }

    private record EstadoDocumento(String tempo, String badge, BadgeVariant variant,
                                   Color corTexto) {
    }
}
