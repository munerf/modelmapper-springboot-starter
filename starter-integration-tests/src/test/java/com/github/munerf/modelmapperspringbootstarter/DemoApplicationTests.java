package com.github.munerf.modelmapperspringbootstarter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    ApplicationContext context;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void contextLoads() {

        assertThat((context.containsBean("modelMapper"))).isTrue();

        BDto bDto = new BDto();
        bDto.setName("bDto");
        bDto.setA( new Long(1));
        B map = modelMapper.map(bDto, B.class);

        assertThat(map.getName()).isEqualTo("bDto");
        assertThat(map.getA().name).isEqualTo("a1");
    }

    @Test
    public void simpleEntityWithOneToOneIsMapped() {

        BDto bDto = new BDto();
        bDto.setName("bDto");
        bDto.setA( new Long(1));
        B map = modelMapper.map(bDto, B.class);

        assertThat(map.getName()).isEqualTo("bDto");
        assertThat(map.getA().name).isEqualTo("a1");
    }

    @Test
    public void nullsAreSkipped() {

        BDto bDto = new BDto();
        bDto.setName("bDto");
        B map = modelMapper.map(bDto, B.class);

        assertThat(map.getName()).isEqualTo("bDto");
        assertThat(map.getA()).isNull();

    }

}
