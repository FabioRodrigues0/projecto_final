package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.data.DB;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import models.Subscricao.DocumentosSubscricao;
import models.Subscricao.Subscricoes;
import models.TipoDocumentoSubscricao;

public class SubscricaoViewModel extends BricksViewModel implements IViewModel<Subscricoes>, IViewModelDocumentos<DocumentosSubscricao> {
    // SUBSCRICOES
    public final State<String> nomeSubscricao = state("");
    // DOCUMENTOS
    public final State<String> servicoSubscricao = state("");
    public final State<TipoDocumentoSubscricao> categoriaSubscricao = state(
        TipoDocumentoSubscricao.NONE
    );
    public final State<Double> custoSubscricao = state(null);
    public final State<String> planoSubscricao = state("");
    public final State<LocalDate> dataRenovacaoSubscricao = state(null);
    public final State<Boolean> estadoSubscricao = state(true);

    @Override
    public List<Subscricoes> ver() {
        return DB
            .query()
            .select("id", "nome", "data", "logo")
            .from("subscricoes")
            .execute(Subscricoes.class);
    }

    @Override
    public void novo() {
        DB
            .query()
            .insertInto("subscricoes")
            .value("nome", nomeSubscricao.get())
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .execute();
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void apagar(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'apagar'");
    }

    @Override
    public List<DocumentosSubscricao> verDocumentos() {
        return DB
            .query()
            .select(
                "id",
                "subscricao_id",
                "titulo",
                "tipo",
                "modelo_pagamento",
                "custo",
                "plano",
                "data_renovacao",
                "ativa",
                "notas"
            )
            .from("documentos_subscricao")
            .execute(DocumentosSubscricao.class);
    }

    @Override
    public void novoDocumento(int subscricaoId) {
        DB
            .query()
            .insertInto("documentos_subscricao")
            .value("subscricao_id", subscricaoId)
            .value("titulo", servicoSubscricao.get())
            .value("tipo", categoriaSubscricao.get())
            .value("custo", custoSubscricao.get())
            .value("data_renovacao", DateValues.atStartOfDay(dataRenovacaoSubscricao.get()))
            .value("ativa", estadoSubscricao.get())
            .when(planoSubscricao.get() != "", q -> q.value("plano", planoSubscricao.get()))
            .execute();
    }

    @Override
    public void updateDocumento(int id) {
    }

    @Override
    public void apagarDocumento(int id) {
    }
}
