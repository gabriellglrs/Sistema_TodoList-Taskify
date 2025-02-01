package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.entities.Lista;

public class ListaProvider {

    public static final Long ID = 1L;

    public Lista create () {
        Lista lista = new Lista();
        lista.setId(ID);
        return lista;
    }
}
