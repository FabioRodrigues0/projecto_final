package models.Pessoal;

import java.util.Date;
import models.Documentos;
import models.TipoDocumentoPessoal;

public class DocumentosPessoal extends Documentos {
    public TipoDocumentoPessoal tipo;
    public Date dataEmissao;
    public Date dataValidade;

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

    public Date getDataEmissao() {
        return this.dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataValidade() {
        return this.dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
}
