package models.Pessoal;

import java.time.LocalDateTime;
import models.Identidades;

/**
 * Representa Pessoas na aplicação.
 */
public class Pessoas extends Identidades {
    /**
     * Cria uma nova instância.
     */
    public Pessoas() {
    }

    /**
     * Cria uma nova instância.
     *
     * @param id   valor usado pela operação
     * @param nome valor usado pela operação
     * @param data valor usado pela operação
     */
    public Pessoas(int id, String nome, LocalDateTime data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
    }

    /**
     * Executa a operação getId.
     *
     * @return resultado da operação
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Executa a operação getNome.
     *
     * @return resultado da operação
     */
    @Override
    public String getNome() {
        return this.nome;
    }

    /**
     * Executa a operação getData.
     *
     * @return resultado da operação
     */
    @Override
    public LocalDateTime getData() {
        return this.data;
    }

    /**
     * Executa a operação setId.
     *
     * @param id valor usado pela operação
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Executa a operação setNome.
     *
     * @param nome valor usado pela operação
     */
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Executa a operação setData.
     *
     * @param data valor usado pela operação
     */
    @Override
    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
