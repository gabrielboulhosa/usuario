package com.boulhosa.usuario.business.converter;

import com.boulhosa.usuario.business.dto.UsuarioDTO;
import com.boulhosa.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    public Usuario dtoParaUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .build();
    }

    public UsuarioDTO usuarioParaDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario){
       return Usuario.builder()
               .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuario.getNome())
               .id(usuario.getId())
               .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuario.getSenha())
               .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail())
               .build();
    }
}
