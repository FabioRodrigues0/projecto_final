package viewModels;

import java.util.List;

public abstract interface IViewModelDocumentos<T> {
    public abstract List<T> verDocumentos();

    public abstract void novoDocumento(T doc);

    public abstract void updateDocumento(int id);

    public abstract void apagarDocumento(int id);
}
