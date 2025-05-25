package com.evaluaciones.microservicioEvaluaciones.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;
import com.evaluaciones.microservicioEvaluaciones.Repository.EvaluacionRepository;

@Service
public class EvaluacionService {

        @Autowired
        private EvaluacionRepository evaluacionRepository;

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
}