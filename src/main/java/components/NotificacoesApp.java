package components;

import fabiorodrigues.bricks.components.Notification;
import fabiorodrigues.bricks.components.NotificationType;
import fabiorodrigues.bricks.core.BricksApplication;
import java.util.List;
import models.Expiracoes;
import models.TipoExpiracoes;
import viewModels.IViewModel;
import viewModels.IViewModelDocumentos;

/**
 * Representa NotificacoesApp na aplicação.
 */
public final class NotificacoesApp {
    /**
     * Cria uma nova instância.
     */
    private NotificacoesApp() {
    }

    /**
     * Executa a operação criado.
     *
     * @param app valor usado pela operação
     * @param vm  valor usado pela operação
     */
    public static void criado(BricksApplication app, IViewModel<?> vm) {
        sucesso(app, "Criação concluída: " + vm.nomeRecurso() + ".");
    }

    /**
     * Executa a operação atualizado.
     *
     * @param app valor usado pela operação
     * @param vm  valor usado pela operação
     */
    public static void atualizado(BricksApplication app, IViewModel<?> vm) {
        sucesso(app, "Atualização concluída: " + vm.nomeRecurso() + ".");
    }

    /**
     * Executa a operação removido.
     *
     * @param app valor usado pela operação
     * @param vm  valor usado pela operação
     */
    public static void removido(BricksApplication app, IViewModel<?> vm) {
        aviso(app, "Remoção concluída: " + vm.nomeRecurso() + ".");
    }

    /**
     * Executa a operação documentoCriado.
     *
     * @param app valor usado pela operação
     * @param vm  valor usado pela operação
     */
    public static void documentoCriado(BricksApplication app, IViewModelDocumentos<?> vm) {
        sucesso(app, "Criação concluída: " + vm.nomeDocumento() + ".");
    }

    /**
     * Executa a operação documentoAtualizado.
     *
     * @param app valor usado pela operação
     * @param vm  valor usado pela operação
     */
    public static void documentoAtualizado(BricksApplication app, IViewModelDocumentos<?> vm) {
        sucesso(app, "Atualização concluída: " + vm.nomeDocumento() + ".");
    }

    /**
     * Executa a operação documentoRemovido.
     *
     * @param app valor usado pela operação
     * @param vm  valor usado pela operação
     */
    public static void documentoRemovido(BricksApplication app, IViewModelDocumentos<?> vm) {
        aviso(app, "Remoção concluída: " + vm.nomeDocumento() + ".");
    }

    /**
     * Executa a operação notificacoesSistemaAtivadas.
     *
     * @param app        valor usado pela operação
     * @param expiracoes valor usado pela operação
     */
    public static void notificacoesSistemaAtivadas(
                                                   BricksApplication app, List<Expiracoes> expiracoes
    ) {
        if (expiracoes == null || expiracoes.isEmpty()) {
            Notification
                .toast(
                    app,
                    "Notificações ativadas. Não existem alertas pendentes.",
                    NotificationType.INFO
                );
            return;
        }

        expiracoes
            .forEach(
                expiracao -> Notification
                    .system(
                        app,
                        tituloExpiracao(expiracao),
                        mensagemExpiracao(expiracao),
                        tipoExpiracao(expiracao)
                    )
            );
        Notification
            .toast(
                app,
                "Notificações do sistema ativadas para " + expiracoes.size() + " alerta(s).",
                NotificationType.SUCCESS
            );
    }

    /**
     * Executa a operação sucesso.
     *
     * @param app      valor usado pela operação
     * @param mensagem valor usado pela operação
     */
    private static void sucesso(BricksApplication app, String mensagem) {
        Notification.toast(app, mensagem, NotificationType.SUCCESS);
    }

    /**
     * Executa a operação aviso.
     *
     * @param app      valor usado pela operação
     * @param mensagem valor usado pela operação
     */
    private static void aviso(BricksApplication app, String mensagem) {
        Notification.toast(app, mensagem, NotificationType.WARNING);
    }

    /**
     * Executa a operação tituloExpiracao.
     *
     * @param expiracao valor usado pela operação
     * @return resultado da operação
     */
    private static String tituloExpiracao(Expiracoes expiracao) {
        return expiracao.tipo() == TipoExpiracoes.EXPIRADO ? expiracao
            .titulo() + " expirado" : expiracao.titulo() + " a vencer";
    }

    /**
     * Executa a operação mensagemExpiracao.
     *
     * @param expiracao valor usado pela operação
     * @return resultado da operação
     */
    private static String mensagemExpiracao(Expiracoes expiracao) {
        int dias = expiracao.tipo() == TipoExpiracoes.EXPIRADO ? -expiracao.dias() : expiracao
            .dias();
        String prazo = DiasRestantes.texto(dias);

        if (expiracao.subTitulo() == null || expiracao.subTitulo().isBlank()) {
            return prazo;
        }

        return expiracao.subTitulo() + " - " + prazo;
    }

    /**
     * Executa a operação tipoExpiracao.
     *
     * @param expiracao valor usado pela operação
     * @return resultado da operação
     */
    private static NotificationType tipoExpiracao(Expiracoes expiracao) {
        return expiracao
            .tipo() == TipoExpiracoes.EXPIRADO ? NotificationType.ERROR : NotificationType.WARNING;
    }
}
