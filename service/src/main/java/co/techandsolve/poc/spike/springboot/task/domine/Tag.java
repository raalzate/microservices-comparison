package co.techandsolve.poc.spike.springboot.task.domine;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 22/08/2017.
 */
@Entity
@Table(name = "tag")
public class Tag  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Getter @Setter
    private String name;

    public Tag(){

    }
    public Tag(String name){
        this.name = name;
    }
}
