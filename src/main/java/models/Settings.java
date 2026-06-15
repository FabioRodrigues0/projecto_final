package models;

import java.time.LocalDateTime;

/**
 * Representa Settings na aplicação.
 */
public class Settings extends Identidades {
    public String tema;
    public boolean notificacoesAtivas;

    /**
     * Cria uma nova instância.
     */
    public Settings() {
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
     * Executa a operação getTema.
     *
     * @return resultado da operação
     */
    public String getTema() {
        return this.tema;
    }

    /**
     * Executa a operação isNotificacoesAtivas.
     *
     * @return resultado da operação
     */
    public boolean isNotificacoesAtivas() {
        return this.notificacoesAtivas;
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
     * Executa a operação setTema.
     *
     * @param tema valor usado pela operação
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * Executa a operação setNotificacoesAtivas.
     *
     * @param notificacoesAtivas valor usado pela operação
     */
    public void setNotificacoesAtivas(boolean notificacoesAtivas) {
        this.notificacoesAtivas = notificacoesAtivas;
    }
}
