package models;

import java.time.LocalDateTime;

public class Settings extends Identidades {
    public String tema;
    public boolean notificacoesAtivas;

    public Settings() {
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public LocalDateTime getData() {
        return this.data;
    }

    public String getTema() {
        return this.tema;
    }

    public boolean isNotificacoesAtivas() {
        return this.notificacoesAtivas;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setNotificacoesAtivas(boolean notificacoesAtivas) {
        this.notificacoesAtivas = notificacoesAtivas;
    }
}
