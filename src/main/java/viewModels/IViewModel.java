package viewModels;

import java.util.List;

public abstract interface IViewModel<T> {
    public abstract List<T> ver();

    public abstract void novo();

    public abstract void update(int id);

    public abstract void apagar(int id);
}
