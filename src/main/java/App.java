import components.Sidebar;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.*;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;
import views.DashboardView;

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
        setTheme(BricksTheme.material().colorScheme().primaryContainer(Color.web("#f8fafc"))
                .secondary(Color.web("#958DA5")).and());
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    // Cria o schema da base de dados no arranque
    private final Effect initDB = effect(() -> DatabaseSchema.create());

    // ── root() ────────────────────────────────────────────────────────────────

    @Override
    public Component root() {
        return new Row().gap(0).children(new Sidebar(this, isSidebarOpen.get()).render(),
                new Column().gap(0).modifier(new Modifier().fillMaxWidth())
                        .children(new Row().gap(0)
                                .modifier(new Modifier().fillMaxWidth().border(Color.DARKGRAY, 2))
                                .children(new IconButton("fas-check").onClick(() -> {
                                    isSidebarOpen.set(!isSidebarOpen.get());
                                }), new Spacer()),
                                currentScene() != null
                                        ? currentScene().render()
                                        : new Text("A carregar...")));
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args
     *            argumentos da linha de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
