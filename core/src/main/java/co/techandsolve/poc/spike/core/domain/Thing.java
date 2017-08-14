package co.techandsolve.poc.spike.core.domain;

import lombok.Getter;
import lombok.Setter;


public class Thing {
    @Getter @Setter
    private Integer id;
    @Getter @Setter
    private String name;

    public Thing(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Thing(){

    }

}
