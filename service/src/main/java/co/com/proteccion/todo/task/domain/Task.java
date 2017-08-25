package co.com.proteccion.todo.task.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * En esta entidad se hace uso de la libreria lombok.
 *
 * Nota: Tener en cuenta el plugin para su IDE dado que el IDE puede arrojar errores de anotacion.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 22/08/2017.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private boolean isDone;

    @Getter
    @Setter
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "TaskTag",
            joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    private List<Tag> tags;

    public Task() {
    }

    public Task(Long id, String name) {
        this.name = name;
        this.id = id;
    }

}
