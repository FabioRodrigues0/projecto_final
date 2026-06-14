package views;

import components.DocumentoImportacao;
import components.FormularioModal;
import components.NotificacoesApp;
import components.SubscricaoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Alert;
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
import fabiorodrigues.bricks.style.BricksTheme;
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
                new Titulo(this.app, "Subscrições Digitais")
                    .subtitulo("Netflix, Spotify, software e serviços online")
                    .botao("fas-plus", "Nova Subscricao")
                    .onClick(() -> abrirSubscricaoModal(null, null))
                    .render(),
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
                    .emptyState(
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .children(new Text("Sem subscricoes"))
                    )
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
                                NotificacoesApp.documentoRemovido(app, vm);
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

        new FormularioModal(app, "Subscrições Digitais")
            .size(520.0, 450.0)
            .update(update)
            .titles("Nova Subscricao", "Editar Subscricao")
            .content(subscricaoForm())
            .onFileImport((file, content) -> preencherSubscricaoImportada(file, content))
            .onClear(vm::limparCampos)
            .onSubmit(() -> {
                if (update) {
                    vm.update(subscricao.getId());
                    vm.updateDocumento(documento.getId());
                    NotificacoesApp.atualizado(app, vm);
                } else {
                    vm.novo();
                    NotificacoesApp.criado(app, vm);
                }
                vm.carregarSubscricoes();
                vm.carregarDocumentos();
            })
            .show();
    }

    private void preencherSubscricaoImportada(java.io.File file, String content) {
        vm.servicoSubscricao
            .set(
                DocumentoImportacao
                    .descricaoFatura(content)
                    .orElseGet(() -> DocumentoImportacao.titulo(file, content))
            );
        vm.nomeSubscricao.set(vm.servicoSubscricao.get());
        vm.categoriaSubscricao.set(categoriaSubscricaoImportada(content));
        DocumentoImportacao
            .valorPorEtiqueta(content, "plano", "subscricao", "assinatura")
            .ifPresent(vm.planoSubscricao::set);
        custoSubscricaoImportado(content).ifPresent(vm.custoSubscricao::set);
        DocumentoImportacao.ultimaData(content).ifPresent(vm.dataRenovacaoSubscricao::set);
        vm.estadoSubscricao.set(true);
    }

    private java.util.Optional<Double> custoSubscricaoImportado(String content) {
        java.util.Optional<Double> valorFatura = DocumentoImportacao
            .valorTotalFatura(content)
            .or(
                () -> DocumentoImportacao
                    .valorPorEtiquetaNumerico(
                        content,
                        "amount due",
                        "total due",
                        "balance due",
                        "total a pagar",
                        "valor a pagar",
                        "total"
                    )
            );

        java.util.Optional<Double> valor = valorFatura.isPresent() || DocumentoImportacao
            .pareceFatura(content) ? valorFatura : DocumentoImportacao.primeiroValor(content);

        return valor
            .map(value -> DocumentoImportacao.temPeriodoAnual(content) ? value / 12 : value);
    }

    private TipoDocumentoSubscricao categoriaSubscricaoImportada(String content) {
        TipoDocumentoSubscricao categoria = DocumentoImportacao
            .categoria(TipoDocumentoSubscricao.class, content, TipoDocumentoSubscricao.OUTRO);

        if (categoria != TipoDocumentoSubscricao.OUTRO) {
            return categoria;
        }

        String texto = (vm.servicoSubscricao.get() + " " + content).toLowerCase();
        if (
            texto.contains("netflix") || texto.contains("spotify") || texto
                .contains("disney") || texto.contains("hbo") || texto.contains("prime")
        ) {
            return TipoDocumentoSubscricao.STREAMING;
        }

        if (texto.contains("google one") || texto.contains("icloud") || texto.contains("dropbox")) {
            return TipoDocumentoSubscricao.SERVICO_ONLINE;
        }

        if (texto.contains("adobe") || texto.contains("office") || texto.contains("microsoft")) {
            return TipoDocumentoSubscricao.SOFTWARE;
        }

        return TipoDocumentoSubscricao.OUTRO;
    }

    private Component subscricaoForm() {
        return new Column()
            .gap(8)
            .children(
                new TextField().label("Serviço").bindTo(this.vm.servicoSubscricao),
                new Row()
                    .gap(5)
                    .children(
                        new Dropdown<>(List.of(TipoDocumentoSubscricao.values()))
                            .label("Categoria:")
                            .bindTo(vm.categoriaSubscricao),
                        new TextField().decimal().label("Custo Mensal").bindTo(vm.custoSubscricao)
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
                    .children(new Text("Ativa"), new Checkbox("").bindTo(vm.estadoSubscricao))
            );
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
