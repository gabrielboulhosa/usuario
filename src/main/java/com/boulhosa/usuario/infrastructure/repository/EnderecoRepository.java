package com.boulhosa.usuario.infrastructure.repository;

import com.boulhosa.usuario.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Override
    Optional<Endereco> findById(Long id);
}
