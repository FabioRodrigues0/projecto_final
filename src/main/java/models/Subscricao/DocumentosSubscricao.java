package models.Subscricao;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoSubscricao;
import models.TipoPagamento;

public class DocumentosSubscricao extends Documentos {
    public TipoDocumentoSubscricao tipo;
    public TipoPagamento modeloPagamento;
    public double custo;
    public String plano;
    public LocalDate dataRenovacao;
    public boolean ativa;

    public DocumentosSubscricao() {
    }

    public DocumentosSubscricao(
                                String titulo, TipoDocumentoSubscricao tipo, TipoPagamento modeloPagamento, double custo, String plano, LocalDate dataRenovacao, boolean ativa, String notas) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.modeloPagamento = modeloPagamento;
        this.custo = custo;
        this.plano = plano;
        this.dataRenovacao = dataRenovacao;
        this.ativa = ativa;
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

    public TipoDocumentoSubscricao getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDocumentoSubscricao tipo) {
        this.tipo = tipo;
    }

    public TipoPagamento getModeloPagamento() {
        return this.modeloPagamento;
    }

    public void setModeloPagamento(TipoPagamento modeloPagamento) {
        this.modeloPagamento = modeloPagamento;
    }

    public double getCusto() {
        return this.custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public String getPlano() {
        return this.plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public LocalDate getDataRenovacao() {
        return this.dataRenovacao;
    }

    public void setDataRenovacao(LocalDate dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public boolean isAtiva() {
        return this.ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
