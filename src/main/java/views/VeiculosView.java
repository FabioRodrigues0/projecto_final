package views;

import components.FormularioModal;
import components.NotificacoesApp;
import components.Titulo;
import components.VeiculosCard;
import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.FilePicker;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.ItemsColumn;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.components.When;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksPaths;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import models.Veiculo.Veiculos;
import viewModels.VeiculosViewModel;

/**
 * Representa VeiculosView na aplicação.
 */
public class VeiculosView extends BricksScene {

    private final VeiculosViewModel vm = new VeiculosViewModel();
    private final BricksApplication app;

    /**
     * Cria uma nova instância.
     *
     * @param app valor usado pela operação
     */
    public VeiculosView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarVeiculos();
    }

    /**
     * Constrói o componente visual.
     *
     * @return resultado da operação
     */
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
                    .emptyState(
                        new Card()
                            .elevation(2)
                            .background(BricksTheme.current().colorScheme().surface())
                            .children(new Text("Sem veiculos"))
                    )
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
                            }, fotoIconButton(veiculo)
                        ).render()
                    )
            );
    }

    /**
     * Executa a operação abrirVeiculoModal.
     *
     * @param veiculo valor usado pela operação
     */
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

    /**
     * Executa a operação veiculoForm.
     *
     * @param veiculo valor usado pela operação
     * @return resultado da operação
     */
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
                        fotoPicker(veiculo),
                        new When(temFoto(veiculo))
                            .children(
                                new IconButton("fas-trash-alt")
                                    .ghost()
                                    .color(Color.RED)
                                    .onClick(() -> {
                                        vm.removerFotoVeiculo.set(true);
                                    })
                            )
                    ),
                new TextField().multiline().label("Notas").bindTo(vm.notasVeiculo)
            );
    }

    /**
     * Executa a operação fotoLabel.
     *
     * @param veiculo valor usado pela operação
     * @return resultado da operação
     */
    private String fotoLabel(Veiculos veiculo) {
        if (!temFoto(veiculo)) {
            return "Sem foto";
        }
        return "Foto: " + Path.of(veiculo.getFoto()).getFileName().toString();
    }

    /**
     * Executa a operação temFoto.
     *
     * @param veiculo valor usado pela operação
     * @return resultado da operação
     */
    private boolean temFoto(Veiculos veiculo) {
        return veiculo != null && veiculo.getFoto() != null && !veiculo.getFoto().isBlank();
    }

    /**
     * Executa a operação fotoIconButton.
     *
     * @param veiculo valor usado pela operação
     * @return resultado da operação
     */
    private Component fotoIconButton(Veiculos veiculo) {
        return new IconButton("fas-camera")
            .tooltip(temFoto(veiculo) ? "Alterar foto" : "Adicionar foto")
            .color(BricksTheme.current().colorScheme().onSurfaceVariant())
            .modifier(
                new Modifier()
                    .background(BricksTheme.current().colorScheme().surfaceVariant())
                    .borderRadius(8)
                    .width(42)
                    .height(40)
            )
            .onClick(() -> guardarFotoVeiculo(veiculo));
    }

    /**
     * Executa a operação guardarFotoVeiculo.
     *
     * @param veiculo valor usado pela operação
     */
    private void guardarFotoVeiculo(Veiculos veiculo) {
        File selected = escolherFoto();
        if (selected == null) {
            return;
        }

        String path = vm.fotoPath(veiculo.getId(), selected.getName());
        Path destino = BricksPaths.resolveUserData(path);
        try {
            Files.createDirectories(destino.getParent());
            Files.copy(selected.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Alert.error("Erro", "Não foi possível guardar a foto.");
            return;
        }

        vm.fotoVeiculo.set(path);
        vm.removerFotoVeiculo.set(false);
        vm.update(veiculo.getId());
        vm.fotoVeiculo.set("");
        vm.carregarVeiculos();
        NotificacoesApp.atualizado(app, vm);
    }

    /**
     * Executa a operação escolherFoto.
     *
     * @return resultado da operação
     */
    private File escolherFoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Foto do veículo");
        chooser
            .getExtensionFilters()
            .add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        return chooser.showOpenDialog(app.getStage());
    }

    /**
     * Executa a operação fotoPicker.
     *
     * @param veiculo valor usado pela operação
     * @return resultado da operação
     */
    private Component fotoPicker(Veiculos veiculo) {
        FilePicker picker = new FilePicker()
            .label(temFoto(veiculo) ? "Alterar foto" : "Adicionar foto")
            .title("Foto do veículo")
            .filter("Imagens", "*.png", "*.jpg", "*.jpeg");

        return picker.pathToUserData().saveTo(file -> {
            String path = veiculo == null ? vm.fotoPath(file.getName()) : vm
                .fotoPath(veiculo.getId(), file.getName());
            vm.fotoVeiculo.set(path);
            vm.removerFotoVeiculo.set(false);
            return path;
        });
    }

    /**
     * Executa a operação preencherVeiculo.
     *
     * @param veiculo valor usado pela operação
     */
    private void preencherVeiculo(Veiculos veiculo) {
        vm.marcaVeiculo.set(veiculo.getNome());
        vm.modeloVeiculo.set("");
        vm.anoVeiculo.set(veiculo.getAno());
        vm.matriculaVeiculo.set(veiculo.getMatricula());
        vm.fotoVeiculo.set("");
        vm.removerFotoVeiculo.set(false);
        vm.notasVeiculo.set("");
    }

    /**
     * Executa a operação limparVeiculo.
     */
    private void limparVeiculo() {
        vm.marcaVeiculo.set("");
        vm.modeloVeiculo.set("");
        vm.anoVeiculo.set(null);
        vm.matriculaVeiculo.set("");
        vm.fotoVeiculo.set("");
        vm.removerFotoVeiculo.set(false);
        vm.notasVeiculo.set("");
    }
}
