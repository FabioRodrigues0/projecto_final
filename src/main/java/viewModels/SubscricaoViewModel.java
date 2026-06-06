package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.QueryResult;
import fabiorodrigues.bricks.data.WhereOperator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import models.Subscricao.DocumentosSubscricao;
import models.Subscricao.Subscricoes;
import models.TipoDocumentoSubscricao;
import models.TipoPagamento;

public class SubscricaoViewModel extends BricksViewModel implements IViewModel<Subscricoes>, IViewModelDocumentos<DocumentosSubscricao> {

    public final StateList<DocumentosSubscricao> listDocumentosSubscricao = stateList(List.of());
    public final StateList<Subscricoes> listSubscricoes = stateList(List.of());
    public final State<Double> gastoMensal = state(0.0);
    public final State<Double> gastoAnual = state(0.0);
    public final State<Integer> subscricoesAtivas = state(0);

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

    public void carregarSubscricoes() {
        listSubscricoes.clear();
        listSubscricoes.addAll(ver());
    }

    public void carregarDocumentos() {
        listDocumentosSubscricao.clear();
        listDocumentosSubscricao.addAll(verDocumentos());
        calcularGastos();
    }

    public void limparCampos() {
        nomeSubscricao.set("");
        servicoSubscricao.set("");
        categoriaSubscricao.set(TipoDocumentoSubscricao.NONE);
        custoSubscricao.set(null);
        planoSubscricao.set("");
        dataRenovacaoSubscricao.set(null);
        estadoSubscricao.set(true);
    }

    private void calcularGastos() {
        double mensal = 0.0;
        double anual = 0.0;

        for (DocumentosSubscricao documento : listDocumentosSubscricao.get()) {
            if (!documento.isAtiva()) {
                continue;
            }

            if (documento.getModeloPagamento() == TipoPagamento.ANUAL) {
                mensal += documento.getCusto() / 12;
                anual += documento.getCusto();
            } else {
                mensal += documento.getCusto();
                anual += documento.getCusto() * 12;
            }
        }

        Set<Integer> ativas = listDocumentosSubscricao
            .get()
            .stream()
            .filter(DocumentosSubscricao::isAtiva)
            .map(DocumentosSubscricao::getSubscricaoId)
            .collect(Collectors.toSet());

        gastoMensal.set(mensal);
        gastoAnual.set(anual);
        subscricoesAtivas.set(ativas.size());
    }

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
        QueryResult result = DB
            .query()
            .insertInto("subscricoes")
            .value("nome", servicoSubscricao.get())
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .executeResult();

        novoDocumento(result.getGeneratedIdAsInt());
    }

    @Override
    public void update(int id) {
        DB
            .query()
            .update("subscricoes")
            .value("nome", servicoSubscricao.get())
            .where("id", WhereOperator.EQ, id)
            .execute();
    }

    @Override
    public void apagar(int id) {
        DB.query().deleteFrom("subscricoes").where("id", WhereOperator.EQ, id).execute();
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
                "ativa"
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
            .value("modelo_pagamento", TipoPagamento.MENSAL)
            .value("custo", custoSubscricao.get())
            .value("data_renovacao", DateValues.atStartOfDay(dataRenovacaoSubscricao.get()))
            .value("ativa", estadoSubscricao.get())
            .when(planoSubscricao.get() != "", q -> q.value("plano", planoSubscricao.get()))
            .execute();
    }

    @Override
    public void updateDocumento(int id) {
        DB
            .query()
            .update("documentos_subscricao")
            .value("titulo", servicoSubscricao.get())
            .value("tipo", categoriaSubscricao.get())
            .value("custo", custoSubscricao.get())
            .value("plano", planoSubscricao.get())
            .value("data_renovacao", DateValues.atStartOfDay(dataRenovacaoSubscricao.get()))
            .value("ativa", estadoSubscricao.get())
            .where("id", WhereOperator.EQ, id)
            .execute();
    }

    @Override
    public void apagarDocumento(int id) {
        DB.query().deleteFrom("documentos_subscricao").where("id", WhereOperator.EQ, id).execute();
    }
}
