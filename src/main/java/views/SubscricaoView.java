package views;

import components.SubscricaoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Checkbox;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import models.Subscricao.DocumentosSubscricao;
import models.Subscricao.Subscricoes;
import models.TipoDocumentoSubscricao;
import viewModels.SubscricaoViewModel;

public class SubscricaoView extends BricksScene {

    private final SubscricaoViewModel vm = new SubscricaoViewModel();
    private final BricksApplication app;

    public SubscricaoView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarSubscricoes();
        this.vm.carregarDocumentos();
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
                        ), () -> {
                            vm.novo();
                            vm.carregarSubscricoes();
                            vm.carregarDocumentos();
                            vm.limparCampos();
                        }, () -> {
                            vm.limparCampos();
                        }, 520.0, 450.0
                ).render(),
                new Column()
                    .gap(8)
                    .children(
                        new Row()
                            .gap(8)
                            .children(
                                new Column()
                                    .gap(2)
                                    .children(
                                        new Text("Custo Total Mensal"),
                                        new Text(valorTexto(vm.gastoMensal.get()))
                                            .fontSize(44)
                                            .modifier(new Modifier().bold())
                                    ),
                                new Spacer(),
                                new Column()
                                    .gap(2)
                                    .children(
                                        new Text(
                                            this.vm.subscricoesAtivas.get() + " subscrições ativas"
                                        ),
                                        new Text(valorTexto(vm.gastoAnual.get()) + "/ano")
                                    )
                            )
                    ),
                new ItemsColumn<Subscricoes>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem subscricoes")))
                    .items(this.vm.listSubscricoes)
                    .item(
                        subscricao -> new ItemsColumn<DocumentosSubscricao>()
                            .gap(8)
                            .modifier(new Modifier().fillMaxWidth())
                            .emptyState(
                                new Card()
                                    .elevation(2)
                                    .padding(15)
                                    .children(new Text("Sem documentos"))
                            )
                            .items(
                                this.vm.listDocumentosSubscricao
                                    .get()
                                    .stream()
                                    .filter(
                                        documento -> documento.getSubscricaoId() == subscricao
                                            .getId()
                                    )
                                    .toList()
                            )
                            .item(documento -> new SubscricaoCard(subscricao, documento).render())
                    )
            );
    }

    private String valorTexto(double valor) {
        return String.format("€%.2f", valor);
    }
}
