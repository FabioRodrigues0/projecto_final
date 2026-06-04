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
import java.util.function.Supplier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class Titulo {

    private static final double DEFAULT_MODAL_WIDTH = 500.0;
    private static final double DEFAULT_MODAL_HEIGHT = 400.0;

    private final BricksApplication app;
    private final String titulo;
    private final String subTitulo;
    private final String iconButton;
    private final String textButton;
    private final String tituloModal;
    private final Supplier<Component> modalContent;
    private final Runnable onSubmit;
    private final Runnable onClear;
    private final double modalWidth;
    private final double modalHeight;

    // Construtor simples - sem modal
    public Titulo(BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton) {
        this(app, titulo, subTitulo, iconButton, textButton, "", null, () -> {}, () -> {});
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, String tituloModal
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, tituloModal, (Supplier<Component>) null, null, null
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, Supplier<Component> modalContent, Runnable onSubmit, Runnable onClear
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, textButton, modalContent, onSubmit, onClear
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, Supplier<Component> modalContent, Runnable onSubmit, Runnable onClear, double modalWidth, double modalHeight
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, textButton, modalContent, onSubmit, onClear, modalWidth, modalHeight
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, Component modalContent, Runnable onSubmit, Runnable onClear
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, textButton, modalContent, onSubmit, onClear
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, Component modalContent, Runnable onSubmit, Runnable onClear, double modalWidth, double modalHeight
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, textButton, modalContent, onSubmit, onClear, modalWidth, modalHeight
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, String tituloModal, Component modalContent, Runnable onSubmit, Runnable onClear
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, tituloModal, () -> modalContent, onSubmit, onClear
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, String tituloModal, Component modalContent, Runnable onSubmit, Runnable onClear, double modalWidth, double modalHeight
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, tituloModal, () -> modalContent, onSubmit, onClear, modalWidth, modalHeight
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, String tituloModal, Supplier<Component> modalContent, Runnable onSubmit, Runnable onClear
    ) {
        this(
            app, titulo, subTitulo, iconButton, textButton, tituloModal, modalContent, onSubmit, onClear, DEFAULT_MODAL_WIDTH, DEFAULT_MODAL_HEIGHT
        );
    }

    public Titulo(
                  BricksApplication app, String titulo, String subTitulo, String iconButton, String textButton, String tituloModal, Supplier<Component> modalContent, Runnable onSubmit, Runnable onClear, double modalWidth, double modalHeight
    ) {
        this.app = app;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.iconButton = iconButton;
        this.textButton = textButton;
        this.tituloModal = tituloModal;
        this.modalContent = modalContent;
        this.onSubmit = onSubmit != null ? onSubmit : () -> {};
        this.onClear = onClear != null ? onClear : () -> {};
        this.modalWidth = modalWidth;
        this.modalHeight = modalHeight;
    }

    public Component render() {
        Row row = new Row()
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
                            .showUndecorated(
                                app,
                                this.titulo,
                                this.modalWidth,
                                this.modalHeight,
                                modal -> {
                                    modal.setOnHidden(event -> this.onClear.run());

                                    Column content = new Column()
                                        .gap(8)
                                        .children(new Text(this.tituloModal).fontSize(18));

                                    if (this.modalContent != null) {
                                        content.children(this.modalContent.get());
                                    }

                                    content
                                        .children(
                                            new Row()
                                                .gap(8)
                                                .modifier(
                                                    new Modifier().alignment(Pos.BOTTOM_RIGHT)
                                                )
                                                .children(
                                                    new Button("Cancelar").onClick(modal::close),
                                                    new Button("Adicionar").onClick(() -> {
                                                        try {
                                                            this.onSubmit.run();
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
                                                    })
                                                )
                                        );

                                    return content;
                                }
                            );
                    })
            );

        if (this.iconButton != null && this.textButton != null) {
            row
                .children(
                    new IconButton(this.iconButton, this.textButton)
                        .color(BricksTheme.current().colorScheme().onPrimary())
                        .modifier(new Modifier().height(35).padding(0))
                        .onClick(() -> {
                            Modal.showUndecorated(app, this.titulo, 500.0, 400.0, modal -> {
                                modal.setOnHidden(event -> this.onClear.run());

                                Column content = new Column()
                                    .gap(8)
                                    .children(new Text(this.tituloModal).fontSize(18));

                                if (this.modalContent != null) {
                                    content.children(this.modalContent.get());
                                }

                                content
                                    .children(
                                        new Row()
                                            .gap(8)
                                            .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                                            .children(
                                                new Button("Cancelar").onClick(modal::close),
                                                new Button("Adicionar").onClick(() -> {
                                                    try {
                                                        this.onSubmit.run();
                                                        modal.close();
                                                    } catch (RuntimeException e) {
                                                        if (e.getMessage() != null && e
                                                            .getMessage()
                                                            .contains("UNIQUE")) {
                                                            Alert
                                                                .error(
                                                                    "Erro",
                                                                    "Já existe um registo com esse valor."
                                                                );
                                                        } else {
                                                            Alert
                                                                .error(
                                                                    "Erro",
                                                                    "Não foi possível criar o registo."
                                                                );
                                                        }
                                                    }
                                                })
                                            )
                                    );

                                return content;
                            });
                        })
                );
        }

        return row;
    }
}
