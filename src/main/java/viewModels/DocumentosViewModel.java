package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import models.Pessoal.DocumentosPessoal;
import models.Pessoal.Pessoas;
import models.TipoDocumentoPessoal;

public class DocumentosViewModel extends BricksViewModel implements IViewModel<Pessoas>, IViewModelDocumentos<DocumentosPessoal> {
    public final StateList<DocumentosPessoal> listDocumentos = stateList(List.of());
    public final StateList<Pessoas> listPessoas = stateList(List.of());
    // pessoa
    public final State<String> nomePessoa = state("");
    // documento
    public final State<String> tituloDocumento = state("");
    public final State<TipoDocumentoPessoal> categoriaDocumento = state(TipoDocumentoPessoal.NONE);
    public final State<LocalDate> dataEmissaoDocumento = state(null);
    public final State<LocalDate> dataValidadeDocumento = state(null);
    public final State<String> notasDocumento = state("");

    @Override
    public String nomeRecurso() {
        return "Pessoa";
    }

    @Override
    public String nomeDocumento() {
        return "Documento";
    }

    public void carregarPessoas() {
        listPessoas.clear();
        listPessoas.addAll(ver());
    }

    public void carregarDocumentos() {
        listDocumentos.clear();
        listDocumentos.addAll(verDocumentos());
    }

    @Override
    public List<Pessoas> ver() {
        return DB.query().select("id", "nome", "data").from("pessoas").execute(Pessoas.class);
    }

    @Override
    public void novo() {
        DB
            .query()
            .insertInto("pessoas")
            .value("nome", nomePessoa.get())
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .execute();
    }

    @Override
    public void update(int id) {
        DB
            .query()
            .update("pessoas")
            .value("nome", nomePessoa.get())
            .where("id", WhereOperator.EQ, id)
            .execute();
    }

    @Override
    public void apagar(int id) {
        DB.query().deleteFrom("pessoas").where("id", WhereOperator.EQ, id).execute();
        List<DocumentosPessoal> list = DB
            .query()
            .select("*")
            .from("documentos_pessoal")
            .where("pessoa_id", WhereOperator.EQ, id)
            .execute(DocumentosPessoal.class);
        if (list.size() != 0) {
            list.forEach(l -> apagarDocumento(l.getId()));
        }
    }

    @Override
    public List<DocumentosPessoal> verDocumentos() {
        return DB
            .query()
            .select("id", "pessoa_id", "titulo", "tipo", "data_emissao", "data_validade", "notas")
            .from("documentos_pessoal")
            .execute(DocumentosPessoal.class);
    }

    @Override
    public void novoDocumento(int pessoaId) {
        DB
            .query()
            .insertInto("documentos_pessoal")
            .value("pessoa_id", pessoaId)
            .value("titulo", tituloDocumento.get())
            .value("tipo", categoriaDocumento.get())
            .value("data_emissao", DateValues.atStartOfDay(dataEmissaoDocumento.get()))
            .value("data_validade", DateValues.atStartOfDay(dataValidadeDocumento.get()))
            .when(notasDocumento.get() != "", q -> q.value("notas", notasDocumento.get()))
            .execute();
    }

    @Override
    public void updateDocumento(int id) {
        DB
            .query()
            .update("documentos_pessoal")
            .value("titulo", tituloDocumento.get())
            .value("tipo", categoriaDocumento.get())
            .value("data_emissao", DateValues.atStartOfDay(dataEmissaoDocumento.get()))
            .value("data_validade", DateValues.atStartOfDay(dataValidadeDocumento.get()))
            .value("notas", notasDocumento.get())
            .where("id", WhereOperator.EQ, id)
            .execute();

    }

    @Override
    public void apagarDocumento(int id) {
        DB.query().deleteFrom("documentos_pessoal").where("id", WhereOperator.EQ, id).execute();
    }
}
