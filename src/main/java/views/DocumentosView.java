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
import models.Pessoal.DocumentosPessoal;
import viewModels.DocumentosViewModel;

public class DocumentosView extends BricksScene {

    private final DocumentosViewModel vm = new DocumentosViewModel();

    public DocumentosView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.vm.carregarDocumentos();
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(
                    "Documentos", "Garantias, contratos, faturas e outros", "fas-plus", "Novo Documento"
                ).render(),
                new ItemsColumn<DocumentosPessoal>()
                    .gap(10)
                    .columns(3)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem Documentos")))
                    .items(this.vm.listDocumentos)
                    .item(documento -> new DocumentoCard(documento).render())
            );
    }
}
