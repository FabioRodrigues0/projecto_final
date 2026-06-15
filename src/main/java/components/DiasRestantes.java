package components;

import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.style.Modifier;
import javafx.scene.paint.Color;

/**
 * Representa DiasRestantes na aplicação.
 */
public class DiasRestantes {

    private final long dias;

    /**
     * Cria uma nova instância.
     *
     * @param dias valor usado pela operação
     */
    public DiasRestantes(long dias) {
        this.dias = dias;
    }

    /**
     * Executa a operação texto.
     *
     * @param dias valor usado pela operação
     * @return resultado da operação
     */
    public static String texto(long dias) {
        if (dias < 0) {
            return Math.abs(dias) + " dias em atraso";
        }

        return dias + " dias restantes";
    }

    /**
     * Executa a operação cor.
     *
     * @param dias valor usado pela operação
     * @return resultado da operação
     */
    public static Color cor(long dias) {
        if (dias < 0) {
            return Color.rgb(193, 0, 7);
        }

        if (dias <= 30) {
            return Color.rgb(187, 77, 0);
        }

        return Color.rgb(20, 120, 55);
    }

    /**
     * Constrói o componente visual.
     *
     * @return resultado da operação
     */
    public Component render() {
        return new Text(texto(this.dias)).modifier(new Modifier().bold().textColor(cor(this.dias)));
    }
}
