package com.github.munerf.modelmapperspringbootstarter;

import javax.persistence.Id;
import java.util.List;

public class CDto {

    private Long id;

    private String name;

  //  @Id
    private List<Long> as;

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
}
