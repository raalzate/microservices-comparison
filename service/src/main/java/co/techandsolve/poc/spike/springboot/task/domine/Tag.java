package co.techandsolve.poc.spike.springboot.task.domine;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * En esta entidad se hace uso de la libreria lombok.
 *
 * Nota: Tener en cuenta el plugin para su IDE dado que el IDE puede arrojar errores de anotacion.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }
}
