package models.Subscricao;

import java.time.LocalDateTime;
import models.Identidades;

/**
 * Representa Subscricoes na aplicação.
 */
public class Subscricoes extends Identidades {
    public String logo;

    /**
     * Cria uma nova instância.
     */
    public Subscricoes() {
    }

    /**
     * Cria uma nova instância.
     *
     * @param id   valor usado pela operação
     * @param nome valor usado pela operação
     * @param data valor usado pela operação
     * @param logo valor usado pela operação
     */
    public Subscricoes(int id, String nome, LocalDateTime data, String logo) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.logo = logo;
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
     * Executa a operação getLogo.
     *
     * @return resultado da operação
     */
    public String getLogo() {
        return this.logo;
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

    /**
     * Executa a operação setLogo.
     *
     * @param logo valor usado pela operação
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
}
