import fabiorodrigues.bricks.components.*;
import fabiorodrigues.bricks.core.*;
import fabiorodrigues.bricks.style.BricksTheme;
import javafx.scene.paint.Color;
import views.DashboardView;
import views.DocumentosView;
import views.SubscricaoView;
import views.VeiculosView;


/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e
 * base de dados SQLite integrada.
 */
public class App extends BricksApplication {

    // ── Estado ────────────────────────────────────────────────────────────────
    private final State<Boolean> isSidebarOpen = state(true);

    {
        setTitle("App");
        setInitialScene(new DashboardView(this));
        // setTheme(BricksTheme.dark()); // descomenta para dark mode
        setTheme(
            BricksTheme
                .material()
                .colorScheme()
                .primaryContainer(Color.web("#f8fafc"))
                .secondary(Color.web("#958DA5"))
                .and()
        );
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    // Cria o schema da base de dados no arranque
    private final Effect initDB = effect(() -> DatabaseSchema.create());

    // ── root() ────────────────────────────────────────────────────────────────

    @Override
    public Component root() {
        return new AppLayout()
            .sidebar(
                new Sidebar()
                    .logo("/logo_faculdade.png")
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
            )
            .navbar(new Navbar())
            .content(currentScene() != null ? currentScene().render() : new Text("A carregar..."));
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args
     *             argumentos da linha de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
