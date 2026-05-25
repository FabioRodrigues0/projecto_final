package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.LazyColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import viewModels.DashboardViewModel;

public class DashboardView extends BricksScene {

    private final DashboardViewModel vm = new DashboardViewModel();

    public DashboardView(BricksApplication app) {
        super(app);
        use(this.vm);
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20))
            .children(
                new Titulo("Dashboard", "Visão geral dos seus documentos e subscrições").render(),
                new Row()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth())
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
                                                new Text("Documentos"),
                                                new Text(String.valueOf(vm.qntDocumentos.get()))
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
                    .children(
                        new Column()
                            .gap(0)
                            .children(new Text("Proximas Expiracoes (30 dias)"), new LazyColumn<>())
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
