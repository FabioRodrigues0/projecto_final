package components;

import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
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

    public VeiculosCard(BricksApplication app, Veiculos veiculo) {
        this.app = app;
        this.id = veiculo.getId();
        this.nome = veiculo.getNome();
        this.ano = veiculo.getAno();
        this.matricula = veiculo.getMatricula();
        this.foto = veiculo.getFoto();
    }

    public Component render() {
        boolean hasFoto = this.foto != null && !this.foto.isBlank();
        String imagePath = hasFoto ? this.foto : "/car.png";

        return new Card()
            .elevation(2)
            .cornerRadius(10)
            .coverImage(imagePath, 160)
            .width(380)
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
                                new IconButton("fas-pen").ghost().modifier(new Modifier()),
                                new IconButton("fas-trash-alt").ghost().color(Color.RED)
                            ),
                        new Button("Ver Documentos")
                            .modifier(
                                new Modifier()
                                    .background(Color.WHITE)
                                    .textColor(Color.BLACK)
                                    .border(Color.GRAY, 0.5)
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
}
