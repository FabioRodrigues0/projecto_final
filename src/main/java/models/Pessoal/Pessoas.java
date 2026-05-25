package models.Pessoal;

import java.util.Date;
import models.Identidades;

public class Pessoas extends Identidades {

    public Pessoas() {
    }

    public Pessoas(int id, String nome, Date data) {
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
    public Date getData() {
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
    public void setData(Date data) {
        this.data = data;
    }
}
