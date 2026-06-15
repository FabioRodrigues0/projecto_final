package models.Veiculo;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoVeiculo;

/**
 * Representa DocumentosVeiculo na aplicação.
 */
public class DocumentosVeiculo extends Documentos {
    public int veiculoId;
    public TipoDocumentoVeiculo tipo;
    public LocalDate dataValidade;
    public String seguradora;
    public String cobertura;
    public double valor;

    /**
     * Cria uma nova instância.
     */
    public DocumentosVeiculo() {
        super();
    }

    /**
     * Cria uma nova instância.
     *
     * @param id           valor usado pela operação
     * @param veiculoId    valor usado pela operação
     * @param titulo       valor usado pela operação
     * @param tipo         valor usado pela operação
     * @param dataValidade valor usado pela operação
     * @param seguradora   valor usado pela operação
     * @param cobertura    valor usado pela operação
     * @param valor        valor usado pela operação
     * @param notas        valor usado pela operação
     */
    public DocumentosVeiculo(
                             int id, int veiculoId, String titulo, TipoDocumentoVeiculo tipo, LocalDate dataValidade, String seguradora, String cobertura, double valor, String notas) {
        super(id, titulo, notas);
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.seguradora = seguradora;
        this.cobertura = cobertura;
        this.valor = valor;
    }

    /**
     * Cria uma nova instância.
     *
     * @param id           valor usado pela operação
     * @param veiculoId    valor usado pela operação
     * @param titulo       valor usado pela operação
     * @param tipo         valor usado pela operação
     * @param dataValidade valor usado pela operação
     * @param seguradora   valor usado pela operação
     * @param cobertura    valor usado pela operação
     * @param valor        valor usado pela operação
     */
    public DocumentosVeiculo(
                             int id, int veiculoId, String titulo, TipoDocumentoVeiculo tipo, LocalDate dataValidade, String seguradora, String cobertura, double valor) {
        super(id, titulo);
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.seguradora = seguradora;
        this.cobertura = cobertura;
        this.valor = valor;
    }

    /**
     * Cria uma nova instância.
     *
     * @param id           valor usado pela operação
     * @param veiculoId    valor usado pela operação
     * @param titulo       valor usado pela operação
     * @param tipo         valor usado pela operação
     * @param dataValidade valor usado pela operação
     * @param valor        valor usado pela operação
     * @param notas        valor usado pela operação
     */
    public DocumentosVeiculo(
                             int id, int veiculoId, String titulo, TipoDocumentoVeiculo tipo, LocalDate dataValidade, double valor, String notas) {
        super(id, titulo, notas);
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.valor = valor;
    }

    /**
     * Cria uma nova instância.
     *
     * @param id           valor usado pela operação
     * @param veiculoId    valor usado pela operação
     * @param titulo       valor usado pela operação
     * @param tipo         valor usado pela operação
     * @param dataValidade valor usado pela operação
     * @param valor        valor usado pela operação
     */
    public DocumentosVeiculo(
                             int id, int veiculoId, String titulo, TipoDocumentoVeiculo tipo, LocalDate dataValidade, double valor) {
        super(id, titulo);
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.valor = valor;
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
     * Executa a operação setId.
     *
     * @param id valor usado pela operação
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Executa a operação getVeiculoId.
     *
     * @return resultado da operação
     */
    public int getVeiculoId() {
        return this.veiculoId;
    }

    /**
     * Executa a operação setVeiculoId.
     *
     * @param veiculoId valor usado pela operação
     */
    public void setVeiculoId(int veiculoId) {
        this.veiculoId = veiculoId;
    }

    /**
     * Executa a operação getTitulo.
     *
     * @return resultado da operação
     */
    @Override
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Executa a operação setTitulo.
     *
     * @param titulo valor usado pela operação
     */
    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Executa a operação getNotas.
     *
     * @return resultado da operação
     */
    @Override
    public String getNotas() {
        return this.notas;
    }

    /**
     * Executa a operação setNotas.
     *
     * @param notas valor usado pela operação
     */
    @Override
    public void setNotas(String notas) {
        this.notas = notas;
    }

    /**
     * Executa a operação getTipo.
     *
     * @return resultado da operação
     */
    public TipoDocumentoVeiculo getTipo() {
        return this.tipo;
    }

    /**
     * Executa a operação setTipo.
     *
     * @param tipo valor usado pela operação
     */
    public void setTipo(TipoDocumentoVeiculo tipo) {
        this.tipo = tipo;
    }

    /**
     * Executa a operação getDataValidade.
     *
     * @return resultado da operação
     */
    public LocalDate getDataValidade() {
        return this.dataValidade;
    }

    /**
     * Executa a operação setDataValidade.
     *
     * @param dataValidade valor usado pela operação
     */
    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    /**
     * Executa a operação getSeguradora.
     *
     * @return resultado da operação
     */
    public String getSeguradora() {
        return this.seguradora;
    }

    /**
     * Executa a operação setSeguradora.
     *
     * @param seguradora valor usado pela operação
     */
    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    /**
     * Executa a operação getCobertura.
     *
     * @return resultado da operação
     */
    public String getCobertura() {
        return this.cobertura;
    }

    /**
     * Executa a operação setCobertura.
     *
     * @param cobertura valor usado pela operação
     */
    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    /**
     * Executa a operação getValor.
     *
     * @return resultado da operação
     */
    public double getValor() {
        return this.valor;
    }

    /**
     * Executa a operação setValor.
     *
     * @param valor valor usado pela operação
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
}
