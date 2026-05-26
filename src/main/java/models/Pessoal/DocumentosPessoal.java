package models.Pessoal;

import java.time.LocalDate;
import models.Documentos;
import models.TipoDocumentoPessoal;

public class DocumentosPessoal extends Documentos {
    public TipoDocumentoPessoal tipo;
    public LocalDate dataEmissao;
    public LocalDate dataValidade;

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
}
