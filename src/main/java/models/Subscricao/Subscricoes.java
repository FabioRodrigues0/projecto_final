package models.Subscricao;

import java.time.LocalDateTime;
import models.Identidades;

public class Subscricoes extends Identidades {
    public String logo;

    public Subscricoes() {
    }

    public Subscricoes(int id, String nome, LocalDateTime data, String logo) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.logo = logo;
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

    public String getLogo() {
        return this.logo;
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

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
