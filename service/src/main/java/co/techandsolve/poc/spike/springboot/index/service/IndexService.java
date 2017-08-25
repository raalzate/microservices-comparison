package co.techandsolve.poc.spike.springboot.index.service;

import reactor.core.publisher.Mono;


/**
 * Interfaz que garantiza los comportamientos del servicio.
 *
 * Este contrato se encarga de garantizar basicamente un pequeño mensaje en la pantalla.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 24/08/2017.
 */
public interface IndexService {

    /*obtener resumen de los listandos*/
    Mono<String> summary();
}
