package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.util.List;
import models.Veiculo.DocumentosVeiculo;
import models.Veiculo.Veiculos;

public class VeiculosViewModel extends BricksViewModel implements IViewModel<Veiculos>, IViewModelDocumentos<DocumentosVeiculo> {

    public final StateList<Veiculos> listVeiculos = stateList(List.of());
    public final StateList<DocumentosVeiculo> listDocumentos = stateList(List.of());
    public final State<String> marcaVeiculo = state("");
    public final State<String> modeloVeiculo = state("");
    public final State<String> anoVeiculo = state("");
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
    public void novo(Veiculos identidade) {
        DB
            .query()
            .insertInto("veiculos")
            .value("nome", identidade.getNome())
            .value("data", identidade.getData())
            .value("ano", identidade.getAno())
            .value("matricula", identidade.getMatricula())
            .when(identidade.getFoto() != null, q -> q.value("foto", identidade.getFoto()))
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
