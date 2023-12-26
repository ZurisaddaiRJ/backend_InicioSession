/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*
 * Esta clase representa un servicio (Service) encargado de la lógica de inicio de sesión.
 * Está anotada con @Service, indicando que es un componente de servicio de Spring.
 */
package org.uv.Abarrotes.servicio;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.uv.Abarrotes.modelos.Empleado;
import org.uv.Abarrotes.repositorio.EmpleadoRepository;

@Service
public class InicioSessionService {

//    // Inyección de dependencias del repositorio de empleados.
//    @Autowired
//    private EmpleadoRepository empleadoRepository;
//    /*
//     * Método para realizar la autenticación de un empleado.
//     * Recibe un objeto Empleado que contiene nombre de usuario y contraseña.
//     * Devuelve un mapa con un mensaje y, en caso de inicio de sesión exitoso, el rol del empleado.
//     */
//    public Map<String, String> login(Empleado empleado) {
//        // Buscar un empleado existente en la base de datos por su nombre de usuario.
//        Empleado empleadoExistente = empleadoRepository.findByNombre(empleado.getNombre());
//        Map<String, String> response = new HashMap<>();
//
//        // Verificar si el empleado existe y si la contraseña proporcionada coincide.
//        if (empleadoExistente != null && empleadoExistente.getContrasenia().equals(empleado.getContrasenia())) {
//            // Si la autenticación es exitosa, establecer un mensaje de éxito y el rol del empleado.
//            response.put("message", "Inicio de sesión exitoso");
//            response.put("rol", empleadoExistente.getRoles().getNombre());
//            return response;
//        } else {
//            // Si la autenticación falla, establecer un mensaje de credenciales incorrectas.
//            response.put("message", "Credenciales incorrectas");
//            return response;
//        }
//    }
    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public boolean autenticarUsuario(String usuario, String contrasenia) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorUsuario(usuario);

        if (empleado != null) {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            return passwordEncoder.matches(contrasenia, empleado.getContrasenia());
// Verificar si las contraseñas coinciden sin utilizar BCrypt
            return empleado.getContrasenia().equals(contrasenia);
        }

        return false;
    }

//    public String asignarPermisos(String usuario) {
//        Empleado empleado = empleadoService.obtenerEmpleadoPorUsuario(usuario);
//
//        if (empleado != null) {
//            return empleado.getRoles().getNombre(); // Puedes ajustar esto según tu modelo de roles
//        }
//
//        return null;
//    }
    //Metodo para JEFE Y GERENTE 
    public String asignarPermisos(String usuario) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorUsuario(usuario);

        if (empleado != null && empleado.getRoles() != null) {
            // Verifica que getRoles() y getNombre() no sean nulos antes de utilizarlos
            return empleado.getRoles().getNombre(); // Puedes ajustar esto según tu modelo de roles
        }

        return null;
    }
}
