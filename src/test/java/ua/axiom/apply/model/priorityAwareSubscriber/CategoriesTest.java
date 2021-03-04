package ua.axiom.apply.model.priorityAwareSubscriber;

import org.junit.Test;
import ua.axiom.apply.Product;
import ua.axiom.apply.User;
import ua.axiom.apply.model.PriorityAwareSubscriberModel;

/**
 * Tests that premium user subscription goes first
 */
public class PremiumUserTest {
    private final PriorityAwareSubscriberModel<Product, User> model = new PriorityAwareSubscriberModel<>();
    private final User premiumUser = new User("Puser Name", true, 22);
    private final User averageUser = new User("Auser Name", false, 22);



    @Test
    public void twoCategoriesTest
}
