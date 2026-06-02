package components;

import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Modal;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;

public class Titulo {
    private final BricksApplication app;
    private final String titulo;
    private final String subTitulo;
    private final String iconButton;
    private final String textButton;
    private final String tituloModal;

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, String tituloModal) {
        this.app = app;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.iconButton = iconButton;
        this.textButton = textButton;
        this.tituloModal = tituloModal;
    }

    public Component render() {
        return new Row()
            .gap(0)
            .children(
                new Column()
                    .gap(8)
                    .children(
                        new Text(this.titulo).fontSize(24).modifier(new Modifier().bold()),
                        new Text(this.subTitulo)
                            .fontSize(13)
                            .modifier(new Modifier().textColor(Color.GRAY))
                    ),
                new Spacer(),
                new IconButton(this.iconButton, this.textButton)
                    .color(BricksTheme.current().colorScheme().onPrimary())
                    .modifier(new Modifier().height(35).padding(0))
                    .onClick(() -> {
                        Modal
                            .show(
                                app,
                                modal -> new Column()
                                    .gap(8)
                                    .children(
                                        new Text(this.tituloModal).fontSize(18),
                                        // TODO receber Componente no Contruturor
                                        // assim e mais facil defenir o que esta dentro do modal
                                        new Row()
                                            .gap(8)
                                            .children(new Button("Cancelar").onClick(() -> {
                                                // TODO receber no contrutor as actions a
                                                // fazer aqui
                                                modal.close();
                                            }), new Button("Adicionar").onClick(() -> {
                                                try {
                                                    // TODO receber no contrutor as actions a
                                                    // fazer aqui
                                                    modal.close();
                                                } catch (RuntimeException e) {
                                                    if (e.getMessage() != null && e
                                                        .getMessage()
                                                        .contains("UNIQUE")) {
                                                        Alert
                                                            .error(
                                                                "Erro",
                                                                "Já existe um cartão com esse" + " número."
                                                            );
                                                    } else {
                                                        Alert
                                                            .error(
                                                                "Erro",
                                                                "Não foi possível criar o cartão."
                                                            );
                                                    }
                                                }
                                            }))
                                    )
                            );
                    })
            );
    }
}
