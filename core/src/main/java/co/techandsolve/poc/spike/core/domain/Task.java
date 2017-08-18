package co.techandsolve.poc.spike.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


public class Task {

    @Getter @Setter
    private Integer id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private Date date;
    @Getter @Setter
    private List<String> tags;
    @Getter @Setter
    private boolean isDone;

    public Task(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Task(){}

}
