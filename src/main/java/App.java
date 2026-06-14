import fabiorodrigues.bricks.components.AppLayout;
import fabiorodrigues.bricks.components.Navbar;
import fabiorodrigues.bricks.components.Sidebar;
import fabiorodrigues.bricks.components.SidebarItem;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksPaths;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.Effect;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import fabiorodrigues.bricks.data.config.SQLiteConfig;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import java.nio.file.Path;
import java.util.List;
import javafx.scene.paint.Color;
import models.Settings;
import theme.AppThemes;
import views.CalendarioView;
import views.DashboardView;
import views.DocumentosView;
import views.SettingsView;
import views.SubscricaoView;
import views.VeiculosView;

/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e base de dados SQLite
 * integrada.
 */
public class App extends BricksApplication {

    // ── Estado ────────────────────────────────────────────────────────────────

    {
        setPathUserData(
            Path.of(System.getProperty("user.home"), "Documents", "life-binders").toString()
        );
        DB.configure(new SQLiteConfig(BricksPaths.resolveUserData("data/database.db").toString()));
        setTitle("LifeBinder+");
        setAppIcon("/images/lifebinder.png");
        setTrayIcon("/images/lifebinder.png");
        setTrayTooltip("LifeBinder+");
        setTrayMenuLabels("Abrir LifeBinder+", "Sair");
        minimizeToTray();

        DatabaseSchema.create();

        setInitialScene(new DashboardView(this));
        setSize(1280, 720);
        setTheme(AppThemes.light());
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    // Cria o schema da base de dados no arranque
    private final Effect initTheme = effect(() -> {
        //DatabaseSchema.create();
        aplicarTemaGuardado();
    });

    // ── root() ────────────────────────────────────────────────────────────────

    @Override
    public Component root() {
        return new AppLayout()
            .sidebar(
                new Sidebar()
                    .modifier(
                        new Modifier()
                            .background(BricksTheme.current().colorScheme().surface())
                            .border(BricksTheme.current().colorScheme().outlineVariant(), 1)
                    )
                    .logo(logoPath())
                    .item(
                        new SidebarItem(
                            "fas-chart-line", "Dashboard", () -> navigateTo(new DashboardView(this))
                        )
                    )
                    .item(
                        new SidebarItem(
                            "fas-file", "Documentos", () -> navigateTo(new DocumentosView(this))
                        )
                    )
                    .item(
                        new SidebarItem(
                            "fas-car", "Veiculos", () -> navigateTo(new VeiculosView(this))
                        )
                    )
                    .item(
                        new SidebarItem(
                            "fas-star", "Subscricoes", () -> navigateTo(new SubscricaoView(this))
                        )
                    )
                    .item(
                        new SidebarItem(
                            "fas-calendar", "Calendário", () -> navigateTo(new CalendarioView(this))
                        )
                    )
                    .bottomItem(
                        new SidebarItem(
                            "fas-cog", "Settings", () -> navigateTo(new SettingsView(this))
                        )
                    )
            )
            .navbar(
                new Navbar()
                    .modifier(
                        new Modifier()
                            .background(BricksTheme.current().colorScheme().surface())
                            .border(BricksTheme.current().colorScheme().outlineVariant(), 1)
                    )
            )
            .content(currentScene() != null ? currentScene().render() : new Text("A carregar..."));
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void aplicarTemaGuardado() {
        List<Settings> settings = DB
            .query()
            .select("tema")
            .from("settings")
            .where("id", WhereOperator.EQ, 1)
            .execute(Settings.class);

        if (!settings.isEmpty()) {
            setTheme(AppThemes.from(settings.get(0).getTema()));
        }
    }

    private String logoPath() {
        if (isDarkTheme()) {
            return "/logo_lifebinder_horizontal.png";
        }

        return "/logo_lifebinder_horizontal.png";
    }

    private boolean isDarkTheme() {
        return BricksTheme.current().colorScheme().background().equals(Color.web("#111016"));
    }
}
