package models.Veiculo;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoVeiculo;

public class DocumentosVeiculo extends Documentos {
    public int veiculoId;
    public TipoDocumentoVeiculo tipo;
    public LocalDate dataValidade;
    public String seguradora;
    public String cobertura;
    public double valor;

    public DocumentosVeiculo() {
        super();
    }

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

    public DocumentosVeiculo(
                             int id, int veiculoId, String titulo, TipoDocumentoVeiculo tipo, LocalDate dataValidade, double valor, String notas) {
        super(id, titulo, notas);
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.valor = valor;
    }

    public DocumentosVeiculo(
                             int id, int veiculoId, String titulo, TipoDocumentoVeiculo tipo, LocalDate dataValidade, double valor) {
        super(id, titulo);
        this.veiculoId = veiculoId;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.valor = valor;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getVeiculoId() {
        return this.veiculoId;
    }

    public void setVeiculoId(int veiculoId) {
        this.veiculoId = veiculoId;
    }

    @Override
    public String getTitulo() {
        return this.titulo;
    }

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String getNotas() {
        return this.notas;
    }

    @Override
    public void setNotas(String notas) {
        this.notas = notas;
    }

    public TipoDocumentoVeiculo getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDocumentoVeiculo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataValidade() {
        return this.dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getSeguradora() {
        return this.seguradora;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public String getCobertura() {
        return this.cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
