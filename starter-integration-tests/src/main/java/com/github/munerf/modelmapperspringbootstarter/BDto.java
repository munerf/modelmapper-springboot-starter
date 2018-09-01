package com.github.munerf.modelmapperspringbootstarter;

import javax.persistence.Id;

public class BDto {

    private Long id;

    private String name;

    @Id
    private Long A;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getA() {
        return A;
    }

    public void setA(Long a) {
        A = a;
    }
}
