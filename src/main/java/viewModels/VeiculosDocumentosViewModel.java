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
    public void novoDocumento(int veiculoId) {
    }

    @Override
    public void updateDocumento(int id) {
    }

    @Override
    public void apagarDocumento(int id) {
    }
}
