package com.hhi.ees.platform.repository;

import com.hhi.ees.platform.Application;
import com.hhi.ees.platform.Entities.QueryEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class QueryRepositoryTest {

    @Autowired
    QueryRepository queryRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSaveOne() throws Exception {
        QueryEntity queryEntity = new QueryEntity();
        queryEntity.setDateQuery(new Date());
        queryEntity.setQuery("SELECT * FROM table");
        queryRepository.save(queryEntity);
        assertEquals(queryRepository.count(), 1);
    }

}