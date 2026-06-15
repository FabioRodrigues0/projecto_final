package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import java.time.LocalDate;
import java.util.List;
import models.TipoDocumentoVeiculo;
import models.Veiculo.DocumentosVeiculo;

/**
 * Representa VeiculosDocumentosViewModel na aplicação.
 */
public class VeiculosDocumentosViewModel extends BricksViewModel implements IViewModelDocumentos<DocumentosVeiculo> {

    public final StateList<DocumentosVeiculo> listDocumentos = stateList(List.of());
    public final State<TipoDocumentoVeiculo> tipoDocumentoVeiculo = state(
        TipoDocumentoVeiculo.NONE
    );
    public final State<String> tituloDocumentoVeiculo = state("");
    public final State<LocalDate> dataValidadeDocumentoVeiculo = state(null);
    public final State<String> seguradoraDocumentoVeiculo = state("");
    public final State<String> coberturaDocumentoVeiculo = state("");
    public final State<Double> valorDocumentoVeiculo = state(0.00);
    public final State<String> notasDocumentoVeiculo = state("");

    public final State<Boolean> eSeguro = state(false);

    /**
     * Executa a operação nomeDocumento.
     *
     * @return resultado da operação
     */
    @Override
    public String nomeDocumento() {
        return "Documento";
    }

    /**
     * Carrega a lista de documentos.
     *
     * @param veiculoId valor usado pela operação
     */
    public void carregarDocumentos(int veiculoId) {
        listDocumentos.clear();
        listDocumentos
            .addAll(
                verDocumentos().stream().filter(doc -> doc.getVeiculoId() == veiculoId).toList()
            );
    }

    /**
     * Obtém os documentos existentes.
     *
     * @return resultado da operação
     */
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

    /**
     * Cria um novo documento.
     *
     * @param veiculoId valor usado pela operação
     */
    @Override
    public void novoDocumento(int veiculoId) {
        DB
            .query()
            .insertInto("documentos_veiculo")
            .value("veiculo_id", veiculoId)
            .value("tipo", tipoDocumentoVeiculo.get())
            .value("titulo", tituloDocumentoVeiculo.get())
            .value("data_validade", DateValues.atStartOfDay(dataValidadeDocumentoVeiculo.get()))
            .value("seguradora", seguradoraDocumentoVeiculo.get())
            .value("cobertura", coberturaDocumentoVeiculo.get())
            .value("valor", valorDocumentoVeiculo.get())
            .value("notas", notasDocumentoVeiculo.get())
            .execute();
    }

    /**
     * Atualiza um documento existente.
     *
     * @param id valor usado pela operação
     */
    @Override
    public void updateDocumento(int id) {
        DB
            .query()
            .update("documentos_veiculo")
            .value("tipo", tipoDocumentoVeiculo.get())
            .value("titulo", tituloDocumentoVeiculo.get())
            .value("data_validade", DateValues.atStartOfDay(dataValidadeDocumentoVeiculo.get()))
            .value("seguradora", seguradoraDocumentoVeiculo.get())
            .value("cobertura", coberturaDocumentoVeiculo.get())
            .value("valor", valorDocumentoVeiculo.get())
            .value("notas", notasDocumentoVeiculo.get())
            .where("id", WhereOperator.EQ, id)
            .execute();
    }

    /**
     * Remove um documento existente.
     *
     * @param id valor usado pela operação
     */
    @Override
    public void apagarDocumento(int id) {
        DB.query().deleteFrom("documentos_veiculo").where("id", WhereOperator.EQ, id).execute();
    }
}
