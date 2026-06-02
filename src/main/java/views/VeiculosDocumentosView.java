package views;

import components.DocumentoCard;
import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
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

    public VeiculosDocumentosView(
                                  BricksApplication app, int id, String nome, int ano, String matricula, String foto) {
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
                new Titulo(
                    this.app, this.nome, String
                        .valueOf(this.ano) + "." + this.matricula, "fas-plus", "Adicionar"
                ).render(),
                new ItemsColumn<DocumentosVeiculo>()
                    .gap(10)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem Documentos")))
                    .items(this.vm.listDocumentos.get())
                    .item(documento -> new DocumentoCard(documento).render())
            );
    }
}
