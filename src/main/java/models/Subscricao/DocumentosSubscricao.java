package models.Subscricao;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoSubscricao;
import models.TipoPagamento;

/**
 * Representa DocumentosSubscricao na aplicação.
 */
public class DocumentosSubscricao extends Documentos {
    public int subscricaoId;
    public TipoDocumentoSubscricao tipo;
    public TipoPagamento modeloPagamento;
    public double custo;
    public String plano;
    public LocalDate dataRenovacao;
    public boolean ativa;

    /**
     * Cria uma nova instância.
     */
    public DocumentosSubscricao() {
        super();
    }

    /**
     * Cria uma nova instância.
     *
     * @param id              valor usado pela operação
     * @param subscricaoId    valor usado pela operação
     * @param titulo          valor usado pela operação
     * @param tipo            valor usado pela operação
     * @param modeloPagamento valor usado pela operação
     * @param custo           valor usado pela operação
     * @param plano           valor usado pela operação
     * @param dataRenovacao   valor usado pela operação
     * @param ativa           valor usado pela operação
     * @param notas           valor usado pela operação
     */
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

    /**
     * Cria uma nova instância.
     *
     * @param id              valor usado pela operação
     * @param subscricaoId    valor usado pela operação
     * @param titulo          valor usado pela operação
     * @param tipo            valor usado pela operação
     * @param modeloPagamento valor usado pela operação
     * @param custo           valor usado pela operação
     * @param plano           valor usado pela operação
     * @param dataRenovacao   valor usado pela operação
     * @param ativa           valor usado pela operação
     */
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

    /**
     * Cria uma nova instância.
     *
     * @param id              valor usado pela operação
     * @param subscricaoId    valor usado pela operação
     * @param titulo          valor usado pela operação
     * @param tipo            valor usado pela operação
     * @param modeloPagamento valor usado pela operação
     * @param custo           valor usado pela operação
     * @param dataRenovacao   valor usado pela operação
     * @param ativa           valor usado pela operação
     */
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
     * Executa a operação getSubscricaoId.
     *
     * @return resultado da operação
     */
    public int getSubscricaoId() {
        return this.subscricaoId;
    }

    /**
     * Executa a operação setSubscricaoId.
     *
     * @param subscricaoId valor usado pela operação
     */
    public void setSubscricaoId(int subscricaoId) {
        this.subscricaoId = subscricaoId;
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
    public TipoDocumentoSubscricao getTipo() {
        return this.tipo;
    }

    /**
     * Executa a operação setTipo.
     *
     * @param tipo valor usado pela operação
     */
    public void setTipo(TipoDocumentoSubscricao tipo) {
        this.tipo = tipo;
    }

    /**
     * Executa a operação getModeloPagamento.
     *
     * @return resultado da operação
     */
    public TipoPagamento getModeloPagamento() {
        return this.modeloPagamento;
    }

    /**
     * Executa a operação setModeloPagamento.
     *
     * @param modeloPagamento valor usado pela operação
     */
    public void setModeloPagamento(TipoPagamento modeloPagamento) {
        this.modeloPagamento = modeloPagamento;
    }

    /**
     * Executa a operação getCusto.
     *
     * @return resultado da operação
     */
    public double getCusto() {
        return this.custo;
    }

    /**
     * Executa a operação setCusto.
     *
     * @param custo valor usado pela operação
     */
    public void setCusto(double custo) {
        this.custo = custo;
    }

    /**
     * Executa a operação getPlano.
     *
     * @return resultado da operação
     */
    public String getPlano() {
        return this.plano;
    }

    /**
     * Executa a operação setPlano.
     *
     * @param plano valor usado pela operação
     */
    public void setPlano(String plano) {
        this.plano = plano;
    }

    /**
     * Executa a operação getDataRenovacao.
     *
     * @return resultado da operação
     */
    public LocalDate getDataRenovacao() {
        return this.dataRenovacao;
    }

    /**
     * Executa a operação setDataRenovacao.
     *
     * @param dataRenovacao valor usado pela operação
     */
    public void setDataRenovacao(LocalDate dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    /**
     * Executa a operação isAtiva.
     *
     * @return resultado da operação
     */
    public boolean isAtiva() {
        return this.ativa;
    }

    /**
     * Executa a operação setAtiva.
     *
     * @param ativa valor usado pela operação
     */
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
