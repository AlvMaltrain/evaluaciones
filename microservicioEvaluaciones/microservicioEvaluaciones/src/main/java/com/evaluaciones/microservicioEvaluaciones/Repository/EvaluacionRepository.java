package com.evaluaciones.microservicioEvaluaciones.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByCursoId(Long cursoId);

}


