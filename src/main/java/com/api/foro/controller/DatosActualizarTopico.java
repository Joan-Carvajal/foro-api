package com.api.foro.controller;

import java.time.LocalDateTime;

public record DatosActualizarTopico(
        String titulo,
        String mensaje,
        Boolean estado
) {

}
