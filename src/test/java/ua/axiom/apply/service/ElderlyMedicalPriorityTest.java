package ua.axiom.apply.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.axiom.apply.*;

import java.util.List;

import static ua.axiom.apply.BackToStockServiceImp.SENIOR_AGE_THRESHOLD;

public class ElderlyMedicalPriorityTest {
    private BackToStockService service;

    private Product medicalProduct = new Product("test_medical", ProductCategory.MEDICAL);
    private Product digitalProduct = new Product("test_digital", ProductCategory.DIGITAL);

    private User elderlyUser1 = new User("test_eldely1", false, SENIOR_AGE_THRESHOLD);
    private User notElderlyUser = new User("test_user", false, SENIOR_AGE_THRESHOLD - 1);

    @Before
    public void initModel() {
        service = new BackToStockServiceImp();
    }

    @Test
    public void elderlyMedicalPriorityTest() {
        //  young put first
        service.subscribe(notElderlyUser, medicalProduct);
        service.subscribe(elderlyUser1, medicalProduct);

        List<User> subscibers = service.subscribedUsers(medicalProduct);

        //  elderly first
        Assert.assertEquals(elderlyUser1, subscibers.get(0));
        Assert.assertEquals(notElderlyUser, subscibers.get(1));

    }

    @Test
    public void elderlyNotMedicalPriorityTest() {
        //  young put first
        service.subscribe(notElderlyUser, digitalProduct);
        service.subscribe(elderlyUser1, digitalProduct);

        List<User> subscibers = service.subscribedUsers(digitalProduct);

        //  elderly FIFO
        Assert.assertEquals(notElderlyUser, subscibers.get(0));
        Assert.assertEquals(elderlyUser1, subscibers.get(1));
    }

}
