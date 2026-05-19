import fabiorodrigues.bricks.components.*;
import fabiorodrigues.bricks.core.*;
import fabiorodrigues.bricks.style.Modifier;

/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e
 * base de dados SQLite integrada.
 */
public class App extends BricksApplication {

    // ── Estado ────────────────────────────────────────────────────────────────

    private final State<String> titulo = state("Olá, Bricks!");

    {
        setTitle("App");
        // setTheme(BricksTheme.dark()); // descomenta para dark mode
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    // Cria o schema da base de dados no arranque
    private final Effect initDB = effect(() -> DatabaseSchema.create());

    // ── root() ────────────────────────────────────────────────────────────────

    @Override
    public Component root() {
        return new Column().padding(20).gap(12).modifier(new Modifier().fillMaxWidth()).children(
                new Text(titulo.get()).modifier(new Modifier().fontSize(24).bold()),
                new Button("Clica-me!").onClick(() -> titulo.set("Botão clicado!")));
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
