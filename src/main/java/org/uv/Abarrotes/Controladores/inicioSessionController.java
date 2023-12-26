/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.Abarrotes.Controladores;

/**
 *
 * @author zurisaddairj
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.uv.Abarrotes.modelos.Empleado;
import org.uv.Abarrotes.repositorio.EmpleadoRepository;
import org.uv.Abarrotes.servicio.EmpleadoService;
import org.uv.Abarrotes.servicio.InicioSessionService;
import org.uv.Abarrotes.servicio.RolService;

@RestController
//@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Reemplaza con la URL de tu aplicación React

public class inicioSessionController {

//    @Autowired
//    private EmpleadoRepository empleadoRepository;// Repositorio para acceder a los datos de los empleados
//
//    @Autowired
//    private InicioSessionService inicioSessionService;// Servicio para la lógica de inicio de sesión
//
//    @PostMapping("/inicioSession")
//    public ResponseEntity<Map<String, String>> loginSubmit(@RequestBody Empleado empleado) {
//        // Llama al servicio de inicio de sesión para obtener la respuesta.
//        Map<String, String> response = inicioSessionService.login(empleado);
//
//        // Verifica si el inicio de sesión fue exitoso y devuelve la respuesta correspondiente.
//        if (response.get("message").equals("Inicio de sesión exitoso")) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }
//
//    /*
//     * Método para manejar las solicitudes GET en el endpoint "/inicio".
//     * Verifica si el usuario está autenticado y redirige según el estado de la sesión.
//     */
//    @GetMapping("/inicio")
//    public ResponseEntity<String> inicioSession(HttpServletRequest request) {
//        // Verificar la sesión del usuario y retornar mensajes de bienvenida o redireccionamiento.
//        if (usuarioEstaAutenticado(request)) {
//            return ResponseEntity.status(HttpStatus.FOUND)
//                 .header("Location", "/salesReport")
//                 .body(null);
//        } else {
//            return ResponseEntity.ok("Bienvenido a la página de inicio de sesión");
//        }
//    }
//
//    /*
//     * Método para verificar si el usuario está autenticado (lógica de ejemplo muy simple).
//     * Puedes implementar lógica más avanzada según tus necesidades de autenticación.
//     */
//    private boolean usuarioEstaAutenticado(HttpServletRequest request) {
//        return request.getSession().getAttribute("usuarioAutenticado") != null;
//    }
    @Autowired
    private InicioSessionService inicioSessionService;
    @Autowired
    private EmpleadoService empleadoService;

    

//    @PostMapping("/api/login")
//    public String login(@RequestBody LoginRequest request) {
//        String usuario = request.getUsuario();
//        String contrasenia = request.getContrasenia();
//
//        if (inicioSessionService.autenticarUsuario(usuario, contrasenia)) {
//            String permisos = inicioSessionService.asignarPermisos(usuario);
//            return "Inicio de sesión exitoso. Permisos: " + permisos;
//            
//        } else {
//            return "Inicio de sesión fallido";
//        }
//    }
    @PostMapping("/api/login")
    public String login(@RequestBody LoginRequest request) {
        String usuario = request.getUsuario();
        String contrasenia = request.getContrasenia();

        if (inicioSessionService.autenticarUsuario(usuario, contrasenia)) {
            String rol = inicioSessionService.asignarPermisos(usuario);
            return "Inicio de sesión exitoso. Rol: " + rol;
        } else {
            return "Inicio de sesión fallido";
        }
    }

    @GetMapping("/api/roles-inicioSession")
    public ResponseEntity<List<String>> obtenerRoles() {
        List<String> roles = empleadoService.obtenerRolesDisponibles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/api/empleado/{usuario}")
    public ResponseEntity<String> obtenerRolPorUsuario(@PathVariable String usuario) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorUsuario(usuario);

        if (empleado != null) {
            String rol = empleado.getRoles().getNombre();
            return ResponseEntity.ok("Rol del usuario " + usuario + ": " + rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/initEmpleados")
    public String inicializarEmpleados() {
        empleadoService.init(); // Llama al método init de EmpleadoService
        return "Empleados inicializados con éxito";
    }

    // Clase auxiliar para el cuerpo de la solicitud de inicio de sesión
    private static class LoginRequest {

        private String usuario;
        private String contrasenia;

        // getters y setters
        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getContrasenia() {
            return contrasenia;
        }

        public void setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
        }
    }
    
}
