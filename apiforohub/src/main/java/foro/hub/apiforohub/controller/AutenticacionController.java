package foro.hub.apiforohub.controller;

import foro.hub.apiforohub.domain.usuario.DatosAutenticacionUsuario;
import foro.hub.apiforohub.domain.usuario.Usuario;
import foro.hub.apiforohub.domain.usuario.UsuarioRepository;
import foro.hub.apiforohub.infra.security.DatosJWTToken;
import foro.hub.apiforohub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.getEmail(), datosAutenticacionUsuario.getContrasena());
            Authentication authentication = authenticationManager.authenticate(authToken);
            String token = tokenService.generarToken((Usuario) authentication.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
