package com.boulhosa.usuario.controller;

import com.boulhosa.usuario.business.UsuarioServise;
import com.boulhosa.usuario.business.dto.EnderecoDTO;
import com.boulhosa.usuario.business.dto.TelefoneDTO;
import com.boulhosa.usuario.business.dto.UsuarioDTO;
import com.boulhosa.usuario.infrastructure.entity.Usuario;
import com.boulhosa.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServise usuarioServise;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){

        return ResponseEntity.ok(usuarioServise.salvaUsuario(usuarioDTO));

    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()
                )
        );

        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email){

        return ResponseEntity.ok(usuarioServise.buscarPorEmail(email));

    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioServise.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosDeUsuario(@RequestBody UsuarioDTO usuarioDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioServise.atualizaDadosDeUsuario(token, usuarioDTO));

    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO, @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioServise.atualizaEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO telefoneDTO, @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioServise.atualizaTelefone(id, telefoneDTO));
    }

}
