package viewModels;

import fabiorodrigues.bricks.core.BricksPaths;
import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import models.Veiculo.Veiculos;

/**
 * Representa VeiculosViewModel na aplicação.
 */
public class VeiculosViewModel extends BricksViewModel implements IViewModel<Veiculos> {

    public final StateList<Veiculos> listVeiculos = stateList(List.of());
    public final State<String> marcaVeiculo = state("");
    public final State<String> modeloVeiculo = state("");
    public final State<Integer> anoVeiculo = state(null);
    public final State<String> matriculaVeiculo = state("");
    public final State<String> fotoVeiculo = state("");
    public final State<Boolean> removerFotoVeiculo = state(false);
    public final State<String> notasVeiculo = state("");

    /**
     * Executa a operação nomeRecurso.
     *
     * @return resultado da operação
     */
    @Override
    public String nomeRecurso() {
        return "Veículo";
    }

    /**
     * Carrega a lista de veículos.
     */
    public void carregarVeiculos() {
        listVeiculos.clear();
        listVeiculos.addAll(ver());
    }

    /**
     * Obtém os registos existentes.
     *
     * @return resultado da operação
     */
    @Override
    public List<Veiculos> ver() {
        return DB
            .query()
            .select("id", "nome", "ano", "matricula", "foto", "data")
            .from("veiculos")
            .execute(Veiculos.class);
    }

    /**
     * Cria um novo registo.
     */
    @Override
    public void novo() {
        int id = DB
            .query()
            .insertInto("veiculos")
            .value("nome", marcaVeiculo.get() + " " + modeloVeiculo.get())
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .value("ano", anoVeiculo.get())
            .value("matricula", matriculaVeiculo.get())
            .value("foto", fotoVeiculo.get())
            .execute();
    }

    /**
     * Atualiza um registo existente.
     *
     * @param id valor usado pela operação
     */
    @Override
    public void update(int id) {
        String fotoAtual = removerFotoVeiculo.get() ? fotoDoVeiculo(id) : "";

        DB
            .query()
            .update("veiculos")
            .when(
                !(marcaVeiculo.get() + " " + modeloVeiculo.get()).trim().isEmpty(),
                q -> q.value("nome", (marcaVeiculo.get() + " " + modeloVeiculo.get()).trim())
            )
            .when(anoVeiculo.get() != null, q -> q.value("ano", anoVeiculo.get()))
            .when(
                !matriculaVeiculo.get().isEmpty(),
                q -> q.value("matricula", matriculaVeiculo.get())
            )
            .when(!fotoVeiculo.get().isEmpty(), q -> q.value("foto", fotoVeiculo.get()))
            .when(removerFotoVeiculo.get(), q -> q.value("foto", ""))
            .where("id", WhereOperator.EQ, id)
            .execute();

        if (removerFotoVeiculo.get()) {
            apagarFotos(id, fotoAtual);
            removerFotoVeiculo.set(false);
        }
    }

    /**
     * Remove um registo existente.
     *
     * @param id valor usado pela operação
     */
    @Override
    public void apagar(int id) {
        String foto = fotoDoVeiculo(id);
        DB.query().deleteFrom("veiculos").where("id", WhereOperator.EQ, id).execute();

        apagarFotos(id, foto);
    }

    /**
     * Executa a operação apagarFotos.
     *
     * @param id   valor usado pela operação
     * @param foto valor usado pela operação
     */
    private void apagarFotos(int id, String foto) {
        if (foto != null && !foto.isBlank()) {
            try {
                Files.deleteIfExists(BricksPaths.resolveUserData(foto));
            } catch (IOException e) {
                System.err.println("[VeiculosViewModel] Erro ao apagar foto: " + e.getMessage());
            }
        }

        Path pasta = BricksPaths.resolveUserData("veiculos/" + id);
        if (Files.exists(pasta)) {
            try (Stream<Path> stream = Files.walk(pasta)) {
                for (Path path : stream.sorted(Comparator.reverseOrder()).toList()) {
                    Files.deleteIfExists(path);
                }
            } catch (IOException e) {
                System.err
                    .println("[VeiculosViewModel] Erro ao apagar pasta foto: " + e.getMessage());
            }
        }
    }

    /**
     * Executa a operação fotoDoVeiculo.
     *
     * @param id valor usado pela operação
     * @return resultado da operação
     */
    private String fotoDoVeiculo(int id) {
        List<Veiculos> veiculos = DB
            .query()
            .select("id", "nome", "ano", "matricula", "foto", "data")
            .from("veiculos")
            .where("id", WhereOperator.EQ, id)
            .execute(Veiculos.class);

        return veiculos.isEmpty() ? "" : veiculos.get(0).getFoto();
    }

    /**
     * Executa a operação fotoPath.
     *
     * @param id       valor usado pela operação
     * @param fileName valor usado pela operação
     * @return resultado da operação
     */
    public String fotoPath(int id, String fileName) {
        return "veiculos/" + id + "/" + safeFileName(fileName);
    }

    /**
     * Executa a operação fotoPath.
     *
     * @param fileName valor usado pela operação
     * @return resultado da operação
     */
    public String fotoPath(String fileName) {
        return "veiculos/" + safeFileName(fileName);
    }

    /**
     * Executa a operação safeFileName.
     *
     * @param fileName valor usado pela operação
     * @return resultado da operação
     */
    private String safeFileName(String fileName) {
        return Path.of(fileName).getFileName().toString().replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}
