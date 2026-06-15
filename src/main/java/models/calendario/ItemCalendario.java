package models.calendario;

import java.time.LocalDate;
import models.TipoItemCalendario;

/**
 * Representa ItemCalendario na aplicação.
 */
public class ItemCalendario {
    private String titulo;
    private TipoItemCalendario categoria; // "Pessoal", "Veículo", "Subscrição"
    private LocalDate data;

    /**
     * Cria uma nova instância.
     *
     * @param titulo    valor usado pela operação
     * @param categoria valor usado pela operação
     * @param data      valor usado pela operação
     */
    public ItemCalendario(String titulo, TipoItemCalendario categoria, LocalDate data) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.data = data;
    }

    /**
     * Executa a operação getTitulo.
     *
     * @return resultado da operação
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Executa a operação getCategoria.
     *
     * @return resultado da operação
     */
    public TipoItemCalendario getCategoria() {
        return categoria;
    }

    /**
     * Executa a operação getData.
     *
     * @return resultado da operação
     */
    public LocalDate getData() {
        return data;
    }
}
