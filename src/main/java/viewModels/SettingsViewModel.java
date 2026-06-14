package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import java.time.LocalDateTime;
import java.util.List;
import models.Settings;
import theme.AppThemes;

public class SettingsViewModel extends BricksViewModel {
    private static final int SETTINGS_ID = 1;
    private static final String SETTINGS_NOME = "Preferencias";
    public static final String TEMA_LIGHT = AppThemes.LIGHT;
    public static final String TEMA_DARK = AppThemes.DARK;

    public final State<String> tema = state(TEMA_LIGHT);
    public final State<Boolean> notificacoesAtivas = state(false);

    public void carregarSettings() {
        Settings settings = procurarSettings();

        if (settings == null) {
            criarSettingsPadrao();
            return;
        }

        tema.set(settings.getTema());
        notificacoesAtivas.set(settings.isNotificacoesAtivas());
    }

    public void guardar() {
        garantirSettings();
        DB
            .query()
            .update("settings")
            .value("tema", tema.get())
            .value("notificacoes_ativas", notificacoesAtivas.get())
            .where("id", WhereOperator.EQ, SETTINGS_ID)
            .execute();
    }

    public boolean temaEscuro() {
        return TEMA_DARK.equals(tema.get());
    }

    private void garantirSettings() {
        if (procurarSettings() == null) {
            criarSettingsPadrao();
        }
    }

    private Settings procurarSettings() {
        List<Settings> settings = DB
            .query()
            .select("id", "nome", "data", "tema", "notificacoes_ativas")
            .from("settings")
            .where("id", WhereOperator.EQ, SETTINGS_ID)
            .execute(Settings.class);

        return settings.isEmpty() ? null : settings.get(0);
    }

    private void criarSettingsPadrao() {
        DB
            .query()
            .insertInto("settings")
            .value("id", SETTINGS_ID)
            .value("nome", SETTINGS_NOME)
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .value("tema", tema.get())
            .value("notificacoes_ativas", notificacoesAtivas.get())
            .execute();
    }
}
