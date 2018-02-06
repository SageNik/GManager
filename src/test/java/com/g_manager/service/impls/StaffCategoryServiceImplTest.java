package com.g_manager.service.impls;

import com.g_manager.entity.StaffCategory;
import com.g_manager.exception.StaffCategoryException;
import com.g_manager.repository.mysql.MySqlStaffCategoryRepository;
import com.g_manager.service.StaffCategoryService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikolenko Oleg on 03.02.2018.
 */
@RunWith(SpringRunner.class)
public class StaffCategoryServiceImplTest {

    @TestConfiguration
    static class StaffCategoryServiceImplTestContextConfiguration{
        @Bean
        StaffCategoryService staffCategoryService(){return new StaffCategoryServiceImpl();}
    }
    @Autowired
    private StaffCategoryService staffCategoryService;
    @MockBean
    private MySqlStaffCategoryRepository mySqlStaffCategoryRepository;

    public void setUp() {
        StaffCategory staffCategory = new StaffCategory();
        staffCategory.setCategory("categoryOne");
        StaffCategory staffCategoryTwo = new StaffCategory();
        staffCategoryTwo.setCategory("categoryTwo");
        StaffCategory staffCategoryThree = new StaffCategory();
        staffCategoryThree.setCategory("categoryThree");
        List<StaffCategory> allStaffCategories = new ArrayList<>();
        allStaffCategories.add(staffCategory);
        allStaffCategories.add(staffCategoryTwo);
        allStaffCategories.add(staffCategoryThree);
        Mockito.when(mySqlStaffCategoryRepository.findAll()).thenReturn(allStaffCategories);
    }

    @Test
    public void whenFindAll_thenReturnListStaffCategory() throws StaffCategoryException{
        setUp();
        List<StaffCategory> found = staffCategoryService.findAllNotEmpty();

        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(1).getCategory()).isEqualTo("categoryTwo");
    }

    @Test(expected = StaffCategoryException.class)
    public void whenNotFindAny_thenThrowStaffCategoryException()throws StaffCategoryException {
        Mockito.when(mySqlStaffCategoryRepository.findAll()).thenReturn(Lists.emptyList());

        List<StaffCategory> found = staffCategoryService.findAllNotEmpty();
    }
}
