package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import models.calendario.ItemCalendario;
import viewModels.CalendarioViewModel;

public class CalendarioView extends BricksScene {

    private final BricksApplication app;
    private final CalendarioViewModel vm = new CalendarioViewModel();

    public CalendarioView(BricksApplication app) {
        super(app);
        this.app = app;
        use(this.vm);
        this.vm.carregarExpirações();
    }

    @Override
    public Component render() {
        return new Column()
            .gap(15)
            .modifier(new Modifier().padding(25, 20).fillMaxHeight())
            .children(
                new Titulo(
                    this.app, "Calendário", "Todas as datas de validade e renovação", "fas-calendar-alt", "", ""
                ).render(),
                criarLegendaCores(),
                criarCartaoCalendarioMensal(),
                criarPainelDiaSelecionado(),
                criarPainelProximosEventos()
            );
    }

    private Component criarLegendaCores() {
        return () -> {
            HBox legenda = new HBox(16);
            legenda.setAlignment(Pos.CENTER_LEFT);
            legenda.setPadding(new Insets(0, 0, 8, 0));
            legenda
                .getChildren()
                .addAll(
                    itemLegenda("#22c55e", "Válido"),
                    itemLegenda("#f59e0b", "Expira Em Breve"),
                    itemLegenda("#ef4444", "Expirado")
                );
            return legenda;
        };
    }

    private HBox itemLegenda(String corHex, String texto) {
        HBox item = new HBox(6);
        item.setAlignment(Pos.CENTER_LEFT);
        Circle dot = new Circle(5, javafx.scene.paint.Color.web(corHex));

        javafx.scene.control.Label label = new javafx.scene.control.Label(texto);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

        item.getChildren().addAll(dot, label);
        return item;
    }

    private Component criarCartaoCalendarioMensal() {
        return () -> {
            VBox card = new VBox(0);
            card.setStyle("""
                    -fx-background-color: white;
                    -fx-background-radius: 12px;
                    -fx-border-color: #e2e8f0;
                    -fx-border-radius: 12px;
                    -fx-border-width: 1px;
                """);
            card.setPadding(new Insets(20));

            YearMonth mesVisivel = vm.getMesAtualVisivel();
            String nomeMes = mesVisivel
                .getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "PT"));
            nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);

            HBox cabecalho = new HBox();
            cabecalho.setAlignment(Pos.CENTER_LEFT);
            cabecalho.setPadding(new Insets(0, 0, 16, 0));

            javafx.scene.control.Label labelMes = new javafx.scene.control.Label(
                nomeMes + " " + mesVisivel.getYear()
            );
            labelMes
                .setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
            HBox.setHgrow(labelMes, javafx.scene.layout.Priority.ALWAYS);

            Button btnAnterior = criarBotaoNavegacao("‹");
            btnAnterior.setOnAction(e -> { vm.recuarMes(); rerender(); });

            Button btnProximo = criarBotaoNavegacao("›");
            btnProximo.setOnAction(e -> { vm.avancarMes(); rerender(); });

            cabecalho.getChildren().addAll(labelMes, btnAnterior, btnProximo);
            card.getChildren().add(cabecalho);

            GridPane grid = new GridPane();
            grid.setHgap(0);
            grid.setVgap(0);

            String[] diasSemana = {"seg", "ter", "qua", "qui", "sex", "sáb", "dom"};
            for (int i = 0; i < diasSemana.length; i++) {
                javafx.scene.control.Label h = new javafx.scene.control.Label(diasSemana[i]);
                h.setStyle("-fx-font-size: 12px; -fx-text-fill: #94a3b8;");
                h.setMinWidth(50);
                h.setAlignment(Pos.CENTER);
                grid.add(h, i, 0);
            }

            LocalDate primeiroDia = mesVisivel.atDay(1);
            int diaSemanaPrimeiro = primeiroDia.getDayOfWeek().getValue() - 1;

            int dia = 1;
            int totalDias = mesVisivel.lengthOfMonth();

            for (int javaSemana = 0; dia <= totalDias; javaSemana++) {
                for (int coluna = 0; coluna < 7 && dia <= totalDias; coluna++) {
                    if (javaSemana == 0 && coluna < diaSemanaPrimeiro) continue;

                    LocalDate dataCorrente = mesVisivel.atDay(dia);
                    VBox celula = construirCelulaDia(dataCorrente, dia);
                    grid.add(celula, coluna, javaSemana + 1);
                    dia++;
                }
            }

            card.getChildren().add(grid);
            return card;
        };
    }

    private VBox construirCelulaDia(LocalDate data, int numeroDia) {
        VBox celula = new VBox(2);
        celula.setAlignment(Pos.CENTER);
        celula.setMinWidth(50);
        celula.setMinHeight(50);
        celula.setPadding(new Insets(4));

        boolean eSelecionado = data.equals(vm.getDiaSelecionado());
        boolean eHoje = data.equals(LocalDate.now());

        javafx.scene.control.Label lbDia = new javafx.scene.control.Label(
            String.valueOf(numeroDia)
        );
        lbDia.setAlignment(Pos.CENTER);
        lbDia.setMinWidth(32);
        lbDia.setMinHeight(32);

        if (eSelecionado) {
            lbDia
                .setStyle(
                    "-fx-background-color: transparent; -fx-border-color: #3b82f6; -fx-border-radius: 16px; -fx-border-width: 2px; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1e293b; -fx-alignment: center;"
                );
        } else if (eHoje) {
            lbDia
                .setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #3b82f6; -fx-alignment: center;"
                );
        } else {
            lbDia.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b; -fx-alignment: center;");
        }

        celula.getChildren().add(lbDia);

        ItemCalendario prazoCritico = null;
        for (ItemCalendario item : vm.getTodosOsPrazos()) {
            if (item.getData().equals(data)) {
                if (prazoCritico == null || vm.diasRestantes(item.getData()) < vm
                    .diasRestantes(prazoCritico.getData())) {
                    prazoCritico = item;
                }
            }
        }

        if (prazoCritico != null) {
            Circle dot = new Circle(
                4, javafx.scene.paint.Color.web(vm.obterCorHex(prazoCritico.getData()))
            );
            celula.getChildren().add(dot);
        }

        celula.setStyle("-fx-cursor: hand;");
        celula.setOnMouseClicked(e -> {
            vm.selecionarDia(data);
            rerender();
        });

        return celula;
    }

    private Component criarPainelDiaSelecionado() {
        LocalDate sel = vm.getDiaSelecionado();
        String txtData = sel.getDayOfMonth() + " de " + sel
            .getMonth()
            .getDisplayName(TextStyle.FULL, Locale.of("pt", "PT")) + " " + sel.getYear();

        return new Column()
            .gap(10)
            .children(
                new Text("📅 " + txtData),
                new ItemsColumn<ItemCalendario>()
                    .gap(8)
                    .columns(1)
                    .emptyState(
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().padding(20))
                            .children(new Text("Sem eventos neste dia."))
                    )
                    .items(this.vm.itensDoDia)
                    .item(item -> construirLinhaPrazo(item))
            );
    }

    private Component criarPainelProximosEventos() {
        return new Column()
            .gap(10)
            .modifier(new Modifier().padding(10, 0, 0, 0))
            .children(
                new Text("Próximos Eventos"),
                new ItemsColumn<ItemCalendario>()
                    .gap(8)
                    .columns(1)
                    .items(this.vm.proximosPrazos)
                    .item(item -> construirLinhaPrazo(item))
            );
    }

    private Component construirLinhaPrazo(ItemCalendario item) {
        long dias = vm.diasRestantes(item.getData());
        String diasRestantesText = dias < 0 ? Math
            .abs(dias) + " dias em atraso" : dias + " dias restantes";

        String icone = "📄";
        if ("Veículo".equalsIgnoreCase(item.getCategoria())) {
            icone = "🚗";
        } else if ("Subscrição".equalsIgnoreCase(item.getCategoria())) {
            icone = "💳";
        }

        return new Card()
            .elevation(1)
            .modifier(new Modifier().padding(12).fillMaxWidth())
            .children(
                new Row()
                    .gap(12)
                    .children(
                        new Text(icone),
                        new Column()
                            .gap(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Text(item.getTitulo()),
                                new Text(item.getData().toString() + " · " + item.getCategoria())
                            ),
                        new Text(diasRestantesText)
                    )
            );
    }

    private Button criarBotaoNavegacao(String simbolo) {
        Button btn = new Button(simbolo);
        btn
            .setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 18px; -fx-text-fill: #3b82f6; -fx-cursor: hand; -fx-min-width: 32px; -fx-min-height: 32px;"
            );
        return btn;
    }
}
