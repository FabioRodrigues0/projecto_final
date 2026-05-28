package models.Pessoal;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoPessoal;

public class DocumentosPessoal extends Documentos {
    public int pessoaId;
    public TipoDocumentoPessoal tipo;
    public LocalDate dataEmissao;
    public LocalDate dataValidade;

    public DocumentosPessoal() {
        super();
    }

    public DocumentosPessoal(int id, String titulo, int pessoaId, TipoDocumentoPessoal tipo, LocalDate dataEmissao, LocalDate dataValidade) {
        super(id, titulo);
        this.pessoaId = pessoaId;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
    }

    public DocumentosPessoal(int id, int pessoaId, String titulo, TipoDocumentoPessoal tipo, LocalDate dataEmissao, LocalDate dataValidade, String notas) {
        super(id, titulo, notas);
        this.pessoaId = pessoaId;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
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

    public TipoDocumentoPessoal getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoDocumentoPessoal tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataEmissao() {
        return this.dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataValidade() {
        return this.dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getPessoaId() {
        return this.pessoaId;
    }

    public void setPessoaId(int id) {
        this.pessoaId = id;
    }
}
