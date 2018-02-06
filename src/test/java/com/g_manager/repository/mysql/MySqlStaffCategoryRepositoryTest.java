package com.g_manager.repository.mysql;

import com.g_manager.entity.ClientCategory;
import com.g_manager.entity.StaffCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleh on 03.02.2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MySqlStaffCategoryRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    MySqlStaffCategoryRepository mySqlStaffCategoryRepository;

    public void setUp() {
        StaffCategory staffCategory = new StaffCategory();
        staffCategory.setCategory("categoryOne");
        StaffCategory staffCategoryTwo = new StaffCategory();
        staffCategoryTwo.setCategory("categoryTwo");
        StaffCategory staffCategoryThree = new StaffCategory();
        staffCategoryThree.setCategory("categoryThree");
        testEntityManager.persist(staffCategory);
        testEntityManager.persist(staffCategoryTwo);
        testEntityManager.persist(staffCategoryThree);
        testEntityManager.flush();
    }

    @Test
    public void whenFindAll_thenReturnList(){
        setUp();
        List<StaffCategory> found = mySqlStaffCategoryRepository.findAll();

        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(1).getCategory()).isEqualTo("categoryTwo");
    }

    @Test
    public void whenNotFindAny_thenReturnEmptyList(){
        List<StaffCategory> found = mySqlStaffCategoryRepository.findAll();

        assertThat(found).isEmpty();
    }
}
