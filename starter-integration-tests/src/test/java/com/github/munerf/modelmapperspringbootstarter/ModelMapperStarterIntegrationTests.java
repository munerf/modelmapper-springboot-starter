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
import java.util.List;

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

    @Autowired
    ARepository aRepository;

    @Autowired
    BRepository bRepository;


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
        assertThat(b.getA().getName()).isEqualTo("a1");
    }

    @Test
    public void simpleEntityWithOneToOneIsMapped() {

        BDto bDto = new BDto();
        bDto.setName("bDto");
        bDto.setA(1L);
        B b = modelMapper.map(bDto, B.class);

        assertThat(b.getName()).isEqualTo("bDto");
        assertThat(b.getA().getName()).isEqualTo("a1");
    }

    @Test
    public void simpleDtoWithOneToOneIsMapped() {

        B b = new B();
        b.setName("bDto");

        A a = aRepository.findById(1L).get();
        b.setA(a);
        BDto bDto = modelMapper.map(b, BDto.class);

        assertThat(bDto.getName()).isEqualTo("bDto");
        assertThat(bDto.getA()).isEqualTo(1L);
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

        Long[] as = new Long[]{1L, 2L};

        CDto cDto = new CDto();
        cDto.setName("cDto");
        cDto.setAs( Arrays.asList(as) );
        C c = modelMapper.map(cDto, C.class);

        assertThat(c.getName()).isEqualTo("cDto");
        assertThat(c.getAs().get(0).getName()).isEqualTo("a1");
        assertThat(c.getAs().get(1).getName()).isEqualTo("a2");

    }

    @Test
    public void simpleDtoWithManyToOneIsMapped() {

        C c = new C();
        c.setName("c1");

        List<A> as = aRepository.findAll();
        c.setAs(as);

        CDto cDto = modelMapper.map(c, CDto.class);

        assertThat(cDto.getName()).isEqualTo("c1");
        assertThat(cDto.getAs().get(0)).isEqualTo(1L);
        assertThat(cDto.getAs().get(1)).isEqualTo(2L);

    }

    @Test
    public void complexDtoWithTwoManyToOneIsMapped() {

        D d = new D();
        d.setName("d1");

        List<A> as = aRepository.findAll();
        d.setAs(as);

        List<B> bs = bRepository.findAll();
        d.setBs(bs);

        DDto dDto = modelMapper.map(d, DDto.class);

        assertThat(dDto.getName()).isEqualTo("d1");
        assertThat(dDto.getAs().get(0)).isEqualTo(1L);
        assertThat(dDto.getAs().get(1)).isEqualTo(2L);

        assertThat(dDto.getBs().get(0)).isEqualTo(1L);
        assertThat(dDto.getBs().get(1)).isEqualTo(2L);

    }

    @Test
    public void complexEntityWithTwoManyToOneIsMapped() {

        Long[] as = new Long[]{1L, 2L};
        Long[] bs = new Long[]{1L, 2L};

        DDto dDto = new DDto();
        dDto.setName("dDto");
        dDto.setAs( Arrays.asList(as) );
        dDto.setBs( Arrays.asList(bs) );

        D d = modelMapper.map(dDto, D.class);

        assertThat(d.getName()).isEqualTo("dDto");
        assertThat(d.getAs().get(0).getName()).isEqualTo("a1");
        assertThat(d.getAs().get(1).getName()).isEqualTo("a2");

        assertThat(d.getBs().get(0).getName()).isEqualTo("b1");
        assertThat(d.getBs().get(1).getName()).isEqualTo("b2");

    }

}
