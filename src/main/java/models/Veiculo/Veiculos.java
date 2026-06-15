package models.Veiculo;

import java.time.LocalDateTime;
import models.Identidades;

/**
 * Representa Veiculos na aplicação.
 */
public class Veiculos extends Identidades {
    public int ano;
    public String matricula;
    public String foto;

    /**
     * Cria uma nova instância.
     */
    public Veiculos() {
    }

    /**
     * Cria uma nova instância.
     *
     * @param id        valor usado pela operação
     * @param nome      valor usado pela operação
     * @param ano       valor usado pela operação
     * @param matricula valor usado pela operação
     * @param foto      valor usado pela operação
     * @param data      valor usado pela operação
     */
    public Veiculos(
                    int id, String nome, int ano, String matricula, String foto, LocalDateTime data) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.matricula = matricula;
        this.foto = foto;
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
     * Executa a operação getAno.
     *
     * @return resultado da operação
     */
    public int getAno() {
        return this.ano;
    }

    /**
     * Executa a operação getMatricula.
     *
     * @return resultado da operação
     */
    public String getMatricula() {
        return this.matricula;
    }

    /**
     * Executa a operação getFoto.
     *
     * @return resultado da operação
     */
    public String getFoto() {
        return this.foto;
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
     * Executa a operação setAno.
     *
     * @param ano valor usado pela operação
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Executa a operação setMatricula.
     *
     * @param matricula valor usado pela operação
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Executa a operação setFoto.
     *
     * @param foto valor usado pela operação
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
