package components;

import fabiorodrigues.bricks.components.BadgeVariant;
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
import models.calendario.ItemCalendario;

public class DocumentoCard {
    private final String titulo;
    private final String tipoDocumento;
    private final LocalDate dataValidade;
    private final Runnable onEditar;
    private final Runnable onApagar;

    public DocumentoCard() {
        this("Titulo", "Tipo", null, () -> {
        }, () -> {
        });
    }

    public DocumentoCard(DocumentosVeiculo documento, Runnable onEditar, Runnable onApagar) {
        this(
            documento.getTitulo(), formatTipo(documento.getTipo()), documento
                .getDataValidade(), onEditar, onApagar
        );
    }

    public DocumentoCard(DocumentosPessoal documento, Runnable onEditar, Runnable onApagar) {
        this(
            documento.getTitulo(), formatTipo(documento.getTipo()), documento
                .getDataValidade(), onEditar, onApagar
        );
    }

    public DocumentoCard(
                         String titulo, String tipoDocumento, LocalDate dataValidade, Runnable onEditar, Runnable onApagar) {
        this.titulo = titulo;
        this.tipoDocumento = tipoDocumento;
        this.dataValidade = dataValidade;
        this.onEditar = onEditar;
        this.onApagar = onApagar;
    }

    public DocumentoCard(ItemCalendario item, Runnable onEditar, Runnable onApagar) {
        this(item.getTitulo(), formatTipo(item.getCategoria()), item.getData(), onEditar, onApagar);
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
                "Sem validade", new BadgeEstado("Sem validade", BadgeVariant.INFO), Color.GRAY
            );
        }

        int diasReais = (int) ChronoUnit.DAYS.between(LocalDate.now(), this.dataValidade);
        BadgeEstado badgeEstado = new BadgeEstado(diasReais);

        return new EstadoDocumento(
            DiasRestantes.texto(diasReais), badgeEstado, DiasRestantes.cor(diasReais)
        );
    }

    public Component render() {
        EstadoDocumento estado = estado();

        return new Row()
            .gap(10)
            .modifier(
                new Modifier()
                    .border(Color.GRAY, 1)
                    .fillMaxWidth()
                    .background(Color.WHITE)
                    .borderRadius(8)
                    .alignment(Pos.CENTER_LEFT)
            )
            .children(
                // Barra lateral color
                new Column()
                    .modifier(
                        new Modifier()
                            .width(5)
                            .fillMaxHeight()
                            .background(estado.corTexto())
                            .borderRadius(8)
                    ),

                new Icon("fas-file-alt"),

                new Column()
                    .gap(1)
                    .children(
                        new Text(this.titulo).modifier(new Modifier().bold()),
                        new Row()
                            .gap(0)
                            .children(
                                new Text(this.tipoDocumento),
                                new Text(" · Validade: " + dataValidadeTexto())
                            )
                    ),

                new Spacer(),

                new Text(estado.tempo())
                    .modifier(
                        new Modifier()
                            .bold()
                            .textColor((javafx.scene.paint.Color) estado.corTexto())
                    ),
                estado.badge().render(),
                new IconButton("fas-pen").ghost().onClick(this.onEditar),
                new IconButton("fas-trash-alt")
                    .ghost()
                    .color(Color.RED)
                    .onClick(this.onApagar)
                    .modifier(new Modifier().padding(0, 15, 0, 0))
            );
    }

    private String dataValidadeTexto() {
        return this.dataValidade == null ? "-" : this.dataValidade.toString();
    }

    private record EstadoDocumento(String tempo, BadgeEstado badge, Color corTexto) {
    }
}
