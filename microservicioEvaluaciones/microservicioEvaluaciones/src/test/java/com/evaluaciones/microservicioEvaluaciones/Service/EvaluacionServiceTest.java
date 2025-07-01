package com.evaluaciones.microservicioEvaluaciones.Service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.evaluaciones.microservicioEvaluaciones.Model.Evaluacion;
import com.evaluaciones.microservicioEvaluaciones.Repository.EvaluacionRepository;

public class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @InjectMocks
    private EvaluacionService evaluacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void testFindAll() {
        // Preparar datos simulados
        Evaluacion eval1 = new Evaluacion();
        Evaluacion eval2 = new Evaluacion();
        List<Evaluacion> evaluacionesSimuladas = Arrays.asList(eval1, eval2);

        // Configurar comportamiento simulado del repositorio
        when(evaluacionRepository.findAll()).thenReturn(evaluacionesSimuladas);

        // Ejecutar método a testear
        List<Evaluacion> resultado = evaluacionService.obtenerTodas();

        // Verificar resultados
        assertEquals(2, resultado.size(), "El tamaño de la lista debe ser 2");
        assertEquals(evaluacionesSimuladas, resultado, "La lista debe ser la misma que la simulada");
    }
}
