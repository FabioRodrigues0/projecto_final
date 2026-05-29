package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.util.List;
import models.Veiculo.DocumentosVeiculo;

public class VeiculosDocumentosViewModel extends BricksViewModel implements IViewModelDocumentos<DocumentosVeiculo> {

    public final StateList<DocumentosVeiculo> listDocumentos = stateList(List.of());

    public void carregarDocumentos(int veiculoId) {
        listDocumentos.clear();
        listDocumentos
            .addAll(
                verDocumentos().stream().filter(doc -> doc.getVeiculoId() == veiculoId).toList()
            );
    }

    @Override
    public List<DocumentosVeiculo> verDocumentos() {
        return DB
            .query()
            .select(
                "id",
                "veiculo_id",
                "titulo",
                "tipo",
                "data_validade",
                "seguradora",
                "cobertura",
                "valor",
                "notas"
            )
            .from("documentos_veiculo")
            .execute(DocumentosVeiculo.class);
    }

    @Override
    public void novoDocumento(DocumentosVeiculo doc) {
        DB
            .query()
            .insertInto("documentos_veiculo")
            .value("veiculo_id", doc.getVeiculoId())
            .value("titulo", doc.getTitulo())
            .value("tipo", doc.getTipo())
            .value("data_validade", DateValues.atStartOfDay(doc.getDataValidade()))
            .value("valor", doc.getValor())
            .when(doc.getSeguradora() != null, q -> q.value("seguradora", doc.getSeguradora()))
            .when(doc.getCobertura() != null, q -> q.value("cobertura", doc.getCobertura()))
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
