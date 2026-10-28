package com.boulhosa.usuario.infrastructure.repository;

import com.boulhosa.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

     Boolean existsByEmail(String email);

     Optional<Usuario> findByEmail(String email);

     @Transactional
     void deleteByEmail(String email);

}
