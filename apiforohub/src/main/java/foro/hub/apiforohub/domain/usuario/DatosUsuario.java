package foro.hub.apiforohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosUsuario(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String contrasena) {
}
