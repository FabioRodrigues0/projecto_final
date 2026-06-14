package views;

import components.DocumentoCard;
import components.DocumentoImportacao;
import components.FormularioModal;
import components.NotificacoesApp;
import components.Titulo;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.DatePicker;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.components.When;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import models.TipoDocumentoVeiculo;
import models.Veiculo.DocumentosVeiculo;
import viewModels.VeiculosDocumentosViewModel;

public class VeiculosDocumentosView extends BricksScene {

    private final VeiculosDocumentosViewModel vm = new VeiculosDocumentosViewModel();
    private final BricksApplication app;
    private final int id;
    private final String nome;
    private final int ano;
    private final String matricula;
    private final String foto;

    public VeiculosDocumentosView(BricksApplication app, int id, String nome, int ano, String matricula, String foto) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarDocumentos(id);
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.matricula = matricula;
        this.foto = foto;
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight().fillMaxWidth())
            .children(
                new Titulo(this.app, this.nome)
                    .subtitulo(this.ano + "." + this.matricula)
                    .botao("fas-plus", "Adicionar")
                    .onClick(() -> abrirDocumentoModal(null))
                    .render(),
                new ItemsColumn<DocumentosVeiculo>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .children(new Text("Sem Documentos"))
                    )
                    .items(this.vm.listDocumentos.get())
                    .item(
                        documento -> new DocumentoCard(
                            documento, () -> abrirDocumentoModal(documento), () -> {
                                if (!Alert
                                    .confirm(
                                        "Confirmar",
                                        "Tem a certeza que pretende apagar este documento?"
                                    )) {
                                    return;
                                }

                                vm.apagarDocumento(documento.getId());
                                vm.carregarDocumentos(this.id);
                                NotificacoesApp.documentoRemovido(app, vm);
                            }
                        ).render()
                    )
            );
    }

    private void abrirDocumentoModal(DocumentosVeiculo documento) {
        boolean update = documento != null;

        if (update) {
            preencherDocumento(documento);
        } else {
            limparDocumento();
        }

        new FormularioModal(app, "Documentos")
            .size(550.0, 620.0)
            .update(update)
            .titles("Novo Documento", "Editar Documento")
            .content(documentoForm())
            .onFileImport((file, content) -> preencherDocumentoImportado(file, content))
            .onClear(this::limparDocumento)
            .onSubmit(() -> {
                if (update) {
                    vm.updateDocumento(documento.getId());
                    NotificacoesApp.documentoAtualizado(app, vm);
                } else {
                    vm.novoDocumento(this.id);
                    NotificacoesApp.documentoCriado(app, vm);
                }
                vm.carregarDocumentos(this.id);
            })
            .show();
    }

    private Component documentoForm() {
        return new Column()
            .gap(8)
            .children(
                new Dropdown<>(List.of(TipoDocumentoVeiculo.values()))
                    .label("Tipo")
                    .bindTo(vm.tipoDocumentoVeiculo)
                    .onChange(tipo -> {
                        boolean seguro = tipo == TipoDocumentoVeiculo.SEGURO;

                        vm.eSeguro.set(seguro);
                        if (!seguro) {
                            vm.seguradoraDocumentoVeiculo.set("");
                            vm.coberturaDocumentoVeiculo.set("");
                        }
                    }),
                new TextField().label("Titulo").bindTo(vm.tituloDocumentoVeiculo),
                new DatePicker().label("Data de Validade").bindTo(vm.dataValidadeDocumentoVeiculo),
                new When(vm.eSeguro)
                    .children(
                        new TextField()
                            .label("Seguradora")
                            .modifier(new Modifier().visible(vm.eSeguro.get()))
                            .bindTo(vm.seguradoraDocumentoVeiculo),
                        new TextField()
                            .label("Cobertura")
                            .modifier(new Modifier().visible(vm.eSeguro.get()))
                            .bindTo(vm.coberturaDocumentoVeiculo)
                    ),
                new TextField().label("Valor(€)").decimal().bindTo(vm.valorDocumentoVeiculo),
                new TextField().label("Notas").multiline().bindTo(vm.notasDocumentoVeiculo)
            );
    }

    private void preencherDocumentoImportado(java.io.File file, String content) {
        TipoDocumentoVeiculo tipo = DocumentoImportacao
            .categoria(TipoDocumentoVeiculo.class, content, TipoDocumentoVeiculo.OUTRO);

        vm.tipoDocumentoVeiculo.set(tipo);
        vm.eSeguro.set(tipo == TipoDocumentoVeiculo.SEGURO);
        vm.tituloDocumentoVeiculo.set(DocumentoImportacao.titulo(file, content));
        DocumentoImportacao.primeiraData(content).ifPresent(vm.dataValidadeDocumentoVeiculo::set);
        DocumentoImportacao
            .valorPorEtiqueta(content, "seguradora", "companhia")
            .ifPresent(vm.seguradoraDocumentoVeiculo::set);
        DocumentoImportacao
            .valorPorEtiqueta(content, "cobertura", "plano")
            .ifPresent(vm.coberturaDocumentoVeiculo::set);
        DocumentoImportacao.primeiroValor(content).ifPresent(vm.valorDocumentoVeiculo::set);
        vm.notasDocumentoVeiculo.set(DocumentoImportacao.texto(content));
    }

    private void preencherDocumento(DocumentosVeiculo documento) {
        vm.tipoDocumentoVeiculo.set(documento.getTipo());
        vm.tituloDocumentoVeiculo.set(documento.getTitulo());
        vm.dataValidadeDocumentoVeiculo.set(documento.getDataValidade());
        vm.eSeguro.set(documento.getTipo() == TipoDocumentoVeiculo.SEGURO);
        vm.seguradoraDocumentoVeiculo
            .set(documento.getSeguradora() == null ? "" : documento.getSeguradora());
        vm.coberturaDocumentoVeiculo
            .set(documento.getCobertura() == null ? "" : documento.getCobertura());
        vm.valorDocumentoVeiculo.set(documento.getValor());
        vm.notasDocumentoVeiculo.set(documento.getNotas() == null ? "" : documento.getNotas());
    }

    private void limparDocumento() {
        vm.tipoDocumentoVeiculo.set(TipoDocumentoVeiculo.NONE);
        vm.tituloDocumentoVeiculo.set("");
        vm.dataValidadeDocumentoVeiculo.set(null);
        vm.eSeguro.set(false);
        vm.seguradoraDocumentoVeiculo.set("");
        vm.coberturaDocumentoVeiculo.set("");
        vm.valorDocumentoVeiculo.set(0.00);
        vm.notasDocumentoVeiculo.set("");
    }
}
