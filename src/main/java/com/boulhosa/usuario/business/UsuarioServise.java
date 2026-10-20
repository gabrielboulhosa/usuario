package com.boulhosa.usuario.business;

import com.boulhosa.usuario.business.converter.UsuarioConverter;
import com.boulhosa.usuario.business.dto.UsuarioDTO;
import com.boulhosa.usuario.infrastructure.entity.Usuario;
import com.boulhosa.usuario.infrastructure.exptions.ConflictExeption;
import com.boulhosa.usuario.infrastructure.exptions.ResourceNotFaundExeption;
import com.boulhosa.usuario.infrastructure.repository.UsuarioRepository;
import com.boulhosa.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServise {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        existeEmail(usuarioDTO.getEmail());
        Usuario usuario = usuarioConverter.dtoParaUsuario(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioConverter.usuarioParaDTO(
                usuarioRepository.save(usuario)
        );
    }

    public void existeEmail(String email) {

        try {
            if (usuarioRepository.existsByEmail(email)){
                throw new ConflictExeption("Email já cadastrado");
            }
        } catch (ConflictExeption e) {
            throw new ConflictExeption("Email já cadastrado " + e.getCause(), e);
        }
    }

    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFaundExeption("Usuário não encontrado"));
    }

    public void deletaUsuarioPorEmail(String email){
        if (usuarioRepository.existsByEmail(email)){
            usuarioRepository.deleteByEmail(email);
        } else {
            throw new ResourceNotFaundExeption("Usuário não encontrado");

        }
    }

    public UsuarioDTO atualizaDadosDeUsuario(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extractUsername(token.substring(7));

        Usuario usuarioBuscado = buscarPorEmail(email);

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );

        Usuario usuarioSalvo = usuarioConverter.updateUsuario(usuarioDTO, usuarioBuscado);

        return usuarioConverter.usuarioParaDTO(
                usuarioRepository.save(usuarioSalvo)
        );
    }

}
