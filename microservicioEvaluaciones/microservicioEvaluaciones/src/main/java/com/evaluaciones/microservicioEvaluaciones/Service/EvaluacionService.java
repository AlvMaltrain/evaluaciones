package com.evaluaciones.microservicioEvaluaciones.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.evaluaciones.microservicioEvaluaciones.DTO.UsuarioDTO;
import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;
import com.evaluaciones.microservicioEvaluaciones.Repository.EvaluacionRepository;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final UsuarioClientService usuarioClientService;

    public EvaluacionService(EvaluacionRepository evaluacionRepository, UsuarioClientService usuarioClientService) {
        this.evaluacionRepository = evaluacionRepository;
        this.usuarioClientService = usuarioClientService;
    }

    //Guardar o actualizar una evaluacion
    public Evaluacion guardar(Evaluacion evaluacion){
        return evaluacionRepository.save(evaluacion);
    }

    //Obtener todas las evaluaciones
    public List<Evaluacion> obtenerTodas() {
        return evaluacionRepository.findAll();
    }

    //Obtener una evaluacion por ID
    public Optional<Evaluacion> obtenerPorId(Long id) {
        return evaluacionRepository.findById(id);
    }

    //Eliminar una evaluacion por ID
    public void eliminar(Long id) {
        evaluacionRepository.deleteById(id);
    }

    //Buscar por cursoID
    public List<Evaluacion> obtenerPorCursoId(Long cursoId) {
        return evaluacionRepository.findByCursoId(cursoId);
    }

    //Uso del cliente REST para obtener usuario
    public void ejemploConsultaUsuario(Long idUsuario) {
        UsuarioDTO usuario = usuarioClientService.obtenerUsuarioPorId(idUsuario);

        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getNombre());
            
        } else {
            System.out.println("Usuario no encontrado.");
        
        }
    }
    
    public UsuarioDTO obtenerUsuarioDesdeUsuarios(Long idUsuario) {
    return usuarioClientService.obtenerUsuarioPorId(idUsuario);
}
}
