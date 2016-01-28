package br.tcc.glic.domain.personagem;

import java.util.Map;

import br.tcc.glic.domain.enums.EstadoPersonagem;
import br.tcc.glic.domain.enums.TipoMensagem;

/**
 * Created by André on 27/01/2016.
 */
public class TipoPersonagem {
    private String nome;
    private Map<Integer, Map<EstadoPersonagem, byte[]>> sprites;
    private Map<TipoMensagem, String> mensagens;
}
