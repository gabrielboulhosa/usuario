package com.boulhosa.usuario.business;

import com.boulhosa.usuario.business.converter.UsuarioConverter;
import com.boulhosa.usuario.business.dto.EnderecoDTO;
import com.boulhosa.usuario.business.dto.TelefoneDTO;
import com.boulhosa.usuario.business.dto.UsuarioDTO;
import com.boulhosa.usuario.infrastructure.entity.Endereco;
import com.boulhosa.usuario.infrastructure.entity.Telefone;
import com.boulhosa.usuario.infrastructure.entity.Usuario;
import com.boulhosa.usuario.infrastructure.exptions.ConflictExeption;
import com.boulhosa.usuario.infrastructure.exptions.ResourceNotFaundExeption;
import com.boulhosa.usuario.infrastructure.repository.EnderecoRepository;
import com.boulhosa.usuario.infrastructure.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


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

    public UsuarioDTO buscarPorEmail(String email){
        try {
            Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFaundExeption("Usuário não encontrado"));
            return usuarioConverter.usuarioParaDTO(usuario);
        } catch (
                ResourceNotFaundExeption e) {
            throw new ResourceNotFaundExeption("Usuário não encontrado " + e.getCause(), e);
        }
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

        Usuario usuarioBuscado = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFaundExeption("Usuário não encontrado"));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );

        Usuario usuarioSalvo = usuarioConverter.updateUsuario(usuarioDTO, usuarioBuscado);

        return usuarioConverter.usuarioParaDTO(
                usuarioRepository.save(usuarioSalvo)
        );
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFaundExeption("Endereço não encontrado"));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.enderecoParaDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFaundExeption("Telefone não encontrado"));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        return usuarioConverter.telefoneParaDTO(telefoneRepository.save(telefone));
    }

}
