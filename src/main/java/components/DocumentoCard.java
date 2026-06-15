package components;

import fabiorodrigues.bricks.components.BadgeVariant;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Icon;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.Pessoal.DocumentosPessoal;
import models.Veiculo.DocumentosVeiculo;
import models.calendario.ItemCalendario;

/**
 * Representa DocumentoCard na aplicação.
 */
public class DocumentoCard {
    private final String titulo;
    private final String tipoDocumento;
    private final LocalDate dataValidade;
    private final Runnable onEditar;
    private final Runnable onApagar;

    /**
     * Cria uma nova instância.
     */
    public DocumentoCard() {
        this("Titulo", "Tipo", null, () -> {
        }, () -> {
        });
    }

    /**
     * Cria uma nova instância.
     *
     * @param documento valor usado pela operação
     * @param onEditar  valor usado pela operação
     * @param onApagar  valor usado pela operação
     */
    public DocumentoCard(DocumentosVeiculo documento, Runnable onEditar, Runnable onApagar) {
        this(
            documento.getTitulo(), formatTipo(documento.getTipo()), documento
                .getDataValidade(), onEditar, onApagar
        );
    }

    /**
     * Cria uma nova instância.
     *
     * @param documento valor usado pela operação
     * @param onEditar  valor usado pela operação
     * @param onApagar  valor usado pela operação
     */
    public DocumentoCard(DocumentosPessoal documento, Runnable onEditar, Runnable onApagar) {
        this(
            documento.getTitulo(), formatTipo(documento.getTipo()), documento
                .getDataValidade(), onEditar, onApagar
        );
    }

    /**
     * Cria uma nova instância.
     *
     * @param titulo        valor usado pela operação
     * @param tipoDocumento valor usado pela operação
     * @param dataValidade  valor usado pela operação
     * @param onEditar      valor usado pela operação
     * @param onApagar      valor usado pela operação
     */
    public DocumentoCard(
                         String titulo, String tipoDocumento, LocalDate dataValidade, Runnable onEditar, Runnable onApagar) {
        this.titulo = titulo;
        this.tipoDocumento = tipoDocumento;
        this.dataValidade = dataValidade;
        this.onEditar = onEditar;
        this.onApagar = onApagar;
    }

    /**
     * Cria uma nova instância.
     *
     * @param item     valor usado pela operação
     * @param onEditar valor usado pela operação
     * @param onApagar valor usado pela operação
     */
    public DocumentoCard(ItemCalendario item, Runnable onEditar, Runnable onApagar) {
        this(item.getTitulo(), formatTipo(item.getCategoria()), item.getData(), onEditar, onApagar);
    }

    /**
     * Executa a operação formatTipo.
     *
     * @param tipo valor usado pela operação
     * @return resultado da operação
     */
    private static String formatTipo(Enum<?> tipo) {
        if (tipo == null) {
            return "Sem tipo";
        }

        String texto = tipo.name().replace("_", " ").toLowerCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    /**
     * Executa a operação estado.
     *
     * @return resultado da operação
     */
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

    /**
     * Constrói o componente visual.
     *
     * @return resultado da operação
     */
    public Component render() {
        EstadoDocumento estado = estado();

        return new Card()
            .elevation(2)
            .padding(0)
            .background(BricksTheme.current().colorScheme().surface())
            .modifier(new Modifier().fillMaxWidth())
            .children(
                new Row()
                    .gap(0)
                    .modifier(new Modifier().fillMaxWidth().alignment(Pos.CENTER_LEFT))
                    .children(
                        new Column()
                            .modifier(
                                new Modifier()
                                    .width(5)
                                    .fillMaxHeight()
                                    .background(estado.corTexto())
                                    .borderRadius(8)
                            ),
                        new Row()
                            .gap(10)
                            .modifier(
                                new Modifier().padding(15).fillMaxWidth().alignment(Pos.CENTER)
                            )
                            .children(
                                new Icon("fas-file-alt")
                                    .color(BricksTheme.current().colorScheme().onSurfaceVariant()),
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
                                    .modifier(new Modifier().bold().textColor(estado.corTexto())),
                                estado.badge().render(),
                                new IconButton("fas-pen")
                                    .ghost()
                                    .color(BricksTheme.current().colorScheme().onSurface())
                                    .onClick(this.onEditar),
                                new IconButton("fas-trash-alt")
                                    .ghost()
                                    .color(Color.RED)
                                    .onClick(this.onApagar)
                            )
                    )
            );
    }

    /**
     * Executa a operação dataValidadeTexto.
     *
     * @return resultado da operação
     */
    private String dataValidadeTexto() {
        return this.dataValidade == null ? "-" : this.dataValidade.toString();
    }

    /**
     * Representa os dados imutáveis de EstadoDocumento.
     */
    private record EstadoDocumento(String tempo, BadgeEstado badge, Color corTexto) {
    }
}
