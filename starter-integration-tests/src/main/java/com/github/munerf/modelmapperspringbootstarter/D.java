package com.github.munerf.modelmapperspringbootstarter;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class D {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany
    private List<A> as;

    @OneToMany
    private List<B> bs;

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

    public List<A> getAs() {
        return as;
    }

    public void setAs(List<A> as) {
        this.as = as;
    }

    public List<B> getBs() {
        return bs;
    }

    public void setBs(List<B> bs) {
        this.bs = bs;
    }
}
