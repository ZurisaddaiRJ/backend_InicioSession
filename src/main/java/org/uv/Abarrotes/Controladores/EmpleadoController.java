/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.Abarrotes.Controladores;

import java.util.List;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.uv.Abarrotes.DTOs.DTOEmpleadoInfo;
import org.uv.Abarrotes.modelos.Empleado;
import org.uv.Abarrotes.servicio.EmpleadoService;

/**
 *
 * @author yacruz
 */
@Controller
@RequestMapping("api/empleados")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
//    @PreAuthorize("hasAnyAuthority('JEFE', 'GERENTE')")
    public ResponseEntity<org.uv.Abarrotes.DTOs.DTOEmpleadoInfo> crearEmpleadoConEntidades(@RequestBody Empleado nuevoEmpleado) {
        org.uv.Abarrotes.DTOs.DTOEmpleadoInfo empleadoCreado = empleadoService.crearEmpleado(nuevoEmpleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreado);
    }

    //Metodo modificado para crear empleados
//     @PostMapping("/crear")
//    public ResponseEntity<DTOEmpleadoInfo> crearEmpleado(@RequestBody Empleado nuevoEmpleado) {
//        DTOEmpleadoInfo empleadoCreado = empleadoService.crearEmpleado(nuevoEmpleado);
//        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCreado);
//    }
    //----------------
    @GetMapping
    public ResponseEntity<List<DTOEmpleadoInfo>> obtenerEmpleados() {
        List<DTOEmpleadoInfo> empleados = empleadoService.obtenerEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOEmpleadoInfo> obtenerEmpleadoPorId(@PathVariable Long id) {
        DTOEmpleadoInfo empleado = empleadoService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTOEmpleadoInfo> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {
        DTOEmpleadoInfo empleado = empleadoService.actualizarEmpleado(id, empleadoActualizado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo para buscar empleados por nombres y apellidos
    @GetMapping("/buscar")
    public ResponseEntity<List<DTOEmpleadoInfo>> buscarEmpleadosPorNombreYApellidos(
         @RequestParam(value = "nombre", required = false) String nombre,
         @RequestParam(value = "apellidos", required = false) String apellidos) {
        List<DTOEmpleadoInfo> empleados = empleadoService.buscarEmpleadosPorNombreYApellidos(nombre, apellidos);
        return ResponseEntity.ok(empleados);
    }
}
