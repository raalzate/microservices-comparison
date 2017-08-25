package co.com.proteccion.todo.task.domain;

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
@Table(name = "task_tag")
public class TaskTag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    @Getter
    @Setter
    private long taskId;

    @Column(name = "tag_id")
    @Getter
    @Setter
    private long tagId;
}
