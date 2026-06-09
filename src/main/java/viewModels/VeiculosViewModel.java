package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import fabiorodrigues.bricks.data.WhereOperator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import models.Veiculo.Veiculos;

public class VeiculosViewModel extends BricksViewModel implements IViewModel<Veiculos> {

    public final StateList<Veiculos> listVeiculos = stateList(List.of());
    public final State<String> marcaVeiculo = state("");
    public final State<String> modeloVeiculo = state("");
    public final State<Integer> anoVeiculo = state(null);
    public final State<String> matriculaVeiculo = state("");
    public final State<String> fotoVeiculo = state("");
    public final State<File> fotoFileVeiculo = state(null);
    public final State<Boolean> removerFotoVeiculo = state(false);
    public final State<String> notasVeiculo = state("");

    @Override
    public String nomeRecurso() {
        return "Veículo";
    }

    public void carregarVeiculos() {
        listVeiculos.clear();
        listVeiculos.addAll(ver());
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
        int id = DB
            .query()
            .insertInto("veiculos")
            .value("nome", marcaVeiculo.get() + " " + modeloVeiculo.get())
            .value("data", DateValues.timestamp(LocalDateTime.now()))
            .value("ano", anoVeiculo.get())
            .value("matricula", matriculaVeiculo.get())
            .value("foto", fotoVeiculo.get())
            .execute();

        File foto = fotoFileVeiculo.get();
        if (foto != null) {
            try {
                String relPath = "data/veiculos/" + id + "/" + foto.getName();
                Path destino = Path.of(relPath);
                Files.createDirectories(destino.getParent());
                Files.copy(foto.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                DB
                    .query()
                    .update("veiculos")
                    .value("foto", relPath)
                    .where("id", WhereOperator.EQ, id)
                    .execute();
            } catch (IOException e) {
                System.err.println("[VeiculosViewModel] Erro ao guardar foto: " + e.getMessage());
            }
            fotoFileVeiculo.set(null);
        }
    }

    @Override
    public void update(int id) {
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
            Path pasta = Path.of("data/veiculos/" + id);
            if (Files.exists(pasta)) {
                try (Stream<Path> stream = Files.walk(pasta)) {
                    stream
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                } catch (IOException e) {
                    System.err
                        .println(
                            "[VeiculosViewModel] Erro ao apagar pasta foto: " + e.getMessage()
                        );
                }
            }
            removerFotoVeiculo.set(false);
            return;
        }

        File foto = fotoFileVeiculo.get();
        if (foto != null) {
            try {
                String relPath = "data/veiculos/" + id + "/" + foto.getName();
                Path destino = Path.of(relPath);
                Files.createDirectories(destino.getParent());
                Files.copy(foto.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                DB
                    .query()
                    .update("veiculos")
                    .value("foto", relPath)
                    .where("id", WhereOperator.EQ, id)
                    .execute();
            } catch (IOException e) {
                System.err.println("[VeiculosViewModel] Erro ao guardar foto: " + e.getMessage());
            }
            fotoFileVeiculo.set(null);
        }
    }

    @Override
    public void apagar(int id) {
        DB.query().deleteFrom("veiculos").where("id", WhereOperator.EQ, id).execute();

        Path pasta = Path.of("data/veiculos/" + id);
        if (Files.exists(pasta)) {
            try (Stream<Path> stream = Files.walk(pasta)) {
                stream.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            } catch (IOException e) {
                System.err
                    .println("[VeiculosViewModel] Erro ao apagar pasta foto: " + e.getMessage());
            }
        }
    }
}
