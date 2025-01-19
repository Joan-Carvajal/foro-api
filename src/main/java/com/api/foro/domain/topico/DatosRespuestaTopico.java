package com.api.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
         String titulo,
         String mensaje,
         LocalDateTime fecha,
         Boolean estado
) {

}
