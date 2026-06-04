package models.Subscricao;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoSubscricao;
import models.TipoPagamento;

public class DocumentosSubscricao extends Documentos {
    public int subscricaoId;
    public TipoDocumentoSubscricao tipo;
    public TipoPagamento modeloPagamento;
    public double custo;
    public String plano;
    public LocalDate dataRenovacao;
    public boolean ativa;

    public DocumentosSubscricao() {
        super();
    }

    public DocumentosSubscricao(
                                int id, int subscricaoId, String titulo, TipoDocumentoSubscricao tipo, TipoPagamento modeloPagamento, double custo, String plano, LocalDate dataRenovacao, boolean ativa, String notas) {
        super(id, titulo, notas);
        this.subscricaoId = subscricaoId;
        this.tipo = tipo;
        this.modeloPagamento = modeloPagamento;
        this.custo = custo;
        this.plano = plano;
        this.dataRenovacao = dataRenovacao;
        this.ativa = ativa;
    }

    public DocumentosSubscricao(
                                int id, int subscricaoId, String titulo, TipoDocumentoSubscricao tipo, TipoPagamento modeloPagamento, double custo, String plano, LocalDate dataRenovacao, boolean ativa) {
        super(id, titulo);
        this.subscricaoId = subscricaoId;
        this.tipo = tipo;
        this.modeloPagamento = modeloPagamento;
        this.custo = custo;
        this.plano = plano;
        this.dataRenovacao = dataRenovacao;
        this.ativa = ativa;
    }

    public DocumentosSubscricao(
                                int id, int subscricaoId, String titulo, TipoDocumentoSubscricao tipo, TipoPagamento modeloPagamento, double custo, LocalDate dataRenovacao, boolean ativa) {
        super(id, titulo);
        this.subscricaoId = subscricaoId;
        this.tipo = tipo;
        this.modeloPagamento = modeloPagamento;
        this.custo = custo;
        this.dataRenovacao = dataRenovacao;
        this.ativa = ativa;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getSubscricaoId() {
        return this.subscricaoId;
    }

    public void setSubscricaoId(int subscricaoId) {
        this.subscricaoId = subscricaoId;
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
