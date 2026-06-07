package components;

import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;

public class Titulo {

    private static final double DEFAULT_MODAL_WIDTH = 500.0;
    private static final double DEFAULT_MODAL_HEIGHT = 400.0;

    private final BricksApplication app;
    private final String titulo;

    private String subTitulo = "";
    private String iconButton = "";
    private String textButton = "";
    private String tituloModal = "";
    private Component modalContent;
    private Runnable onSubmit = () -> {};
    private Runnable onClear = () -> {};
    private Runnable onClick;
    private double modalWidth = DEFAULT_MODAL_WIDTH;
    private double modalHeight = DEFAULT_MODAL_HEIGHT;

    public Titulo(BricksApplication app, String titulo) {
        this.app = app;
        this.titulo = titulo;
    }

    public Titulo subtitulo(String subTitulo) {
        this.subTitulo = subTitulo;
        return this;
    }

    public Titulo botao(String iconButton, String textButton) {
        this.iconButton = iconButton;
        this.textButton = textButton;
        return this;
    }

    public Titulo tituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
        return this;
    }

    public Titulo modalContent(Component modalContent) {
        this.modalContent = modalContent;
        return this;
    }

    public Titulo modalSize(double width, double height) {
        this.modalWidth = width;
        this.modalHeight = height;
        return this;
    }

    public Titulo onSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit != null ? onSubmit : () -> {};
        return this;
    }

    public Titulo onClear(Runnable onClear) {
        this.onClear = onClear != null ? onClear : () -> {};
        return this;
    }

    public Titulo onClick(Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

    public Component render() {
        Row row = new Row()
            .gap(0)
            .children(
                new Column()
                    .gap(8)
                    .children(
                        new Text(titulo).fontSize(24).modifier(new Modifier().bold()),
                        new Text(subTitulo)
                            .fontSize(13)
                            .modifier(new Modifier().textColor(Color.GRAY))
                    ),
                new Spacer()
            );

        if (hasText(iconButton) && hasText(textButton)) {
            row
                .children(
                    new IconButton(iconButton, textButton)
                        .color(BricksTheme.current().colorScheme().onPrimary())
                        .modifier(new Modifier().height(35).padding(0))
                        .onClick(onClick != null ? onClick : this::abrirModal)
                );
        }

        return row;
    }

    private void abrirModal() {
        new FormularioModal(app, titulo)
            .size(modalWidth, modalHeight)
            .createTitle(hasText(tituloModal) ? tituloModal : textButton)
            .content(modalContent)
            .onSubmit(onSubmit)
            .onClear(onClear)
            .show();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
