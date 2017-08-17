package co.techandsolve.poc.spike.core.domain;

public class Thing {

    private Integer id;
    private String name;

    public Thing(String name) {
        this.name = name;
    }

    Thing() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
