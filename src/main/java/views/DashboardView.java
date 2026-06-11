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
import fabiorodrigues.bricks.style.Modifier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.Expiracoes;
import viewModels.DashboardViewModel;

public class DashboardView extends BricksScene {

    private final DashboardViewModel vm = new DashboardViewModel();
    private final BricksApplication app;

    // Recebe as dependências necessárias no construtor da View
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
<<<<<<< HEAD
                                        new Icon("fas-car-alt")
                                            .color(Color.web("#0820fc"))
                                            .size(30)
=======
                                        new IconButton("fas-car-side")
                                            .color(Color.web("#4f39f6"))
                                            .size(20)
>>>>>>> a7da3126a972335c238bfbb9ceeb4f7d8e937094
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
<<<<<<< HEAD
                                        new Icon("fas-tv")
                                            .color(Color.web("#d509e8"))
                                            .size(30)
=======
                                        new IconButton("fas-tv")
                                            .color(Color.web("#7f22fe"))
                                            .size(20)
>>>>>>> a7da3126a972335c238bfbb9ceeb4f7d8e937094
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
<<<<<<< HEAD
                                        new Icon("fas-exclamation-triangle")
                                            .color(Color.web("#ed5509"))
                                            .size(30)
=======
                                        new IconButton("fas-exclamation-triangle")
>>>>>>> a7da3126a972335c238bfbb9ceeb4f7d8e937094
                                            .modifier(
                                                new Modifier()
                                                    .background(Color.web("#f1f5f9"))
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
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
<<<<<<< HEAD
                                    .gap(12)
                                    .modifier(new Modifier().padding(20))
                                    .children(
                                        new Icon("fas-file-alt")
                                            .color(Color.web("#1a73e8"))
                                            .size(24)
                                            .modifier(
                                                new Modifier()
                                                    .padding(10)
                                                    .background(Color.web("#e8f0fe"))
                                                    .borderRadius(8)
                                            ),
                                        new Column()
                                            .gap(4)
                                            .children(
                                                new Text("Gerir Documentos")
                                                    .modifier(new Modifier().bold()),
=======
                                    .gap(10)
                                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT))
                                    .children(
                                        new IconButton("fas-file-alt")
                                            .modifier(
                                                new Modifier()
                                                    .width(35)
                                                    .height(36)
                                                    .background(Color.web("#f1f5f9"))
                                            ),
                                        new Column()
                                            .gap(2)
                                            .modifier(new Modifier())
                                            .children(
                                                new Text("Gerir Documentos").bold(),
>>>>>>> a7da3126a972335c238bfbb9ceeb4f7d8e937094
                                                new Text("Garantias, contratos e faturas")
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                            )
                                    )
                            )
                            .onClick(() -> app.navigateTo(new DocumentosView(app))),
                        new Card()
                            .padding(20)
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
<<<<<<< HEAD
                                    .gap(12)
                                    .modifier(new Modifier().padding(20))
                                    .children(
                                        new Icon("fas-car-alt")
                                            .color(Color.web("#1a73e8"))
                                            .size(24)
                                            .modifier(
                                                new Modifier()
                                                    .padding(10)
                                                    .background(Color.web("#e8f0fe"))
                                                    .borderRadius(8)
                                            ),
                                        new Column()
                                            .gap(4)
                                            .children(
                                                new Text("Gerir Veiculos")
                                                    .modifier(new Modifier().bold()),
=======
                                    .gap(10)
                                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT))
                                    .children(
                                        new IconButton("fas-car-side")
                                            .modifier(
                                                new Modifier()
                                                    .width(35)
                                                    .height(36)
                                                    .background(Color.web("#f1f5f9"))
                                            ),
                                        new Column()
                                            .gap(2)
                                            .modifier(new Modifier())
                                            .children(
                                                new Text("Gerir Veiculos").bold(),
>>>>>>> a7da3126a972335c238bfbb9ceeb4f7d8e937094
                                                new Text("Seguro, IUC e inspecao")
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                            )
                                    )
                            )
                            .onClick(() -> app.navigateTo(new VeiculosView(app))),
                        new Card()
                            .padding(20)
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth().alignment(Pos.CENTER))
                            .children(
                                new Row()
<<<<<<< HEAD
                                    .gap(12)
                                    .modifier(new Modifier().padding(20))
                                    .children(
                                        new Icon("fas-tv")
                                            .color(Color.web("#1a73e8"))
                                            .size(24)
                                            .modifier(
                                                new Modifier()
                                                    .padding(10)
                                                    .background(Color.web("#e8f0fe"))
                                                    .borderRadius(8)
                                            ),
                                        new Column()
                                            .gap(4)
                                            .children(
                                                new Text("Gerir Subscricoes")
                                                    .modifier(new Modifier().bold()),
=======
                                    .gap(10)
                                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT))
                                    .children(
                                        new IconButton("fas-credit-card")
                                            .modifier(
                                                new Modifier()
                                                    .width(35)
                                                    .height(36)
                                                    .background(Color.web("#f1f5f9"))
                                            ),
                                        new Column()
                                            .gap(2)
                                            .children(
                                                new Text("Gerir Subscricoes").bold(),
>>>>>>> a7da3126a972335c238bfbb9ceeb4f7d8e937094
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
