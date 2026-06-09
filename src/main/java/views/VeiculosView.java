package views;

import components.FormularioModal;
import components.NotificacoesApp;
import components.Titulo;
import components.VeiculosCard;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.components.When;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import java.io.File;
import java.nio.file.Path;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import models.Veiculo.Veiculos;
import viewModels.VeiculosViewModel;

public class VeiculosView extends BricksScene {

    private final VeiculosViewModel vm = new VeiculosViewModel();
    private final BricksApplication app;

    public VeiculosView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarVeiculos();
    }

    @Override
    public Component render() {
        return new Column()
            .gap(20)
            .modifier(new Modifier().padding(30, 20).fillMaxHeight())
            .children(
                new Titulo(this.app, "Veiculos")
                    .subtitulo("Seguro, IUC e inspeção de cada veículo")
                    .botao("fas-plus", "Novo Veiculo")
                    .onClick(() -> abrirVeiculoModal(null))
                    .render(),
                new ItemsColumn<Veiculos>()
                    .gap(10)
                    .columns(3)
                    .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
                    .emptyState(new Card().elevation(2).children(new Text("Sem veiculos")))
                    .items(this.vm.listVeiculos)
                    .item(
                        veiculo -> new VeiculosCard(
                            this.app, veiculo, () -> abrirVeiculoModal(veiculo), () -> {
                                if (!Alert
                                    .confirm(
                                        "Confirmar",
                                        "Tem a certeza que pretende apagar este veículo?"
                                    )) {
                                    return;
                                }
                                vm.apagar(veiculo.getId());
                                vm.carregarVeiculos();
                                NotificacoesApp.removido(app, vm);
                            }, new IconButton("fas-camera", "Foto").ghost().onClick(() -> {
                                File selected = escolherFoto();
                                if (selected == null) {
                                    return;
                                }
                                vm.fotoFileVeiculo.set(selected);
                                vm.update(veiculo.getId());
                                vm.carregarVeiculos();
                                NotificacoesApp.atualizado(app, vm);
                            })
                        ).render()
                    )
            );
    }

    private void abrirVeiculoModal(Veiculos veiculo) {
        boolean update = veiculo != null;

        if (update) {
            preencherVeiculo(veiculo);
        } else {
            limparVeiculo();
        }

        new FormularioModal(app, "Veiculos")
            .size(520.0, 480.0)
            .update(update)
            .titles("Novo Veiculo", "Editar Veiculo")
            .content(veiculoForm(veiculo))
            .onClear(this::limparVeiculo)
            .onSubmit(() -> {
                if (update) {
                    vm.update(veiculo.getId());
                    NotificacoesApp.atualizado(app, vm);
                } else {
                    vm.novo();
                    NotificacoesApp.criado(app, vm);
                }
                vm.carregarVeiculos();
            })
            .show();
    }

    private Component veiculoForm(Veiculos veiculo) {
        return new Column()
            .gap(8)
            .children(
                new Row()
                    .gap(5)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new TextField()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Marca")
                            .bindTo(vm.marcaVeiculo),
                        new TextField()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Modelo")
                            .bindTo(vm.modeloVeiculo)
                    ),
                new Row()
                    .gap(5)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new TextField()
                            .number()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Ano")
                            .bindTo(vm.anoVeiculo),
                        new TextField()
                            .modifier(new Modifier().fillMaxWidth())
                            .label("Matricula")
                            .bindTo(vm.matriculaVeiculo)
                    ),
                new Row()
                    .gap(8)
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new Text(fotoLabel(veiculo)),
                        new Spacer(),
                        new IconButton(
                            "fas-camera", temFoto(veiculo) ? "Alterar foto" : "Adicionar foto"
                        ).ghost().onClick(() -> {
                            File selected = escolherFoto();
                            if (selected != null) {
                                vm.fotoFileVeiculo.set(selected);
                            }
                        }),
                        new When(temFoto(veiculo))
                            .children(
                                new IconButton("fas-trash-alt")
                                    .ghost()
                                    .color(Color.RED)
                                    .onClick(() -> {
                                        vm.removerFotoVeiculo.set(true);
                                        vm.fotoFileVeiculo.set(null);
                                    })
                            )
                    ),
                new TextField().multiline().label("Notas").bindTo(vm.notasVeiculo)
            );
    }

    private String fotoLabel(Veiculos veiculo) {
        if (!temFoto(veiculo)) {
            return "Sem foto";
        }
        return "Foto: " + Path.of(veiculo.getFoto()).getFileName().toString();
    }

    private boolean temFoto(Veiculos veiculo) {
        return veiculo != null && veiculo.getFoto() != null && !veiculo.getFoto().isBlank();
    }

    private File escolherFoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Foto do veículo");
        chooser
            .getExtensionFilters()
            .add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg"));
        return chooser.showOpenDialog(null);
    }

    private void preencherVeiculo(Veiculos veiculo) {
        vm.marcaVeiculo.set(veiculo.getNome());
        vm.modeloVeiculo.set("");
        vm.anoVeiculo.set(veiculo.getAno());
        vm.matriculaVeiculo.set(veiculo.getMatricula());
        vm.notasVeiculo.set("");
    }

    private void limparVeiculo() {
        vm.marcaVeiculo.set("");
        vm.modeloVeiculo.set("");
        vm.anoVeiculo.set(null);
        vm.matriculaVeiculo.set("");
        vm.fotoVeiculo.set("");
        vm.fotoFileVeiculo.set(null);
        vm.removerFotoVeiculo.set(false);
        vm.notasVeiculo.set("");
    }
}
