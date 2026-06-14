package components;

import fabiorodrigues.bricks.components.Box;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Icon;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.Veiculo.Veiculos;
import views.VeiculosDocumentosView;

public class VeiculosCard {

    private final int id;
    private final String nome;
    private final int ano;
    private final String matricula;
    private final String foto;
    private final BricksApplication app;
    private final Runnable onEditar;
    private final Runnable onApagar;
    private final Component fotoPicker;

    public VeiculosCard(BricksApplication app, Veiculos veiculo, Runnable onEditar, Runnable onApagar, Component fotoPicker) {
        this.app = app;
        this.id = veiculo.getId();
        this.nome = veiculo.getNome();
        this.ano = veiculo.getAno();
        this.matricula = veiculo.getMatricula();
        this.foto = veiculo.getFoto();
        this.onEditar = onEditar;
        this.onApagar = onApagar;
        this.fotoPicker = fotoPicker;
    }

    public Component render() {
        return new Card()
            .elevation(2)
            .cornerRadius(10)
            .background(BricksTheme.current().colorScheme().surface())
            .coverImageUserData(resolveImagePath(), 160)
            .coverPlaceholder(
                new Box()
                    .modifier(
                        new Modifier()
                            .background(BricksTheme.current().colorScheme().surfaceVariant())
                            .alignment(Pos.CENTER)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                    .children(new Icon("fas-car-side").size(48))
            )
            .width(250)
            .padding(15)
            .children(
                new Column()
                    .gap(8)
                    .children(
                        new Row()
                            .gap(0)
                            .modifier(new Modifier().alignment(Pos.CENTER))
                            .children(
                                new Column()
                                    .gap(2)
                                    .children(
                                        new Text(this.nome).bold().fontSize(18),
                                        new Row()
                                            .gap(2)
                                            .children(
                                                new Text(String.valueOf(this.ano)),
                                                new Text("."),
                                                new Text(this.matricula)
                                            )
                                    ),
                                new Spacer(),
                                this.fotoPicker,
                                new IconButton("fas-pen")
                                    .ghost()
                                    .color(BricksTheme.current().colorScheme().onSurface())
                                    .modifier(new Modifier())
                                    .onClick(this.onEditar),
                                new IconButton("fas-trash-alt")
                                    .ghost()
                                    .color(Color.RED)
                                    .onClick(this.onApagar)
                            ),
                        new Button("Ver Documentos")
                            .modifier(
                                new Modifier()
                                    .background(BricksTheme.current().colorScheme().surface())
                                    .textColor(BricksTheme.current().colorScheme().onSurface())
                                    .border(BricksTheme.current().colorScheme().outline(), 0.5)
                                    .fillMaxWidth()
                            )
                            .onClick(
                                () -> this.app
                                    .navigateTo(
                                        new VeiculosDocumentosView(
                                            this.app, this.id, this.nome, this.ano, this.matricula, this.foto
                                        )
                                    )
                            )
                    )
            );
    }

    private String resolveImagePath() {
        if (this.foto == null || this.foto.isBlank()) {
            return "missing-cover-image";
        }

        return this.foto;
    }
}
