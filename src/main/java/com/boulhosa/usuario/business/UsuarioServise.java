package com.boulhosa.usuario.business;

import com.boulhosa.usuario.business.converter.UsuarioConverter;
import com.boulhosa.usuario.business.dto.UsuarioDTO;
import com.boulhosa.usuario.infrastructure.entity.Usuario;
import com.boulhosa.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServise {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.dtoParaUsuario(usuarioDTO);

        return usuarioConverter.usuarioParaDTO(
                usuarioRepository.save(usuario)
        );
    }

}
