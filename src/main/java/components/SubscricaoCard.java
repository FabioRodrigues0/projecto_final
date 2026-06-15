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
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.Subscricao.DocumentosSubscricao;
import models.Subscricao.Subscricoes;

/**
 * Representa SubscricaoCard na aplicação.
 */
public class SubscricaoCard {

    private final String nome;
    private final String tipo;
    private final String plano;
    private final double custo;
    private final LocalDate dataRenovacao;
    private final String icon;
    private final Runnable onEditar;
    private final Runnable onApagar;

    /**
     * Cria uma nova instância.
     *
     * @param subscricao valor usado pela operação
     * @param documento  valor usado pela operação
     * @param onEditar   valor usado pela operação
     * @param onApagar   valor usado pela operação
     */
    public SubscricaoCard(
                          Subscricoes subscricao, DocumentosSubscricao documento, Runnable onEditar, Runnable onApagar) {
        this.nome = subscricao.getNome();
        this.tipo = formatTipo(documento.getTipo());
        this.plano = documento.getPlano();
        this.custo = documento.getCusto();
        this.dataRenovacao = documento.getDataRenovacao();
        this.icon = resolverIcon(subscricao.getNome(), documento);
        this.onEditar = onEditar;
        this.onApagar = onApagar;
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
     * Executa a operação resolverIcon.
     *
     * @param nome      valor usado pela operação
     * @param documento valor usado pela operação
     * @return resultado da operação
     */
    private static String resolverIcon(String nome, DocumentosSubscricao documento) {
        String texto = normalizar(nome + " " + documento.getTitulo());

        if (texto.contains("spotify")) {
            return "fab-spotify";
        }
        if (texto.contains("youtube")) {
            return "fab-youtube";
        }
        if (texto.contains("prime") || texto.contains("amazon")) {
            return "fab-amazon";
        }
        if (texto.contains("apple") || texto.contains("icloud")) {
            return "fab-apple";
        }
        if (texto.contains("google") || texto.contains("drive") || texto.contains("workspace")) {
            return "fab-google";
        }
        if (texto.contains("microsoft") || texto.contains("office") || texto.contains("onedrive")) {
            return "fab-microsoft";
        }
        if (texto.contains("dropbox")) {
            return "fab-dropbox";
        }
        if (texto.contains("github")) {
            return "fab-github";
        }
        if (texto.contains("discord")) {
            return "fab-discord";
        }
        if (texto.contains("twitch")) {
            return "fab-twitch";
        }
        if (texto.contains("figma")) {
            return "fab-figma";
        }
        if (texto.contains("slack")) {
            return "fab-slack";
        }
        if (texto.contains("netflix") || texto.contains("disney") || texto.contains("hbo") || texto
            .contains("max")) {
            return "fas-play-circle";
        }

        if (documento.getTipo() == null) {
            return "fas-star";
        }

        return switch (documento.getTipo()) {
            case STREAMING -> "fas-play-circle";
            case SOFTWARE -> "fas-laptop-code";
            case SERVICO_ONLINE -> "fas-cloud";
            case OUTRO, NONE -> "fas-star";
        };
    }

    /**
     * Executa a operação normalizar.
     *
     * @param texto valor usado pela operação
     * @return resultado da operação
     */
    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        return Normalizer
            .normalize(texto, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")
            .toLowerCase(Locale.ROOT);
    }

    /**
     * Executa a operação estado.
     *
     * @return resultado da operação
     */
    private EstadoSubscricao estado() {
        if (this.dataRenovacao == null) {
            return new EstadoSubscricao(
                "Sem renovacao", new BadgeEstado("Sem renovacao", BadgeVariant.INFO), Color.GRAY
            );
        }

        int diasReais = (int) ChronoUnit.DAYS.between(LocalDate.now(), this.dataRenovacao);
        BadgeEstado badgeEstado = new BadgeEstado(diasReais);

        return new EstadoSubscricao(
            DiasRestantes.texto(diasReais), badgeEstado, DiasRestantes.cor(diasReais)
        );
    }

    /**
     * Constrói o componente visual.
     *
     * @return resultado da operação
     */
    public Component render() {
        EstadoSubscricao estado = estado();

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
                                new Icon(this.icon)
                                    .color(BricksTheme.current().colorScheme().onSurfaceVariant()),
                                new Column()
                                    .gap(2)
                                    .children(
                                        new Text(this.nome).modifier(new Modifier().bold()),
                                        new Row()
                                            .gap(4)
                                            .children(
                                                new Text(this.tipo)
                                                    .fontSize(12)
                                                    .modifier(new Modifier().textColor(Color.GRAY)),
                                                new Text(planoTexto())
                                                    .fontSize(12)
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                            )
                                    ),
                                new Spacer(),
                                new Column()
                                    .gap(2)
                                    .modifier(new Modifier().alignment(Pos.CENTER))
                                    .children(
                                        new Text(valorTexto()).modifier(new Modifier().bold()),
                                        new Text(estado.tempo())
                                            .modifier(
                                                new Modifier().bold().textColor(estado.corTexto())
                                            )
                                    ),
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
     * Executa a operação planoTexto.
     *
     * @return resultado da operação
     */
    private String planoTexto() {
        if (this.plano == null || this.plano.isBlank()) {
            return "-";
        }

        return this.plano;
    }

    /**
     * Executa a operação valorTexto.
     *
     * @return resultado da operação
     */
    private String valorTexto() {
        return String.format("€%.2f", this.custo);
    }

    /**
     * Representa os dados imutáveis de EstadoSubscricao.
     */
    private record EstadoSubscricao(String tempo, BadgeEstado badge, Color corTexto) {
    }
}
