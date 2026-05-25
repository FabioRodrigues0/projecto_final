package models;

import java.util.Date;

/**
 * SuperClasse identidade
 * -dentro daquilo que tinhamos defenido na aula,pessoa,veiculo e subscrição;
 * -esta classe tem os atributos comuns a todas as outras classes, ou seja, id,nome e data de
 * criação;
 * -serializable para que possa ser guradada em ficheiros e posteriormente lida;
 */
public abstract class Identidades {

    protected int id;
    protected String nome;
    protected Date data;

    /**
     * getters e setters
     * id unico para cada identidade, nome e data de criação
     */

    public abstract int getId();

    public abstract String getNome();

    public abstract Date getData();

    public abstract void setId(int id);

    public abstract void setNome(String nome);

    public abstract void setData(Date data);
}
