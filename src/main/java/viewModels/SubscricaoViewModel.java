package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.data.DB;
import java.util.List;
import models.Subscricao.DocumentosSubscricao;
import models.Subscricao.Subscricoes;

public class SubscricaoViewModel extends BricksViewModel implements IViewModel<Subscricoes>, IViewModelDocumentos<DocumentosSubscricao> {

    @Override
    public List<Subscricoes> ver() {
        return DB
            .query()
            .select("id", "nome", "data", "logo")
            .from("subscricoes")
            .execute(Subscricoes.class);
    }

    @Override
    public void novo(Subscricoes identidade) {
        DB
            .query()
            .insertInto("subscricoes")
            .value("nome", identidade.getNome())
            .value("data", identidade.getData())
            .when(identidade.getLogo() != null, q -> q.value("logo", identidade.getLogo()))
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
    public void novoDocumento(DocumentosSubscricao doc) {
        DB
            .query()
            .insertInto("documentos_subscricao")
            .value("subscricao_id", doc.getSubscricaoId())
            .value("titulo", doc.getTitulo())
            .value("tipo", doc.getTipo())
            .value("modelo_pagamento", doc.getModeloPagamento())
            .value("custo", doc.getCusto())
            .value("data_renovacao", doc.getDataRenovacao())
            .value("ativa", doc.isAtiva())
            .when(doc.getPlano() != null, q -> q.value("plano", doc.getPlano()))
            .when(doc.getNotas() != null, q -> q.value("notas", doc.getNotas()))
            .execute();
    }

    @Override
    public void updateDocumento(int id) {
    }

    @Override
    public void apagarDocumento(int id) {
    }
}
