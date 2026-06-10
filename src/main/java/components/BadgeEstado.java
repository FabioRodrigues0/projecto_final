package components;

import fabiorodrigues.bricks.components.Badge;
import fabiorodrigues.bricks.components.BadgeVariant;
import fabiorodrigues.bricks.core.Component;

public class BadgeEstado {

    private final String texto;
    private final BadgeVariant variant;

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

    public BadgeEstado(String texto, BadgeVariant variant) {
        this.texto = texto;
        this.variant = variant;
    }

    public String texto() {
        return this.texto;
    }

    public BadgeVariant variant() {
        return this.variant;
    }

    public Component render() {
        return new Badge(this.texto).soft().variant(this.variant);
    }
}
