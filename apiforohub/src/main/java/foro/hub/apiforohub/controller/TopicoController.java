package foro.hub.apiforohub.controller;

import foro.hub.apiforohub.domain.topico.*;
import foro.hub.apiforohub.domain.usuario.Usuario;
import foro.hub.apiforohub.domain.usuario.UsuarioRepository;
import foro.hub.apiforohub.service.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")

public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, @RequestHeader("Authorization") String authHeader, UriComponentsBuilder uriComponentsBuilder){
        if (datosRegistroTopico == null ||
                datosRegistroTopico.titulo() == null ||
                datosRegistroTopico.mensaje() == null ||
                datosRegistroTopico.curso() == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios (titulo, mensaje, curso)");
        }

        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un topico con el mismo titulo y mensaje");
        }

        Usuario usuario = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String email = tokenService.getSubject(token);
            usuario = usuarioRepository.findByEmail(email);
        }

        Topico topico = new Topico(datosRegistroTopico);
        if (usuario != null) {
            topico.setUsuario(usuario);
        }

        topicoRepository.save(topico);

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getCurso(),
                topico.getUsuario().getEmail());
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public Page<DatosListadoTopico> listadoTopicos(@PageableDefault(size = 4) Pageable paginacion){
        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> listadoTopicoEspecifico(@PathVariable Long id){
        Optional<Topico> topico= topicoRepository.findById(id);
        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topicoDTO = topico.get();
        DatosRespuestaTopico datosTopico = new DatosRespuestaTopico(
                topicoDTO.getId(),
                topicoDTO.getTitulo(),
                topicoDTO.getMensaje(),
                topicoDTO.getFechaCreacion(),
                topicoDTO.getCurso(),
                topicoDTO.getUsuario().getEmail()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topicoDTO = topico.get();
        topicoDTO.actualizarDatos(datosActualizarTopico);
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topicoDTO.getId(),
                topicoDTO.getTitulo(),
                topicoDTO.getMensaje(),
                topicoDTO.getFechaCreacion(),
                topicoDTO.getCurso(),
                topicoDTO.getUsuario().getEmail()
        );
        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topicoDel = topico.get();
        topicoRepository.delete(topicoDel);
        return ResponseEntity.noContent().build();
    }
}
