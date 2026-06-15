package components;

import fabiorodrigues.bricks.components.Badge;
import fabiorodrigues.bricks.components.BadgeVariant;
import fabiorodrigues.bricks.core.Component;

/**
 * Representa BadgeEstado na aplicação.
 */
public class BadgeEstado {

    private final String texto;
    private final BadgeVariant variant;

    /**
     * Cria uma nova instância.
     *
     * @param dias valor usado pela operação
     */
    public BadgeEstado(long dias) {
        if (dias < 0) {
            this.texto = "Expirado";
            this.variant = BadgeVariant.DANGER;
            return;
        }

        if (dias <= 30) {
            this.texto = "Expira em breve";
            this.variant = BadgeVariant.WARNING;
            return;
        }

        this.texto = "Valido";
        this.variant = BadgeVariant.SUCCESS;
    }

    /**
     * Cria uma nova instância.
     *
     * @param texto   valor usado pela operação
     * @param variant valor usado pela operação
     */
    public BadgeEstado(String texto, BadgeVariant variant) {
        this.texto = texto;
        this.variant = variant;
    }

    /**
     * Executa a operação texto.
     *
     * @return resultado da operação
     */
    public String texto() {
        return this.texto;
    }

    /**
     * Executa a operação variant.
     *
     * @return resultado da operação
     */
    public BadgeVariant variant() {
        return this.variant;
    }

    /**
     * Constrói o componente visual.
     *
     * @return resultado da operação
     */
    public Component render() {
        return new Badge(this.texto).soft().variant(this.variant);
    }
}
