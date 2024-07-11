package foro.hub.apiforohub.domain.topico;

import com.fasterxml.jackson.annotation.JsonIgnore;
import foro.hub.apiforohub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,

        @JsonIgnore
        LocalDateTime fechaCreacion,

        @NotBlank
        String curso) {
}
