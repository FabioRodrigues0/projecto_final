package models.Pessoal;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoPessoal;

/**
 * Representa DocumentosPessoal na aplicação.
 */
public class DocumentosPessoal extends Documentos {
    public int pessoaId;
    public TipoDocumentoPessoal tipo;
    public LocalDate dataEmissao;
    public LocalDate dataValidade;

    /**
     * Cria uma nova instância.
     */
    public DocumentosPessoal() {
        super();
    }

    /**
     * Cria uma nova instância.
     *
     * @param id           valor usado pela operação
     * @param titulo       valor usado pela operação
     * @param pessoaId     valor usado pela operação
     * @param tipo         valor usado pela operação
     * @param dataEmissao  valor usado pela operação
     * @param dataValidade valor usado pela operação
     */
    public DocumentosPessoal(int id, String titulo, int pessoaId, TipoDocumentoPessoal tipo, LocalDate dataEmissao, LocalDate dataValidade) {
        super(id, titulo);
        this.pessoaId = pessoaId;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
    }

    /**
     * Cria uma nova instância.
     *
     * @param id           valor usado pela operação
     * @param pessoaId     valor usado pela operação
     * @param titulo       valor usado pela operação
     * @param tipo         valor usado pela operação
     * @param dataEmissao  valor usado pela operação
     * @param dataValidade valor usado pela operação
     * @param notas        valor usado pela operação
     */
    public DocumentosPessoal(int id, int pessoaId, String titulo, TipoDocumentoPessoal tipo, LocalDate dataEmissao, LocalDate dataValidade, String notas) {
        super(id, titulo, notas);
        this.pessoaId = pessoaId;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
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
    public TipoDocumentoPessoal getTipo() {
        return this.tipo;
    }

    /**
     * Executa a operação setTipo.
     *
     * @param tipo valor usado pela operação
     */
    public void setTipo(TipoDocumentoPessoal tipo) {
        this.tipo = tipo;
    }

    /**
     * Executa a operação getDataEmissao.
     *
     * @return resultado da operação
     */
    public LocalDate getDataEmissao() {
        return this.dataEmissao;
    }

    /**
     * Executa a operação setDataEmissao.
     *
     * @param dataEmissao valor usado pela operação
     */
    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
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
     * Executa a operação getPessoaId.
     *
     * @return resultado da operação
     */
    public int getPessoaId() {
        return this.pessoaId;
    }

    /**
     * Executa a operação setPessoaId.
     *
     * @param id valor usado pela operação
     */
    public void setPessoaId(int id) {
        this.pessoaId = id;
    }
}
