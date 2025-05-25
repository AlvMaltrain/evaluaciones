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
    public List<Evaluacion> obtenerTodasLasEvaluaciones(){
        return evaluacionService.obtenerTodas();
    } 

    /**
     * Obtener una evaluación por su ID.
     * Método HTTP: GET
     * Endpoint: /api/v1/evaluaciones/{id}
     * @param id ID de la evaluación
     * @return La evaluación encontrada o error 404 si es que no existe
     */
    @GetMapping("/{id}")
    public Evaluacion obtenerEvaluacionPorId(@PathVariable Long id) {
        return evaluacionService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Evaluacion no encontrada con ID" + id));
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
public ResponseEntity<Evaluacion> actualizarEvaluacion(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
    Optional<Evaluacion> existente = evaluacionService.obtenerPorId(id);
    if (existente.isPresent()) {
        Evaluacion actual = existente.get();
        actual.setTitulo(evaluacion.getTitulo());
        actual.setDescripcion(evaluacion.getDescripcion());
        actual.setFecha(evaluacion.getFecha());
        actual.setCursoId(evaluacion.getCursoId());

        return ResponseEntity.ok(evaluacionService.guardar(actual));
    } else {
        return ResponseEntity.notFound().build();
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
public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
    Optional<Evaluacion> existente = evaluacionService.obtenerPorId(id);
    if (existente.isPresent()) {
        evaluacionService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    } else {
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}

@GetMapping("/curso/{cursoId}")
public ResponseEntity<List<Evaluacion>> obtenerEvaluacionesPorCursoId(@PathVariable Long cursoId) {
    List<Evaluacion> evaluaciones = evaluacionService.obtenerPorCursoId(cursoId);
    if (evaluaciones.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(evaluaciones);
}

}
