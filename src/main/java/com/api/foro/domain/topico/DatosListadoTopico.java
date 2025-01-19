package com.api.foro.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        String titulo,
        String mensaje,
        LocalDateTime fecha
) {
    public DatosListadoTopico(Topico topico){
        this(topico.getTitulo(),topico.getMensaje(),topico.getFecha());
    }
}
