package models.calendario;

import java.time.LocalDate;
import models.TipoItemCalendario;

public class ItemCalendario {
    private String titulo;
    private TipoItemCalendario categoria; // "Pessoal", "Veículo", "Subscrição"
    private LocalDate data;

    public ItemCalendario(String titulo, TipoItemCalendario categoria, LocalDate data) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public TipoItemCalendario getCategoria() {
        return categoria;
    }

    public LocalDate getData() {
        return data;
    }
}
