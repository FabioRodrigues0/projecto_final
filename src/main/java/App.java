import fabiorodrigues.bricks.components.*;
import fabiorodrigues.bricks.core.*;
import fabiorodrigues.bricks.style.BricksTheme;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;
import views.DashboardView;
import views.DocumentosView;
import views.SubscricaoView;
import views.VeiculosView;

/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e base de dados SQLite
 * integrada.
 */
public class App extends BricksApplication {

    // ── Estado ────────────────────────────────────────────────────────────────

    {
        setTitle("App");
        setInitialScene(new DashboardView(this));
        // setTheme(BricksTheme.dark()); // descomenta para dark mode
        setTheme(
            BricksTheme
                .material()
                .colorScheme()
                // Primary — azul (botões "Novo Documento", "Nova Subscrição", "Ativar Notificações")
                .primary(Color.web("#2563eb"))
                .onPrimary(Color.web("#ffffff"))
                .primaryContainer(Color.web("#dbeafe"))
                .onPrimaryContainer(Color.web("#1e40af"))
                // Secondary — slate neutro
                .secondary(Color.web("#64748b"))
                .onSecondary(Color.web("#ffffff"))
                .secondaryContainer(Color.web("#f1f5f9"))
                .onSecondaryContainer(Color.web("#334155"))
                // Tertiary — roxo do logo LifeBinder+
                .tertiary(Color.web("#7c3aed"))
                .onTertiary(Color.web("#ffffff"))
                .tertiaryContainer(Color.web("#ede9fe"))
                .onTertiaryContainer(Color.web("#5b21b6"))
                // Error — vermelho dos badges "Expirado"
                .error(Color.web("#dc2626"))
                .onError(Color.web("#ffffff"))
                .errorContainer(Color.web("#fee2e2"))
                .onErrorContainer(Color.web("#991b1b"))
                // Background — slate-50 (fundo geral da app)
                .background(Color.web("#f8fafc"))
                .onBackground(Color.web("#0f172a"))
                // Surface — branco (cards, sidebar, navbar)
                .surface(Color.web("#ffffff"))
                .onSurface(Color.web("#0f172a"))
                .surfaceVariant(Color.web("#f1f5f9"))
                .onSurfaceVariant(Color.web("#64748b"))
                .surfaceContainer(Color.web("#f8fafc"))
                .surfaceContainerHigh(Color.web("#f1f5f9"))
                .surfaceContainerHighest(Color.web("#e2e8f0"))
                // Outlines — cinzas suaves
                .outline(Color.web("#cbd5e1"))
                .outlineVariant(Color.web("#e2e8f0"))
                // Inverse
                .inverseSurface(Color.web("#0f172a"))
                .inverseOnSurface(Color.web("#f8fafc"))
                .inversePrimary(Color.web("#60a5fa"))
                .and()
                .typography()
                .fontFamily("Inter, Segoe UI, Roboto, Arial")
                .and()
                .shapes()
                .extraSmall(6)
                .small(8)
                .medium(12)
                .large(16)
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
                    .modifier(
                        new Modifier()
                            .background(BricksTheme.current().colorScheme().surface())
                            .border(Color.rgb(225, 231, 239), 1)
                    )
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
            .navbar(
                new Navbar()
                    .modifier(
                        new Modifier()
                            .background(BricksTheme.current().colorScheme().surface())
                            .border(Color.rgb(225, 231, 239), 1)
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
}
