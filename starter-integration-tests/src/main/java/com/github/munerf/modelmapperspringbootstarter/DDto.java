package com.github.munerf.modelmapperspringbootstarter;

import java.util.List;

import javax.persistence.Id;

public class DDto {

    private Long id;

    private String name;

    private List<Long> as;

    private List<Long> bs;

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

    public List<Long> getAs() {
        return as;
    }

    public void setAs(List<Long> as) {
        this.as = as;
    }

    public List<Long> getBs() {
        return bs;
    }

    public void setBs(List<Long> bs) {
        this.bs = bs;
    }
}
