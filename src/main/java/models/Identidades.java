package models;

import java.time.LocalDateTime;

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
    protected LocalDateTime data;

    /**
     * getters e setters
     * id unico para cada identidade, nome e data de criação
     */
    public abstract int getId();

    /**
     * Executa a operação getNome.
     *
     * @return resultado da operação
     */
    public abstract String getNome();

    /**
     * Executa a operação getData.
     *
     * @return resultado da operação
     */
    public abstract LocalDateTime getData();

    /**
     * Executa a operação setId.
     *
     * @param id valor usado pela operação
     */
    public abstract void setId(int id);

    /**
     * Executa a operação setNome.
     *
     * @param nome valor usado pela operação
     */
    public abstract void setNome(String nome);

    /**
     * Executa a operação setData.
     *
     * @param data valor usado pela operação
     */
    public abstract void setData(LocalDateTime data);
}
