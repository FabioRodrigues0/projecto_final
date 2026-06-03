package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Checkbox;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import models.TipoDocumentoSubscricao;
import viewModels.SubscricaoViewModel;

public class SubscricaoView extends BricksScene {

    private final SubscricaoViewModel vm = new SubscricaoViewModel();
    private final BricksApplication app;

    public SubscricaoView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(
                    this.app, "Subscrições Digitais", "Netflix, Spotify, software e serviços online", "fas-plus", "Nova Subscricao", new Column()
                        .gap(8)
                        .children(
                            new TextField().label("Serviço").bindTo(this.vm.servicoSubscricao),
                            new Row()
                                .gap(5)
                                .children(
                                    new Dropdown<>(List.of(TipoDocumentoSubscricao.values()))
                                        .label("Categoria:")
                                        .bindTo(vm.categoriaSubscricao),
                                    new TextField()
                                        .decimal()
                                        .label("Custo Mensal")
                                        .bindTo(vm.custoSubscricao)
                                ),
                            new Row()
                                .gap(5)
                                .children(
                                    new TextField().label("Plano").bindTo(vm.planoSubscricao),
                                    new DatePicker()
                                        .label("Data de Renovação")
                                        .bindTo(vm.dataRenovacaoSubscricao)
                                ),
                            new Row()
                                .gap(3)
                                .children(
                                    new Text("Ativa"),
                                    new Checkbox("").bindTo(vm.estadoSubscricao)
                                )
                        ), () -> {}, () -> {}, 520.0, 450.0
                ).render()
            );
    }
}
