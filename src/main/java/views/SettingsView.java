package views;

import components.Titulo;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Checkbox;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import theme.AppThemes;
import viewModels.SettingsViewModel;

/**
 * Representa SettingsView na aplicação.
 */
public class SettingsView extends BricksScene {

    private final SettingsViewModel vm = new SettingsViewModel();
    private final BricksApplication app;

    /**
     * Cria uma nova instância.
     *
     * @param app valor usado pela operação
     */
    public SettingsView(BricksApplication app) {
        super(app);
        use(this.vm);
        this.app = app;
        this.vm.carregarSettings();
        aplicarTema();
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
                new Titulo(this.app, "Settings")
                    .subtitulo("Preferências e configuração da aplicação")
                    .render(),
                new Card()
                    .padding(15)
                    .elevation(2)
                    .background(BricksTheme.current().colorScheme().surface())
                    .modifier(new Modifier().fillMaxWidth())
                    .children(
                        new Checkbox("Notificações")
                            .bindTo(vm.notificacoesAtivas)
                            .onChange(value -> vm.guardar()),
                        new Dropdown<>(
                            List.of(SettingsViewModel.TEMA_LIGHT, SettingsViewModel.TEMA_DARK)
                        ).label("Tema").bindTo(vm.tema).onChange(value -> {
                            vm.guardar();
                            aplicarTema();
                        })

                    )
            );
    }

    /**
     * Executa a operação aplicarTema.
     */
    private void aplicarTema() {
        app.setTheme(AppThemes.from(vm.tema.get()));
    }
}
