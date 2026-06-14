package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import models.Expiracoes;
import models.TipoExpiracoes;

public class DashboardViewModel extends BricksViewModel {
    public final State<Integer> qntDocumentos = state(0);
    public final State<Integer> qntVeiculos = state(0);
    public final State<Integer> qntSubscricoes = state(0);
    public final State<Double> gastoMensal = state(0.0);
    public final State<Integer> qntAlertas = state(0);
    public final StateList<Expiracoes> listExpiracoes = stateList(List.of());


    public void carregarDocumentos() {
        Map<String, Object> qnt = DB
            .query()
            .select("COUNT(DISTINCT pessoas.id) as total")
            .from("pessoas")
            .executeRaw()
            .first();

        qntDocumentos.set((Integer) qnt.get("total"));
    }

    public void carregarVeiculos() {
        Map<String, Object> qnt = DB
            .query()
            .select("COUNT(DISTINCT veiculos.id) as total")
            .from("veiculos")
            .executeRaw()
            .first();

        qntVeiculos.set((Integer) qnt.get("total"));
    }

    public void carregarSubscricoes() {
        Map<String, Object> qnt = DB
            .query()
            .select("COUNT(DISTINCT documentos_subscricao.id) as total")
            .from("documentos_subscricao")
            .executeRaw()
            .first();

        qntSubscricoes.set((Integer) qnt.get("total"));

        Map<String, Object> soma = DB
            .query()
            .select("SUM(documentos_subscricao.custo) as total")
            .from("documentos_subscricao")
            .executeRaw()
            .first();

        Object totalGasto = soma.get("total");
        if (totalGasto != null) {
            gastoMensal.set((Double) totalGasto);
        } else {
            gastoMensal.set(0.0);
        }
    }

    public void carregarExpiracoes() {
        List<Expiracoes> lista = carregarLinhasExpiracoes()
            .stream()
            .map(this::toExpiracaoCalculada)
            .filter(expiracao -> expiracao.diasReais() <= 30)
            .sorted(Comparator.comparingInt(ExpiracaoCalculada::diasReais))
            .map(ExpiracaoCalculada::expiracao)
            .toList();

        listExpiracoes.clear();
        listExpiracoes.addAll(lista);
    }

    private List<ExpiracaoRow> carregarLinhasExpiracoes() {
        return DB
            .query()
            .select(
                "dv.titulo AS titulo",
                "v.nome AS nome",
                "dv.seguradora AS detalhe",
                "dv.data_validade AS dataExpiracao",
                "'VEICULO' AS origem"
            )
            .from("documentos_veiculo dv")
            .join("veiculos v", "v.id = dv.veiculo_id")
            .unionAll(
                DB
                    .query()
                    .select(
                        "ds.titulo AS titulo",
                        "s.nome AS nome",
                        "ds.plano AS detalhe",
                        "ds.data_renovacao AS dataExpiracao",
                        "'SUBSCRICAO' AS origem"
                    )
                    .from("documentos_subscricao ds")
                    .join("subscricoes s", "s.id = ds.subscricao_id")
            )
            .unionAll(
                DB
                    .query()
                    .select(
                        "dp.titulo AS titulo",
                        "p.nome AS nome",
                        "dp.notas AS detalhe",
                        "dp.data_validade AS dataExpiracao",
                        "'PESSOAL' AS origem"
                    )
                    .from("documentos_pessoal dp")
                    .join("pessoas p", "p.id = dp.pessoa_id")
            )
            .execute(ExpiracaoRow.class);
    }

    private ExpiracaoCalculada toExpiracaoCalculada(ExpiracaoRow row) {
        LocalDate dataExpiracao = LocalDate.parse(row.dataExpiracao().substring(0, 10));
        int diasReais = (int) ChronoUnit.DAYS.between(LocalDate.now(), dataExpiracao);
        TipoExpiracoes tipo = diasReais < 0 ? TipoExpiracoes.EXPIRADO : TipoExpiracoes.BREVE;

        return new ExpiracaoCalculada(
            new Expiracoes(row.titulo(), subTitulo(row), Math.abs(diasReais), tipo), diasReais
        );
    }

    private String subTitulo(ExpiracaoRow row) {
        if ("VEICULO".equals(row.origem()) && hasText(row.nome()) && hasText(row.detalhe())) {
            return row.nome() + " · " + row.detalhe();
        }

        if (hasText(row.detalhe())) {
            return row.detalhe();
        }

        return row.nome();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private record ExpiracaoRow(
                                String titulo, String nome, String detalhe, String dataExpiracao,
                                String origem) {
    }

    private record ExpiracaoCalculada(Expiracoes expiracao, int diasReais) {
    }
}
