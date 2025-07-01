package com.evaluaciones.microservicioEvaluaciones.Controller.Evaluacion;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import com.evaluaciones.microservicioEvaluaciones.DTO.UsuarioDTO;
import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;
import com.evaluaciones.microservicioEvaluaciones.Service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("api/v1/evaluaciones") //endpoint
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;
 @Operation(summary="Prueba si el microservicio se encuentra activo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Microservicio Evaluaciones Activo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
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
@Operation(summary="Obtiene una lista de todas las evaluaciones")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida exitosamente"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
@GetMapping("/evaluaciones")
public ResponseEntity<?> obtenerTodasLasEvaluaciones() {
    try {
        List<EntityModel<Evaluacion>> evaluaciones = evaluacionService.obtenerTodas().stream()
            .map(evaluacion -> EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionController.class).obtenerEvaluacionPorId(evaluacion.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withRel("evaluaciones")))
            .toList();

        CollectionModel<EntityModel<Evaluacion>> coleccion = CollectionModel.of(evaluaciones,
            linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withSelfRel());

        return ResponseEntity.ok(coleccion);
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
@Operation(summary = "Obtener evaluación por ID", description = "Retorna una evaluación específica según su ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Evaluación encontrada"),
    @ApiResponse(responseCode = "404", description = "Evaluación no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
@GetMapping("/{id}")
public ResponseEntity<?> obtenerEvaluacionPorId(@PathVariable Long id) {
    try {
        Optional<Evaluacion> evaluacion = evaluacionService.obtenerPorId(id);
        if (evaluacion.isPresent()) {
            EntityModel<Evaluacion> recurso = EntityModel.of(
                evaluacion.get(),
                linkTo(methodOn(EvaluacionController.class).obtenerEvaluacionPorId(id)).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withRel("all") // Link para todas
            );
            return ResponseEntity.ok(recurso);
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
@Operation(summary = "Crear nueva evaluación", description = "Crea una nueva evaluación y devuelve enlaces HATEOAS para acceder a ella y a todas las evaluaciones.")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Evaluación creada exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
@PostMapping
public ResponseEntity<?> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
    try {
        Evaluacion nueva = evaluacionService.guardar(evaluacion);

        // Crear el recurso HATEOAS
        EntityModel<Evaluacion> recurso = EntityModel.of(
            nueva,
            linkTo(methodOn(EvaluacionController.class).obtenerEvaluacionPorId(nueva.getId())).withSelfRel(),
            linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withRel("all")
        );

        return ResponseEntity
            .created(new URI("/api/v1/evaluaciones/" + nueva.getId())) // Encabezado Location
            .body(recurso);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear evaluación: " + e.getMessage());
    }
}
    
    /**
     * Actualizar una evalución existente por ID.
     * Método HTTP: PUT
     * Endpoint: /api/v1/evaluaciones/{id}
     * @param id ID de la evaluación a actualizar
     * @param evaluacion Datos nuevos de la evaluación
     * @return Evaluación actualizada o error 404 si es que no existe
     */
  @Operation(summary = "Actualizar una evaluación existente", description = "Actualiza los datos de una evaluación según su ID e incluye enlaces HATEOAS.")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Evaluación actualizada correctamente"),
    @ApiResponse(responseCode = "404", description = "Evaluación no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
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

            // Crear la entidad HATEOAS
            EntityModel<Evaluacion> recurso = EntityModel.of(
                actualizada,
                linkTo(methodOn(EvaluacionController.class).obtenerEvaluacionPorId(actualizada.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withRel("all")
            );
            
            return ResponseEntity.ok(recurso);
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
            
            // Creamos un modelo para responder con link a todas las evaluaciones
            EntityModel<String> modelo = EntityModel.of(
                "Evaluación eliminada correctamente",
                linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withRel("all")
            );
            
            return ResponseEntity.ok(modelo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Evaluación no encontrada con ID: " + id);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al eliminar la evaluación: " + e.getMessage());
    }
}

@Operation(summary = "Listar evaluaciones por curso", description = "Retorna todas las evaluaciones asociadas a un curso específico")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Evaluaciones obtenidas correctamente"),
    @ApiResponse(responseCode = "204", description = "No se encontraron evaluaciones para el curso"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})

@GetMapping("/curso/{cursoId}")
public ResponseEntity<?> obtenerEvaluacionesPorCursoId(@PathVariable Long cursoId) {
    try {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerPorCursoId(cursoId);
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No se encontraron evaluaciones para el curso con ID: " + cursoId);
        }

        
        List<EntityModel<Evaluacion>> modelos = evaluaciones.stream()
            .map(eval -> EntityModel.of(eval,
                linkTo(methodOn(EvaluacionController.class).obtenerEvaluacionPorId(eval.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).obtenerTodasLasEvaluaciones()).withRel("all")
            ))
            .toList();

        return ResponseEntity.ok(modelos);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al obtener evaluaciones por curso: " + e.getMessage());
    }
}

@GetMapping("/usuario/{idUsuario}")
public ResponseEntity<?> obtenerUsuarioDesdeMicroservicio(@PathVariable Long idUsuario) {
    try {
        UsuarioDTO usuario = evaluacionService.obtenerUsuarioDesdeUsuarios(idUsuario);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Usuario no encontrado con ID: " + idUsuario);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error al obtener usuario: " + e.getMessage());
    }
}
    
}




