package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;

public class DashboardViewModel extends BricksViewModel {
    public final State<Integer> qntDocumentos = state(0);
    public final State<Integer> qntVeiculos = state(0);
    public final State<Integer> qntSubscricoes = state(0);
    public final State<Integer> qntAlertas = state(0);
}
