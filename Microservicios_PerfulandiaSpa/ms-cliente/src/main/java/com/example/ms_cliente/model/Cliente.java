package com.example.ms_cliente.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* Esta tabla en si corresponde al detalle del cliente
    La cual incorpora su fecha de registro y dirección de envío
    Se debe evaluar el ID ya que es una tabla "intermedia" correspondiente
    al usuario.
    */
}
