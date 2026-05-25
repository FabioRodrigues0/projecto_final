package models.Veiculo;

import java.util.Date;
import models.Identidades;

public class Veiculos extends Identidades {
    public int ano;
    public String matricula;
    public String foto;

    public Veiculos() {
    }

    public Veiculos(int id, String nome, int ano, String matricula, String foto, Date data) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.matricula = matricula;
        this.foto = foto;
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

    public int getAno() {
        return this.ano;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public String getFoto() {
        return this.foto;
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

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
