package models.calendario;

import java.time.LocalDate;

public class ItemCalendario {
    private String titulo;
    private String categoria; // "Pessoal", "Veículo", "Subscrição"
    private LocalDate data;

    public ItemCalendario(String titulo, String categoria, LocalDate data) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getData() {
        return data;
    }
}
