package co.techandsolve.poc.spike.springboot.task.domine;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 22/08/2017.
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
