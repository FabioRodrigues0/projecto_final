package theme;

import fabiorodrigues.bricks.style.BricksTheme;
import javafx.scene.paint.Color;

/**
 * Representa AppThemes na aplicação.
 */
public final class AppThemes {
    public static final String LIGHT = "Light";
    public static final String DARK = "Dark";

    /**
     * Cria uma nova instância.
     */
    private AppThemes() {
    }

    /**
     * Executa a operação from.
     *
     * @param tema valor usado pela operação
     * @return resultado da operação
     */
    public static BricksTheme from(String tema) {
        return DARK.equals(tema) ? dark() : light();
    }

    /**
     * Executa a operação light.
     *
     * @return resultado da operação
     */
    public static BricksTheme light() {
        return BricksTheme
            .material()
            .colorScheme()
            .primary(Color.web("#2563eb"))
            .onPrimary(Color.web("#ffffff"))
            .primaryContainer(Color.web("#dbeafe"))
            .onPrimaryContainer(Color.web("#1e40af"))
            .secondary(Color.web("#64748b"))
            .onSecondary(Color.web("#ffffff"))
            .secondaryContainer(Color.web("#f1f5f9"))
            .onSecondaryContainer(Color.web("#334155"))
            .tertiary(Color.web("#7c3aed"))
            .onTertiary(Color.web("#ffffff"))
            .tertiaryContainer(Color.web("#ede9fe"))
            .onTertiaryContainer(Color.web("#5b21b6"))
            .error(Color.web("#dc2626"))
            .onError(Color.web("#ffffff"))
            .errorContainer(Color.web("#fee2e2"))
            .onErrorContainer(Color.web("#991b1b"))
            .background(Color.web("#f8fafc"))
            .onBackground(Color.web("#0f172a"))
            .surface(Color.web("#ffffff"))
            .onSurface(Color.web("#0f172a"))
            .surfaceVariant(Color.web("#f1f5f9"))
            .onSurfaceVariant(Color.web("#64748b"))
            .surfaceContainer(Color.web("#f8fafc"))
            .surfaceContainerHigh(Color.web("#f1f5f9"))
            .surfaceContainerHighest(Color.web("#e2e8f0"))
            .outline(Color.web("#cbd5e1"))
            .outlineVariant(Color.web("#e2e8f0"))
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
            .and();
    }

    /**
     * Executa a operação dark.
     *
     * @return resultado da operação
     */
    public static BricksTheme dark() {
        return BricksTheme
            .dark()
            .colorScheme()
            .primary(Color.web("#c4a7ff"))
            .onPrimary(Color.web("#251047"))
            .primaryContainer(Color.web("#4f2b83"))
            .onPrimaryContainer(Color.web("#efe5ff"))
            .secondary(Color.web("#b9c3d5"))
            .onSecondary(Color.web("#253140"))
            .secondaryContainer(Color.web("#334155"))
            .onSecondaryContainer(Color.web("#f1f5f9"))
            .tertiary(Color.web("#d8b4fe"))
            .onTertiary(Color.web("#3b0764"))
            .tertiaryContainer(Color.web("#581c87"))
            .onTertiaryContainer(Color.web("#f3e8ff"))
            .error(Color.web("#f87171"))
            .onError(Color.web("#450a0a"))
            .errorContainer(Color.web("#7f1d1d"))
            .onErrorContainer(Color.web("#fee2e2"))
            .background(Color.web("#111016"))
            .onBackground(Color.web("#e7e2ec"))
            .surface(Color.web("#1b1821"))
            .onSurface(Color.web("#e7e2ec"))
            .surfaceVariant(Color.web("#24202b"))
            .onSurfaceVariant(Color.web("#c9c1d3"))
            .surfaceContainer(Color.web("#15131b"))
            .surfaceContainerHigh(Color.web("#211d28"))
            .surfaceContainerHighest(Color.web("#2b2633"))
            .outline(Color.web("#6f6679"))
            .outlineVariant(Color.web("#3f3948"))
            .inverseSurface(Color.web("#e7e2ec"))
            .inverseOnSurface(Color.web("#1b1821"))
            .inversePrimary(Color.web("#6d42b8"))
            .and()
            .typography()
            .fontFamily("Inter, Segoe UI, Roboto, Arial")
            .and()
            .shapes()
            .extraSmall(6)
            .small(8)
            .medium(12)
            .large(16)
            .and();
    }
}
