package com.github.munerf.modelmapperspringbootstarter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelMapperStarterIntegrationTests {

    @Autowired
    ApplicationContext context;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ModelMapper modelMapper;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void contextLoads() {

        assertThat((context.containsBean("modelMapper"))).isTrue();

        BDto bDto = new BDto();
        bDto.setName("bDto");
        bDto.setA(1L);
        B b = modelMapper.map(bDto, B.class);

        assertThat(b.getName()).isEqualTo("bDto");
        assertThat(b.getA().name).isEqualTo("a1");
    }

    @Test
    public void simpleEntityWithOneToOneIsMapped() {

        BDto bDto = new BDto();
        bDto.setName("bDto");
        bDto.setA(1L);
        B b = modelMapper.map(bDto, B.class);

        assertThat(b.getName()).isEqualTo("bDto");
        assertThat(b.getA().name).isEqualTo("a1");
    }

    @Test
    public void nullsAreSkipped() {

        BDto bDto = new BDto();
        bDto.setName("bDto");
        B map = modelMapper.map(bDto, B.class);

        assertThat(map.getName()).isEqualTo("bDto");
        assertThat(map.getA()).isNull();

    }

    @Test
    public void simpleEntityWithManyToOneIsMapped() {

        Long[] bs = new Long[]{1L, 2L};

        CDto cDto = new CDto();
        cDto.setName("cDto");
        cDto.setAs( Arrays.asList(bs) );
        C c = modelMapper.map(cDto, C.class);

        assertThat(c.getName()).isEqualTo("cDto");
        assertThat(c.getAs().get(0).name).isEqualTo("a1");
        assertThat(c.getAs().get(1).name).isEqualTo("a2");

    }

}
