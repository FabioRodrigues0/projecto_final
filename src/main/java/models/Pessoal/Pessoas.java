package models.Pessoal;

import java.time.LocalDateTime;
import models.Identidades;

public class Pessoas extends Identidades {

    public Pessoas() {
    }

    public Pessoas(int id, String nome, LocalDateTime data) {
        this.id = id;
        this.nome = nome;
        this.data = data;
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
}
