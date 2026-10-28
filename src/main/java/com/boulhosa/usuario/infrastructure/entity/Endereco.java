package com.boulhosa.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    @Column(name = "numero", nullable = false, length = 10)
    private String numero;

    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "cep", nullable = false, length = 20)
    private String cep;

    @Column(name = "usuario_id")
    private Long usuario_Id;

}
