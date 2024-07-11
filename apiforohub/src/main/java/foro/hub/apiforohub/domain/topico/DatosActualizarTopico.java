package foro.hub.apiforohub.domain.topico;

import foro.hub.apiforohub.domain.usuario.DatosUsuario;
import foro.hub.apiforohub.domain.usuario.Usuario;

public record DatosActualizarTopico(Long id, String titulo, String mensaje, String curso) {
}
