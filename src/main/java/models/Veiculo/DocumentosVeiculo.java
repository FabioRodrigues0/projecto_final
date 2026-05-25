package models.Veiculo;

import java.util.Date;
import models.Documentos;
import models.TipoDocumentoVeiculo;

public class DocumentosVeiculo extends Documentos {
    public TipoDocumentoVeiculo tipo;
    public Date dataValidade;
    public String seguradora;
    public String cobertura;
    public double valor;

    public DocumentosVeiculo() {
    }

    public DocumentosVeiculo(String titulo, TipoDocumentoVeiculo tipo, Date dataValidade, String seguradora, String cobertura, double valor, String notas) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.dataValidade = dataValidade;
        this.seguradora = seguradora;
        this.cobertura = cobertura;
        this.valor = valor;
        this.notas = notas;
    }

    @Override
    public String getTitulo() {
        return this.titulo;
    }

    @Override
    public String getNotas() {
        return this.notas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public TipoDocumentoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumentoVeiculo tipo) {
        this.tipo = tipo;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
