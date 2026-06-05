package viewModels;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.Pessoal.DocumentosPessoal;
import models.Subscricao.DocumentosSubscricao;
import models.TipoItemCalendario;
import models.Veiculo.DocumentosVeiculo;
import models.calendario.ItemCalendario;

public class CalendarioViewModel extends BricksViewModel {

    private final List<ItemCalendario> todososPrazos = new ArrayList<>();

    // Inicialização segura através do método estático da classe mãe
    public final StateList<ItemCalendario> itensDoDia = stateList(List.of());
    public final StateList<ItemCalendario> proximosPrazos = stateList(List.of());

    private YearMonth mesAtual = YearMonth.now();
    private LocalDate diaSelecionado = LocalDate.now();

    public void carregarExpirações() {
        try {
            this.todososPrazos.clear();
            this.proximosPrazos.clear();

            // 1. Documentos Pessoais
            List<DocumentosPessoal> pessoal = DB
                .query()
                .select("id", "pessoa_id", "titulo", "tipo", "data_emissao", "data_validade")
                .from("documentos_pessoal")
                .execute(DocumentosPessoal.class);

            if (pessoal != null) {
                for (DocumentosPessoal d : pessoal) {
                    if (d.getDataValidade() != null) {
                        todososPrazos
                            .add(
                                new ItemCalendario(
                                    d.getTitulo(), TipoItemCalendario.PESSOAL, d.getDataValidade()
                                )
                            );
                    }
                }
            }

            // 2. Documentos Veículos
            List<DocumentosVeiculo> veiculos = DB
                .query()
                .select(
                    "id",
                    "veiculo_id",
                    "titulo",
                    "tipo",
                    "data_validade",
                    "seguradora",
                    "cobertura",
                    "valor"
                )
                .from("documentos_veiculo")
                .execute(DocumentosVeiculo.class);

            if (veiculos != null) {
                for (DocumentosVeiculo d : veiculos) {
                    if (d.getDataValidade() != null) {
                        todososPrazos
                            .add(
                                new ItemCalendario(
                                    d.getTitulo(), TipoItemCalendario.VEICULO, d.getDataValidade()
                                )
                            );
                    }
                }
            }

            // 3. Subscrições
            List<DocumentosSubscricao> subscricoes = DB
                .query()
                .select(
                    "id",
                    "subscricao_id",
                    "titulo",
                    "tipo",
                    "modelo_pagamento",
                    "custo",
                    "plano",
                    "data_renovacao",
                    "ativa"
                )
                .from("documentos_subscricao")
                .execute(DocumentosSubscricao.class);

            if (subscricoes != null) {
                for (DocumentosSubscricao d : subscricoes) {
                    if (d.getDataRenovacao() != null) {
                        todososPrazos
                            .add(
                                new ItemCalendario(
                                    d.getTitulo(), TipoItemCalendario.SUBSCRICAO, d
                                        .getDataRenovacao()
                                )
                            );
                    }
                }
            }

            // Ordena globalmente por data mais antiga/próxima
            todososPrazos.sort(Comparator.comparing(ItemCalendario::getData));

            // Atualiza as listas reativas da interface
            atualizarProximosEventos();
            atualizarItensDoDia();

        } catch (Exception e) {
            System.err.println("Erro ao carregar expirações: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void avancarMes() {
        this.mesAtual = this.mesAtual.plusMonths(1);
        this.diaSelecionado = this.mesAtual.atDay(1);
        atualizarItensDoDia();
    }

    public void recuarMes() {
        this.mesAtual = this.mesAtual.minusMonths(1);
        this.diaSelecionado = this.mesAtual.atDay(1);
        atualizarItensDoDia();
    }

    public void selecionarDia(LocalDate dia) {
        this.diaSelecionado = dia;
        atualizarItensDoDia();
    }

    public YearMonth getMesAtualVisivel() {
        return this.mesAtual;
    }

    public LocalDate getDiaSelecionado() {
        return this.diaSelecionado;
    }

    public List<ItemCalendario> getTodosOsPrazos() {
        return this.todososPrazos;
    }

    private void atualizarItensDoDia() {
        this.itensDoDia.clear();
        for (ItemCalendario item : todososPrazos) {
            if (item.getData().equals(this.diaSelecionado)) {
                this.itensDoDia.add(item);
            }
        }
    }

    private void atualizarProximosEventos() {
        this.proximosPrazos.clear();
        LocalDate hoje = LocalDate.now();
        int adicionados = 0;
        for (ItemCalendario item : todososPrazos) {
            if (!item.getData().isBefore(hoje) && adicionados < 5) {
                this.proximosPrazos.add(item);
                adicionados++;
            }
        }
    }

    public String obterCorHex(LocalDate data) {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), data);
        if (dias < 0) return "#ef4444";      // Vermelho
        if (dias < 15) return "#f59e0b";     // Amarelo/Laranja
        return "#22c55e";                    // Verde
    }

    public long diasRestantes(LocalDate data) {
        return ChronoUnit.DAYS.between(LocalDate.now(), data);
    }
}
