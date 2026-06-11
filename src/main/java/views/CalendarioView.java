package views;

import components.BadgeEstado;
import components.DiasRestantes;
import components.ExpiracaoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Calendar;
import fabiorodrigues.bricks.components.CalendarHighlight;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.EventSource;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.style.Modifier;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.TipoItemCalendario;
import models.calendario.ItemCalendario;
import viewModels.CalendarioViewModel;

public class CalendarioView extends BricksScene {

    private final BricksApplication app;
    private final CalendarioViewModel vm = new CalendarioViewModel();
    private final State<LocalDate> diaAtualBricks = new State<>(LocalDate.now());

    public CalendarioView(BricksApplication app) {
        super(app);
        this.app = app;
        use(this.vm);
        this.vm.carregarExpirações();
    }

    private String formatarCategoria(TipoItemCalendario categoria) {
        if (categoria == null) {
            return "Sem categoria";
        }

        return switch (categoria) {
            case PESSOAL -> "Pessoal";
            case VEICULO -> "Veículo";
            case SUBSCRICAO -> "Subscrição";
        };
    }

    private String iconFontAwesomeCalendario(TipoItemCalendario categoria) {
        if (categoria == null) {
            return "far-file-alt";
        }

        return switch (categoria) {
            case PESSOAL -> "far-file-alt";
            case VEICULO -> "fas-car-side";
            case SUBSCRICAO -> "far-credit-card";
        };
    }

    @Override
    public Component render() {
        return new Column()
            .gap(15)
            .modifier(new Modifier().padding(25, 20).fillMaxHeight())
            .children(
                // Cabeçalho
                new Titulo(this.app, "Calendário")
                    .subtitulo("Todas as datas de validade e renovação")
                    .render(),

                // Legenda
                new Row()
                    .gap(6)
                    .modifier(new Modifier().padding(0, 0, 8, 0))
                    .children(
                        () -> new Circle(5, Color.web("#22c55e")),
                        new Text("Válido")
                            .fontSize(13)
                            .modifier(
                                new Modifier().textColor(Color.web("#475569")).padding(0, 10, 0, 0)
                            ),
                        () -> new Circle(5, Color.web("#f59e0b")),
                        new Text("Expira Em Breve")
                            .fontSize(13)
                            .modifier(
                                new Modifier().textColor(Color.web("#475569")).padding(0, 10, 0, 0)
                            ),
                        () -> new Circle(5, Color.web("#ef4444")),
                        new Text("Expirado")
                            .fontSize(13)
                            .modifier(new Modifier().textColor(Color.web("#475569")))
                    ),

                // Calendário principal
                new Column()
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new Calendar()
                            .bindTo(this.diaAtualBricks)
                            .addSource(
                                new EventSource<>(this.vm.getTodosOsPrazos())
                                    .dateBy(ItemCalendario::getData)
                                    .highlightRules(
                                        List
                                            .of(
                                                new CalendarHighlight(Color.web("#ef4444"), 0),
                                                new CalendarHighlight(Color.web("#f59e0b"), 30),
                                                new CalendarHighlight(
                                                    Color.web("#22c55e"), Long.MAX_VALUE
                                                )
                                            )
                                    )
                                    .labels(
                                        ItemCalendario::getTitulo,
                                        item -> formatarCategoria(item.getCategoria()) + ": " + item
                                            .getData()
                                    )
                                    .component(item -> {
                                        long dias = vm.diasRestantes(item.getData());

                                        return new Column()
                                            .gap(0)
                                            .modifier(new Modifier().fillMaxWidth())
                                            .children(
                                                new Row()
                                                    .gap(8)
                                                    .children(
                                                        new Column()
                                                            .gap(0)
                                                            .children(
                                                                new IconButton(
                                                                    iconFontAwesomeCalendario(
                                                                        item.getCategoria()
                                                                    )
                                                                )
                                                                    .modifier(
                                                                        new Modifier()
                                                                            .background(
                                                                                Color.web("#f1f5f9")
                                                                            )
                                                                            .width(35)
                                                                            .height(36)
                                                                    )
                                                            ),
                                                        new Column()
                                                            .gap(2)
                                                            .modifier(new Modifier().fillMaxWidth())
                                                            .children(
                                                                new Text(item.getTitulo())
                                                                    .modifier(
                                                                        new Modifier().bold()
                                                                    ),
                                                                new Text(
                                                                    item
                                                                        .getData()
                                                                        .toString() + " · " + formatarCategoria(
                                                                            item.getCategoria()
                                                                        )
                                                                )
                                                            ),
                                                        new DiasRestantes(dias).render(),
                                                        new BadgeEstado(dias).render()
                                                    )
                                            );
                                    })
                            )
                            .informacaoDia()
                            .modifier(new Modifier().fillMaxWidth())
                    ),

                // Próximos eventos
                new Column()
                    .gap(10)
                    .modifier(new Modifier().padding(10, 0, 0, 0))
                    .children(
                        new Text("Próximos Eventos").fontSize(18).bold(),
                        new ItemsColumn<ItemCalendario>()
                            .gap(8)
                            .columns(1)
                            .items(this.vm.proximosPrazos)
                            .item(
                                item -> new ExpiracaoCard(
                                    iconFontAwesomeCalendario(item.getCategoria()), item
                                        .getTitulo(), item
                                            .getData()
                                            .toString() + " · " + formatarCategoria(
                                                item.getCategoria()
                                            ), (int) vm.diasRestantes(item.getData())
                                ).render()
                            )
                    )
            );
    }
}
