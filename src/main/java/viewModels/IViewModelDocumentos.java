package viewModels;

import java.util.List;

/**
 * Define o contrato de IViewModelDocumentos.
 */
public abstract interface IViewModelDocumentos<T> {
    /**
     * Executa a operação nomeDocumento.
     *
     * @return resultado da operação
     */
    public default String nomeDocumento() {
        return "documento";
    }

    /**
     * Obtém os documentos existentes.
     *
     * @return resultado da operação
     */
    public abstract List<T> verDocumentos();

    /**
     * Cria um novo documento.
     *
     * @param id valor usado pela operação
     */
    public abstract void novoDocumento(int id);

    /**
     * Atualiza um documento existente.
     *
     * @param id valor usado pela operação
     */
    public abstract void updateDocumento(int id);

    /**
     * Remove um documento existente.
     *
     * @param id valor usado pela operação
     */
    public abstract void apagarDocumento(int id);
}
