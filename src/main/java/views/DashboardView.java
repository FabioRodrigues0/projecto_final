package views;

import components.ExpiracaoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Icon;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;
import models.Expiracoes;
import viewModels.DashboardViewModel;

public class DashboardView extends BricksScene {

    private final DashboardViewModel vm = new DashboardViewModel();
    private final BricksApplication app;

    public DashboardView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
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
                new Titulo(
                    this.app, "Dashboard", "Visão geral dos seus documentos e subscrições", "fas-bell-slash", "Ativar Notificacoes"
                ).render(),
                new Row()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        //doc
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
                                                    .modifier(new Modifier().textColor(Color.GRAY))
                                                    .modifier(new Modifier().bold()),
                                                new Text(String.valueOf(vm.qntDocumentos.get()))
                                                    .fontSize(20)
                                                    .modifier(new Modifier().bold())
                                            ),
                                        new Spacer(),
                                        new Icon("fas-file-alt")
                                            .color(Color.web("#1a73e8"))
                                            .size(20)
                                            .modifier(
                                                new Modifier()
                                                    .padding(12)
                                                    .background(Color.web("#e8f0fe"))
                                                    .borderRadius(8)
                                            )
                                    )
                            ),
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .padding(20)
                                            .children(
                                                new Text("Veiculos"),
                                                new Text(String.valueOf(vm.qntVeiculos.get()))
                                            )
                                    )
                            ),
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .padding(20)
                                            .children(
                                                new Text("Subscricoes"),
                                                new Text(String.valueOf(vm.qntSubscricoes.get()))
                                            )
                                    )
                            ),
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .padding(20)
                                            .children(
                                                new Text("Alertas"),
                                                new Text(String.valueOf(vm.qntAlertas.get()))
                                            )
                                    )
                            )
                    ),
                new Card()
                    .elevation(2)
                    .modifier(new Modifier().fillMaxHeight())
                    .children(
                        new Column()
                            .gap(0)
                            .modifier(new Modifier().padding(20).fillMaxHeight())
                            .children(
                                new Text("Proximas Expiracoes (30 dias)")
                                    .modifier(new Modifier().bold().fontSize(18)),
                                new ItemsColumn<Expiracoes>()
                                    .gap(10)
                                    .padding(0)
                                    .modifier(new Modifier().fillMaxHeight())
                                    .items(vm.listExpiracoes)
                                    .emptyState(new Text("Sem Expirações"))
                                    .item(expiracao -> {
                                        return new ExpiracaoCard(
                                            expiracao.titulo(), expiracao.subTitulo(), expiracao
                                                .dias(), expiracao.tipo()
                                        ).render();
                                    })
                            )
                    ),
                new Row()
                    .gap(8)
                    .children(
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .padding(20)
                                            .children(
                                                new Text("Gerir Documentos"),
                                                new Text("Garantias, contratos e faturas")
                                            )
                                    )
                            ),
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .padding(20)
                                            .children(
                                                new Text("Gerir Veiculos"),
                                                new Text("Seguro, IUC e inspecao")
                                            )
                                    )
                            ),
                        new Card()
                            .elevation(2)
                            .modifier(new Modifier().fillMaxWidth())
                            .children(
                                new Row()
                                    .gap(2)
                                    .children(
                                        new Column()
                                            .gap(5)
                                            .padding(20)
                                            .children(
                                                new Text("Gerir Subscricoes"),
                                                new Text("Netflix, Spotify e mais")
                                            )
                                    )
                            )
                    )
            );
    }
}
