package views;

import components.ExpiracaoCard;
import components.NotificacoesApp;
import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Icon;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.Expiracoes;
import viewModels.DashboardViewModel;

/**
 * Representa DashboardView na aplicação.
 */
public class DashboardView extends BricksScene {

    private final DashboardViewModel vm = new DashboardViewModel();
    private final BricksApplication app;

    // Recebe as dependências necessárias no construtor da View
    /**
     * Cria uma nova instância.
     *
     * @param app valor usado pela operação
     */
    public DashboardView(BricksApplication app) {
        super(app);
        this.app = app;
        use(this.vm);

        // Carrega todos os dados necessários
        this.vm.carregarExpiracoes();
        this.vm.carregarDocumentos();
        this.vm.carregarVeiculos();
        this.vm.carregarSubscricoes();
    }

    /**
     * Constrói o componente visual.
     *
     * @return resultado da operação
     */
    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(this.app, "Dashboard")
                    .subtitulo("Visão geral dos seus documentos e subscrições")
                    .botao("fas-bell-slash", "Ativar Notificacoes")
                    .onClick(
                        () -> NotificacoesApp
                            .notificacoesSistemaAtivadas(app, vm.listExpiracoes.get())
                    )
                    .render(),
                new Row()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        // Documentos
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .modifier(new Modifier().padding(20).fillMaxWidth())
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .children(
                                                new Text("Documentos")
                                                    .modifier(
                                                        new Modifier().textColor(Color.GRAY).bold()
                                                    ),
                                                new Text(String.valueOf(vm.qntDocumentos.get()))
                                                    .fontSize(20)
                                                    .modifier(new Modifier().bold())
                                            ),
                                        new Spacer(),
                                        new IconButton("fas-file-alt")
                                            .color(Color.web("#155dfc"))
                                            .size(20)
                                            .modifier(
                                                new Modifier()
                                                    .padding(8, 5)
                                                    .background(Color.web("#e7eefe"))
                                                    .borderRadius(8)
                                                    .width(44)
                                                    .height(43)
                                            )
                                    )
                            ),

                        // Veículos
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .modifier(new Modifier().padding(20).fillMaxWidth())
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .children(
                                                new Text("Veiculos")
                                                    .modifier(
                                                        new Modifier().textColor(Color.GRAY).bold()
                                                    ),
                                                new Text(String.valueOf(vm.qntVeiculos.get()))
                                                    .fontSize(20)
                                                    .modifier(new Modifier().bold())
                                            ),
                                        new Spacer(),
                                        new IconButton("fas-car-side")
                                            .color(Color.web("#4f39f6"))
                                            .size(20)
                                            .modifier(
                                                new Modifier()
                                                    .background(Color.web("#ededfe"))
                                                    .borderRadius(8)
                                                    .padding(8, 5)
                                                    .width(44)
                                                    .height(43)
                                            )
                                    )
                            ),

                        // Subscrições
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .modifier(new Modifier().padding(20).fillMaxWidth())
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .children(
                                                new Text("Subscricoes")
                                                    .modifier(
                                                        new Modifier().textColor(Color.GRAY).bold()
                                                    ),
                                                new Text(String.valueOf(vm.qntSubscricoes.get()))
                                                    .fontSize(20)
                                                    .modifier(new Modifier().bold()),
                                                new Text(
                                                    String
                                                        .format(
                                                            "%.2f",
                                                            vm.gastoMensal.get()
                                                        ) + "€/mês"
                                                )
                                                    .fontSize(14)
                                                    .modifier(
                                                        new Modifier().textColor(Color.GRAY).bold()
                                                    )
                                            ),
                                        new Spacer(),
                                        new IconButton("fas-tv")
                                            .color(Color.web("#7f22fe"))
                                            .size(20)
                                            .modifier(
                                                new Modifier()
                                                    .padding(8, 5)
                                                    .background(Color.web("#f2e8fe"))
                                                    .borderRadius(8)
                                                    .width(44)
                                                    .height(43)
                                            )
                                    )
                            ),

                        // Alertas
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .modifier(new Modifier().padding(20).fillMaxWidth())
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .children(
                                                new Text("Alertas")
                                                    .modifier(
                                                        new Modifier().textColor(Color.GRAY).bold()
                                                    ),
                                                new Text(String.valueOf(vm.qntAlertas.get()))
                                                    .fontSize(20)
                                                    .modifier(new Modifier().bold())
                                            ),
                                        new Spacer(),
                                        new IconButton("fas-exclamation-triangle")
                                            .modifier(
                                                new Modifier()
                                                    .background(
                                                        BricksTheme
                                                            .current()
                                                            .colorScheme()
                                                            .surfaceVariant()
                                                    )
                                                    .width(35)
                                                    .height(36)
                                            )
                                            .color(Color.web("#e7000b"))
                                            .size(20)
                                            .modifier(
                                                new Modifier()
                                                    .padding(8, 5)
                                                    .background(Color.web("#fce5e6"))
                                                    .borderRadius(8)
                                                    .width(44)
                                                    .height(43)
                                            )
                                    )
                            )
                    ),

                // Próximas Expirações
                new Card()
                    .elevation(2)
                    .background(BricksTheme.current().colorScheme().surface())
                    .modifier(new Modifier().fillMaxHeight())
                    .children(
                        new Column()
                            .gap(10)
                            .modifier(new Modifier().padding(20).fillMaxHeight())
                            .children(
                                new Row()
                                    .gap(5)
                                    .children(
                                        new Icon("fas-exclamation-triangle")
                                            .color(Color.web("#fe9a00")),
                                        new Text("Proximas Expiracoes (30 dias)")
                                            .modifier(new Modifier().bold().fontSize(18))
                                    ),
                                new ItemsColumn<Expiracoes>()
                                    .gap(10)
                                    .padding(0)
                                    .modifier(new Modifier().fillMaxHeight())
                                    .items(vm.listExpiracoes)
                                    .emptyState(new Text("Sem Expirações"))
                                    .item(
                                        expiracao -> new ExpiracaoCard(
                                            expiracao.titulo(), expiracao.subTitulo(), expiracao
                                                .dias(), expiracao.tipo()
                                        ).render()
                                    )
                            )
                    ),

                // Atalhos de Gestão Fundos
                new Row()
                    .gap(8)
                    .children(
                        new Card()
                            .padding(20)
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(10)
                                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT))
                                    .children(
                                        new IconButton("fas-file-alt")
                                            .color(
                                                BricksTheme
                                                    .current()
                                                    .colorScheme()
                                                    .onSurfaceVariant()
                                            )
                                            .modifier(
                                                new Modifier()
                                                    .width(35)
                                                    .height(36)
                                                    .background(
                                                        BricksTheme
                                                            .current()
                                                            .colorScheme()
                                                            .surfaceVariant()
                                                    )
                                            ),
                                        new Column()
                                            .gap(2)
                                            .modifier(new Modifier())
                                            .children(
                                                new Text("Gerir Documentos").bold(),
                                                new Text("Garantias, contratos e faturas")
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                            )
                                    )
                            )
                            .onClick(() -> app.navigateTo(new DocumentosView(app))),
                        new Card()
                            .padding(20)
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(10)
                                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT))
                                    .children(
                                        new IconButton("fas-car-side")
                                            .color(
                                                BricksTheme
                                                    .current()
                                                    .colorScheme()
                                                    .onSurfaceVariant()
                                            )
                                            .modifier(
                                                new Modifier()
                                                    .width(35)
                                                    .height(36)
                                                    .background(
                                                        BricksTheme
                                                            .current()
                                                            .colorScheme()
                                                            .surfaceVariant()
                                                    )
                                            ),
                                        new Column()
                                            .gap(2)
                                            .modifier(new Modifier())
                                            .children(
                                                new Text("Gerir Veiculos").bold(),
                                                new Text("Seguro, IUC e inspecao")
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                            )
                                    )
                            )
                            .onClick(() -> app.navigateTo(new VeiculosView(app))),
                        new Card()
                            .padding(20)
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .modifier(new Modifier().fillMaxWidth().alignment(Pos.CENTER))
                            .children(
                                new Row()
                                    .gap(10)
                                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT))
                                    .children(
                                        new IconButton("fas-credit-card")
                                            .color(
                                                BricksTheme
                                                    .current()
                                                    .colorScheme()
                                                    .onSurfaceVariant()
                                            )
                                            .modifier(
                                                new Modifier()
                                                    .width(35)
                                                    .height(36)
                                                    .background(
                                                        BricksTheme
                                                            .current()
                                                            .colorScheme()
                                                            .surfaceVariant()
                                                    )
                                            ),
                                        new Column()
                                            .gap(2)
                                            .children(
                                                new Text("Gerir Subscricoes").bold(),
                                                new Text("Netflix, Spotify e mais")
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                            )
                                    )
                            )
                            .onClick(() -> app.navigateTo(new SubscricaoView(app)))
                    )
            );
    }
}
