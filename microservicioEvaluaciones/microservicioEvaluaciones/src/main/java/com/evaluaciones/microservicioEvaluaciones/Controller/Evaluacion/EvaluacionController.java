package com.evaluaciones.microservicioEvaluaciones.Controller.Evaluacion;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;
import com.evaluaciones.microservicioEvaluaciones.Service.EvaluacionService;


@RestController
@RequestMapping("api/v1/evaluaciones") //endpoint
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;
        @GetMapping("/ping")
        public String probar() {
            return "Microservicio Evaluaciones Activo";
    }

    /**
     * Obtener todas las evaluaciones existentes.
     * Método HTTP: GET
     * Endpoint: /api/v1/evaluaciones
     * @return Lista de todas las evaluaciones
     */
    @GetMapping
public ResponseEntity<?> obtenerTodasLasEvaluaciones() {
    try {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodas();
        return ResponseEntity.ok(evaluaciones);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al obtener las evaluaciones: " + e.getMessage());
    }
}

    /**
     * Obtener una evaluación por su ID.
     * Método HTTP: GET
     * Endpoint: /api/v1/evaluaciones/{id}
     * @param id ID de la evaluación
     * @return La evaluación encontrada o error 404 si es que no existe
     */
    @GetMapping("/{id}")
public ResponseEntity<?> obtenerEvaluacionPorId(@PathVariable Long id) {
    try {
        Optional<Evaluacion> evaluacion = evaluacionService.obtenerPorId(id);
        if (evaluacion.isPresent()) {
            return ResponseEntity.ok(evaluacion.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Evaluación no encontrada con ID: " + id);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al obtener evaluación: " + e.getMessage());
    }
}

    /**
     * Crear una nueva evaluación.
     * Método HTTP: POST
     * Endpoint: /api/v1/evaluaciones
     * @param evaluacion evaluacion Evaluacion a guardar
     * @return Evaluación creada con código 201
     */
    @PostMapping
    public ResponseEntity<Evaluacion> crearEvaluacion(@RequestBody Evaluacion evaluacion){
        Evaluacion nueva = evaluacionService.guardar(evaluacion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }
    
    /**
     * Actualizar una evalución existente por ID.
     * Método HTTP: PUT
     * Endpoint: /api/v1/evaluaciones/{id}
     * @param id ID de la evaluación a actualizar
     * @param evaluacion Datos nuevos de la evaluación
     * @return Evaluación actualizada o error 404 si es que no existe
     */
 @PutMapping("/{id}")
public ResponseEntity<?> actualizarEvaluacion(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
    try {
        Optional<Evaluacion> existente = evaluacionService.obtenerPorId(id);
        if (existente.isPresent()) {
            Evaluacion actual = existente.get();
            actual.setTitulo(evaluacion.getTitulo());
            actual.setDescripcion(evaluacion.getDescripcion());
            actual.setFecha(evaluacion.getFecha());
            actual.setCursoId(evaluacion.getCursoId());

            Evaluacion actualizada = evaluacionService.guardar(actual);
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Evaluación no encontrada con ID: " + id);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al actualizar evaluación: " + e.getMessage());
    }
}
    /**
     * Eliminar una evaluación por ID.
     * Método HTTP: DELETE
     * Endpoint: /api/v1/evaluaciones/{id}
     * @param id ID de la evaluación a eliminar
     * @return Código 204 si se eliminó o 404 si es que no existe
     */
@DeleteMapping("/{id}")
public ResponseEntity<?> eliminarEvaluacion(@PathVariable Long id) {
    try {
        Optional<Evaluacion> existente = evaluacionService.obtenerPorId(id);
        if (existente.isPresent()) {
            evaluacionService.eliminar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Evaluación no encontrada con ID: " + id);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al eliminar la evaluación: " + e.getMessage());
    }
}

@GetMapping("/curso/{cursoId}")
public ResponseEntity<?> obtenerEvaluacionesPorCursoId(@PathVariable Long cursoId) {
    try {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerPorCursoId(cursoId);
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No se encontraron evaluaciones para el curso con ID: " + cursoId);
        }
        return ResponseEntity.ok(evaluaciones);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al obtener evaluaciones por curso: " + e.getMessage());
    }
}
}
