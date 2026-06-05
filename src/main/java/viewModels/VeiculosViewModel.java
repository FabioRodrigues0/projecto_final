package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import java.time.LocalDateTime;
import java.util.List;
import models.Veiculo.DocumentosVeiculo;
import models.Veiculo.Veiculos;

public class VeiculosViewModel extends BricksViewModel implements IViewModel<Veiculos>, IViewModelDocumentos<DocumentosVeiculo> {

    public final StateList<Veiculos> listVeiculos = stateList(List.of());
    public final StateList<DocumentosVeiculo> listDocumentos = stateList(List.of());
    public final State<String> marcaVeiculo = state("");
    public final State<String> modeloVeiculo = state("");
    public final State<Integer> anoVeiculo = state(null);
    public final State<String> matriculaVeiculo = state("");
    public final State<String> notasVeiculo = state("");

    public void carregarVeiculos() {
        listVeiculos.clear();
        listVeiculos.addAll(ver());
    }

    public void carregarDocumentos(int id) {

        listDocumentos.clear();
        listDocumentos.addAll(verDocumentos());
    }

    @Override
    public List<Veiculos> ver() {
        return DB
            .query()
            .select("id", "nome", "ano", "matricula", "foto", "data")
            .from("veiculos")
            .execute(Veiculos.class);
    }

    @Override
    public void novo() {
        DB
            .query()
            .insertInto("veiculos")
            .value("nome", marcaVeiculo.get() + " " + modeloVeiculo.get())
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .value("ano", anoVeiculo.get())
            .value("matricula", matriculaVeiculo.get())
            .execute();
    }

    @Override
    public void update(int id) {
        DB
            .query()
            .update("veiculos")
            .value("nome", (marcaVeiculo.get() + " " + modeloVeiculo.get()).trim())
            .value("ano", anoVeiculo.get())
            .value("matricula", matriculaVeiculo.get())
            .where("id", WhereOperator.EQ, id)
            .execute();
    }

    @Override
    public void apagar(int id) {
        DB.query().deleteFrom("veiculos").where("id", WhereOperator.EQ, id).execute();
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
        DB.query().deleteFrom("documentos_veiculo").where("id", WhereOperator.EQ, id).execute();
    }
}
