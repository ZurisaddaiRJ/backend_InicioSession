/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.Abarrotes.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.Abarrotes.modelos.Rol;

/**
 *
 * @author loken
 */
public interface RolRepository extends JpaRepository<Rol, Long> {

//    Optional<Rol> findByDescripcion(String descripcion);
//        Optional<Rol> findByCve(String cve);
    Optional<Rol> findFirstByCve(String cve);

}
