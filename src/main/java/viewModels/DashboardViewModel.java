package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.util.List;
import models.Expiracoes;

public class DashboardViewModel extends BricksViewModel {
  public final State<Integer> qntDocumentos = state(0);
  public final State<Integer> qntVeiculos = state(0);
  public final State<Integer> qntSubscricoes = state(0);
  public final State<Integer> qntAlertas = state(0);
  public final StateList<Expiracoes> listExpiracoes = stateList(List.of());

  public void carregarExpiracoes() {
    List<Expiracoes> lista =
        DB.query()
            .select("a.id", "d.nome", "a.genero", "a.idade", "a.media", "nd.nota")
            .from("alunos a")
            .execute(Expiracoes.class);

    listExpiracoes.clear();
    listExpiracoes.addAll(lista);
  }
}
