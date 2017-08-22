package co.techandsolve.poc.spike.springboot.task.domine;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 22/08/2017.
 */
@Entity
@Table(name = "task_tag")
public class TaskTag  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    @Getter @Setter
    private long taskId;

    @Column(name = "tag_id")
    @Getter @Setter
    private long tagId;
}
