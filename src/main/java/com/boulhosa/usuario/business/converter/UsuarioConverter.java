package com.boulhosa.usuario.business.converter;

import com.boulhosa.usuario.business.dto.EnderecoDTO;
import com.boulhosa.usuario.business.dto.TelefoneDTO;
import com.boulhosa.usuario.business.dto.UsuarioDTO;
import com.boulhosa.usuario.infrastructure.entity.Endereco;
import com.boulhosa.usuario.infrastructure.entity.Telefone;
import com.boulhosa.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario dtoParaUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(dtoParaListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(dtoParaListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> dtoParaListaEndereco(List<EnderecoDTO> enderecoDTO){
        return enderecoDTO.stream().map(this::dtoParaEndereco).toList();
    }

    public Endereco dtoParaEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    public List<Telefone> dtoParaListaTelefone(List<TelefoneDTO> telefoneDTO){
        return telefoneDTO.stream().map(this::dtoParaTelefone).toList();
    }

    public Telefone dtoParaTelefone(TelefoneDTO dto) {
        return Telefone.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }

    public UsuarioDTO usuarioParaDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos().stream().map(this::enderecoParaDTO).toList())
                .telefones(usuario.getTelefones().stream().map(this::telefoneParaDTO).toList())
                .build();
    }

    public EnderecoDTO enderecoParaDTO(Endereco e) {
        return EnderecoDTO.builder()
                .id(e.getId())
                .rua(e.getRua())
                .numero(e.getNumero())
                .cidade(e.getCidade())
                .estado(e.getEstado())
                .cep(e.getCep())
                .build();
    }

    public TelefoneDTO telefoneParaDTO(Telefone t) {
        return TelefoneDTO.builder()
                .id(t.getId())
                .numero(t.getNumero())
                .ddd(t.getDdd())
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


    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco endereco){
        return Endereco.builder()
                .id(endereco.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : endereco.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : endereco.getNumero())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : endereco.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : endereco.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : endereco.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone telefone){
        return Telefone.builder()
                .id(telefone.getId())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefone.getNumero())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefone.getDdd())
                .build();
    }
}
