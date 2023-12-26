/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.Abarrotes.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.uv.Abarrotes.DTOs.DTOEmpleadoInfo;
import org.uv.Abarrotes.modelos.Empleado;
import org.uv.Abarrotes.modelos.Rol;
import org.uv.Abarrotes.repositorio.EmpleadoRepository;
import org.uv.Abarrotes.repositorio.RolRepository;

/**
 *
 * @author yacruz
 */
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolRepository rolRepository;
//Metodo para crear Empleados original
//    public DTOEmpleadoInfo crearEmpleado(Empleado empleado) {
//        // Verificar si el rol existe
//        Rol rol = rolRepository.findById(empleado.getRoles().getIdRol())
//             .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
//
//        empleado.setRoles(rol);
//
//        Empleado empleadoG = empleadoRepository.save(empleado);
//
//        org.uv.Abarrotes.DTOs.DTOEmpleadoInfo dto = new DTOEmpleadoInfo(empleadoG);
//
//        return dto;
//    }
    //Metodo para agregar empleado editado
//    public DTOEmpleadoInfo crearEmpleado(Empleado empleado) {
//    // Verificar si el rol existe
//    if (empleado.getRoles() == null || empleado.getRoles().getIdRol() == null) {
//        // Manejar el caso en que roles o su ID sea null
//        // Puedes lanzar una excepción, devolver un valor predeterminado, etc.
//        throw new IllegalArgumentException("El objeto Empleado debe tener un objeto Rol válido");
//    }
//
//    Rol rol = rolRepository.findById(empleado.getRoles().getIdRol())
//         .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
//
//    empleado.setRoles(rol);
//
//    Empleado empleadoG = empleadoRepository.save(empleado);
//
//    org.uv.Abarrotes.DTOs.DTOEmpleadoInfo dto = new DTOEmpleadoInfo(empleadoG);
//
//    return dto;
//}

    public DTOEmpleadoInfo crearEmpleado(Empleado empleado) {
        // Verificar si el rol "EMPLOYEE" existe
        Rol rolEmpleado = rolRepository.findFirstByCve("EMPLOYEE")
             .orElseThrow(() -> new EntityNotFoundException("Rol 'EMPLOYEE' no encontrado"));

        // Asignar automáticamente el rol "EMPLOYEE" al nuevo empleado
        empleado.setRoles(rolEmpleado);

        Empleado empleadoG = empleadoRepository.save(empleado);

        org.uv.Abarrotes.DTOs.DTOEmpleadoInfo dto = new DTOEmpleadoInfo(empleadoG);

        return dto;
    }
//Metodo Original
//    public List<DTOEmpleadoInfo> obtenerEmpleados() {
//        List<DTOEmpleadoInfo> DTOempleados = new ArrayList<>();
//        List<Empleado> empleados = empleadoRepository.findAll();
//
//        // Convertir cada producto a DTOProductoInfo
//        for (Empleado empleado : empleados) {
//            DTOEmpleadoInfo dto = new DTOEmpleadoInfo(empleado);
//            DTOempleados.add(dto);
//        }
//
//        return DTOempleados;
//    }

    //Metodo LIst Modificado
    public List<DTOEmpleadoInfo> obtenerEmpleados() {
        // Supongamos que tienes un método en tu repositorio llamado findAll() que devuelve empleados
        List<Empleado> empleados = empleadoRepository.findAll();

        // Convierte la lista de empleados a una lista de DTOEmpleadoInfo
        List<DTOEmpleadoInfo> dtoEmpleados = empleados.stream()
             .map(empleado -> convertirAEmpleadoInfo(empleado))
             .collect(Collectors.toList());

        return dtoEmpleados;
    }

    private DTOEmpleadoInfo convertirAEmpleadoInfo(Empleado empleado) {
        DTOEmpleadoInfo dtoEmpleado = new DTOEmpleadoInfo();
        // Copia los campos necesarios desde el empleado al DTO
        dtoEmpleado.setIdEmpleado(empleado.getIdEmpleado());
        dtoEmpleado.setNombre(empleado.getNombre());
        dtoEmpleado.setApellidos(empleado.getApellidos());

        // Asegúrate de que empleado.getRoles() devuelva el objeto de roles y luego obtén el idRol
        dtoEmpleado.setIdRol(empleado.getRoles().getIdRol());

        return dtoEmpleado;
    }

    public DTOEmpleadoInfo obtenerEmpleadoPorId(long idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
             .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        DTOEmpleadoInfo dto = new DTOEmpleadoInfo(empleado);

        return dto;
    }

    //--------
    public DTOEmpleadoInfo actualizarEmpleado(Long idEmpleado, Empleado empleadoActualizado) {
        Empleado empleadoExistente = empleadoRepository.findById(idEmpleado)
             .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // Update fields
        empleadoExistente.setNombre(empleadoActualizado.getNombre());
        empleadoExistente.setApellidos(empleadoActualizado.getApellidos());
        empleadoExistente.setRoles(empleadoActualizado.getRoles());

        // Save the updated employee
        Empleado empleadoG = empleadoRepository.save(empleadoExistente);

        // Convert to DTO and return
        DTOEmpleadoInfo dto = new DTOEmpleadoInfo(empleadoG);
        return dto;
    }

    public void eliminarEmpleado(Long idEmpleado) {
        Empleado empleadoExistente = empleadoRepository.findById(idEmpleado)
             .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // Delete the employee
        empleadoRepository.delete(empleadoExistente);
    }
///Meodos para el inicio de sesion

    public Empleado obtenerEmpleadoPorUsuario(String usuario) {
        return empleadoRepository.findByNombre(usuario);
    }

    public List<String> obtenerRolesDisponibles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream().map(Rol::getNombre).collect(Collectors.toList());
    }

    //__________________________
    //MEtodo para gerente y jefe
//    @PostConstruct
//    public void agregarEmpleadosPorDefecto() {
//        // Agregar empleado del jefe si no existe
//        Empleado jefe = empleadoRepository.findByNombre("jefe");
//        if (jefe == null) {
//            jefe = new Empleado();
//            jefe.setNombre("jefe");
//            jefe.setApellidos("ApellidoJefe");
//            jefe.setContrasenia("contraseniaJefe");  // Considera utilizar encriptación para contraseñas reales
//            // Asigna el rol correspondiente (debes tener el rol con el nombre correcto en la base de datos)
//            Optional<Rol> rolJefe = rolRepository.findByDescripcion("ROLE_JEFE");
//            rolJefe.ifPresent(jefe::setRoles);
//            empleadoRepository.save(jefe);
//
//            // Agregar empleado del gerente si no existe
//            Empleado gerente = empleadoRepository.findByNombre("gerente");
//            if (gerente == null) {
//                gerente = new Empleado();
//                gerente.setNombre("gerente");
//                gerente.setApellidos("ApellidoGerente");
//                gerente.setContrasenia("contraseniaGerente");  // Considera utilizar encriptación para contraseñas reales
//                // Asigna el rol correspondiente (debes tener el rol con el nombre correcto en la base de datos)
//                Optional<Rol> rolGerente = rolRepository.findByDescripcion("ROLE_GERENTE");
//                rolGerente.ifPresent(gerente::setRoles);
//                empleadoRepository.save(gerente);
//
//            }
//        }
//    }
//    @PostConstruct
//    public void agregarEmpleadosPorDefecto() {
//        // Agregar empleado del jefe si no existe
//        crearEmpleadoSiNoExiste("jefe", "ApellidoJefe", "contraseniaJefe", "ROLE_JEFE");
//
//        // Agregar empleado del gerente si no existe
//        crearEmpleadoSiNoExiste("gerente", "ApellidoGerente", "contraseniaGerente", "ROLE_GERENTE");
//    }
//
//    private void crearEmpleadoSiNoExiste(String nombre, String apellidos, String contrasenia, String rolDescripcion) {
//        Empleado empleado = empleadoRepository.findByNombre(nombre);
//
//        if (empleado == null) {
//            empleado = new Empleado();
//            empleado.setNombre(nombre);
//            empleado.setApellidos(apellidos);
//            empleado.setContrasenia(contrasenia);  // Considera utilizar encriptación para contraseñas reales
//
//            // Asigna el rol correspondiente (debes tener el rol con el nombre correcto en la base de datos)
//            Optional<Rol> rol = rolRepository.findByCve(rolDescripcion);
//
//            if (rol.isPresent()) {
//                empleado.setRoles(rol.get());
//                empleadoRepository.save(empleado);
//            } else {
//                // Manejo de error si el rol no existe
//                System.err.println("Error: No se encontró el rol con la descripción: " + rolDescripcion);
//            }
//        }
//    }
//    @PostConstruct
//    public void agregarEmpleadosYRolesPorDefecto() {
//        // Agregar empleado del jefe si no existe
//        Empleado jefeOptional = empleadoRepository.findByNombre("jefe");
//        if (jefeOptional.isEmpty()) {
//            Empleado nuevoJefe = new Empleado();
//            nuevoJefe.setNombre("jefe");
//            nuevoJefe.setApellidos("ApellidoJefe");
//            nuevoJefe.setContrasenia("contraseniaJefe");  // Considera utilizar encriptación para contraseñas reales
//
//            // Agregar rol correspondiente si no existe
//            Optional<Rol> rolJefeOptional = rolRepository.findByCve("ROLE_JEFE");
//            Rol rolJefeGuardado = rolJefeOptional.orElseGet(() -> rolRepository.save(new Rol("ROLE_JEFE")));
//
//            nuevoJefe.setRoles(rolJefeGuardado);
//            empleadoRepository.save(nuevoJefe);
//        }
//
//        // Agregar empleado del gerente si no existe
//        Empleado gerenteOptional = empleadoRepository.findByNombre("gerente");
//        if (gerenteOptional.isEmpty()) {
//            Empleado nuevoGerente = new Empleado();
//            nuevoGerente.setNombre("gerente");
//            nuevoGerente.setApellidos("ApellidoGerente");
//            nuevoGerente.setContrasenia("contraseniaGerente");  // Considera utilizar encriptación para contraseñas reales
//
//            // Agregar rol correspondiente si no existe
//            Optional<Rol> rolGerenteOptional = rolRepository.findByCve("ROLE_GERENTE");
//            Rol rolGerenteGuardado = rolGerenteOptional.orElseGet(() -> rolRepository.save(new Rol("ROLE_GERENTE")));
//
//            nuevoGerente.setRoles(rolGerenteGuardado);
//            empleadoRepository.save(nuevoGerente);
//        }
//
//        // Puedes agregar más empleados y roles según sea necesario
//    }
    public void init() {
        // Verificar si ya existen empleados en la base de datos
//        if (empleadoRepository.count() == 0) {
        // Si no hay empleados, crea dos empleados por defecto (jefe y gerente)

        // Crear el rol "Jefe"
        Rol rolJefe = new Rol();
        rolJefe.setCve("JEFE");
        rolJefe.setDescripcion("Jefe");
        rolRepository.save(rolJefe);

        // Crear el empleado "Jefe" con contraseña "jefe123"
        Empleado empleadoJefe = new Empleado();
        empleadoJefe.setNombre("Jefe");
        empleadoJefe.setApellidos("ApellidoJefe");
        empleadoJefe.setContrasenia("jefe123"); // ¡Recuerda hashear la contraseña en un entorno de producción!
        empleadoJefe.setRoles(rolJefe);
        empleadoRepository.save(empleadoJefe);

        // Crear el rol "Gerente"
        Rol rolGerente = new Rol();
        rolGerente.setCve("GERENTE");
        rolGerente.setDescripcion("Gerente");
        rolRepository.save(rolGerente);

        // Crear el empleado "Gerente" con contraseña "gerente123"
        Empleado empleadoGerente = new Empleado();
        empleadoGerente.setNombre("Gerente");
        empleadoGerente.setApellidos("ApellidoGerente");
        empleadoGerente.setContrasenia("gerente123"); // ¡Recuerda hashear la contraseña en un entorno de producción!
        empleadoGerente.setRoles(rolGerente);
        empleadoRepository.save(empleadoGerente);
//        }
    }

    //Metodo  para buscar Empleados por No,bre y apellidos
    public List<DTOEmpleadoInfo> buscarEmpleadosPorNombreYApellidos(String nombre, String apellidos) {
        List<Empleado> empleados;

        if (nombre != null && apellidos != null) {
            // Buscar por nombre y apellidos
            empleados = empleadoRepository.findByNombreAndApellidos(nombre, apellidos);
        } else if (nombre != null) {
            // Buscar solo por nombre
            empleados = (List<Empleado>) empleadoRepository.findByNombre(nombre);
        } else if (apellidos != null) {
            // Buscar solo por apellidos
            empleados = empleadoRepository.findByApellidos(apellidos);
        } else {
            // Si ambos son nulos, devolver todos los empleados
            empleados = empleadoRepository.findAll();
        }

        return empleados.stream()
             .map(empleado -> convertirAEmpleadoInfo(empleado))
             .collect(Collectors.toList());
    }

}
