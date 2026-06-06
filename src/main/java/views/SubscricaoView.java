package views;

import components.SubscricaoCard;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Checkbox;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Modal;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import javafx.geometry.Pos;
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
                new Row()
                    .gap(0)
                    .children(
                        new Column()
                            .gap(8)
                            .children(
                                new Text("Subscrições Digitais")
                                    .fontSize(24)
                                    .modifier(new Modifier().bold()),
                                new Text("Netflix, Spotify, software e serviços online")
                                    .fontSize(13)
                            ),
                        new Spacer(),
                        new IconButton("fas-plus", "Nova Subscricao")
                            .color(BricksTheme.current().colorScheme().onPrimary())
                            .modifier(new Modifier().height(35).padding(0))
                            .onClick(() -> abrirSubscricaoModal(null, null))
                    ),
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
                new ItemsColumn<DocumentosSubscricao>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem subscricoes")))
                    .items(this.vm.listDocumentosSubscricao)
                    .item(documento -> {
                        Subscricoes subscricao = subscricaoDoDocumento(documento);

                        return new SubscricaoCard(
                            subscricao, documento, () -> abrirSubscricaoModal(
                                subscricao,
                                documento
                            ), () -> {
                                if (!Alert
                                    .confirm(
                                        "Confirmar",
                                        "Tem a certeza que pretende apagar esta subscrição?"
                                    )) {
                                    return;
                                }

                                vm.apagarDocumento(documento.getId());
                                vm.carregarDocumentos();
                            }
                        ).render();
                    })
            );
    }

    private void abrirSubscricaoModal(Subscricoes subscricao, DocumentosSubscricao documento) {
        boolean update = documento != null;

        if (update) {
            preencherSubscricao(subscricao, documento);
        } else {
            vm.limparCampos();
        }

        Modal.showUndecorated(app, "Subscrições Digitais", 520.0, 450.0, modal -> {
            modal.setOnHidden(event -> vm.limparCampos());

            return new Column()
                .gap(8)
                .children(
                    new Text(update ? "Editar Subscricao" : "Nova Subscricao").fontSize(18),
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
                        .children(new Text("Ativa"), new Checkbox("").bindTo(vm.estadoSubscricao)),
                    new Row()
                        .gap(8)
                        .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                        .children(
                            new Button("Cancelar").onClick(modal::close),
                            new Button(update ? "Atualizar" : "Adicionar").onClick(() -> {
                                if (update) {
                                    vm.update(subscricao.getId());
                                    vm.updateDocumento(documento.getId());
                                } else {
                                    vm.novo();
                                }

                                vm.carregarSubscricoes();
                                vm.carregarDocumentos();
                                modal.close();
                            })
                        )
                );
        });
    }

    private void preencherSubscricao(Subscricoes subscricao, DocumentosSubscricao documento) {
        vm.nomeSubscricao.set(subscricao.getNome());
        vm.servicoSubscricao.set(documento.getTitulo());
        vm.categoriaSubscricao.set(documento.getTipo());
        vm.custoSubscricao.set(documento.getCusto());
        vm.planoSubscricao.set(documento.getPlano() == null ? "" : documento.getPlano());
        vm.dataRenovacaoSubscricao.set(documento.getDataRenovacao());
        vm.estadoSubscricao.set(documento.isAtiva());
    }

    private Subscricoes subscricaoDoDocumento(DocumentosSubscricao documento) {
        return this.vm.listSubscricoes
            .get()
            .stream()
            .filter(subscricao -> subscricao.getId() == documento.getSubscricaoId())
            .findFirst()
            .orElse(
                new Subscricoes(documento.getSubscricaoId(), documento.getTitulo(), null, null)
            );
    }

    private String valorTexto(double valor) {
        return String.format("€%.2f", valor);
    }
}
