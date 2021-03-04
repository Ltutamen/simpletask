package ua.axiom.apply.service;

import org.junit.Before;
import org.junit.Test;
import ua.axiom.apply.Product;
import ua.axiom.apply.ProductCategory;
import ua.axiom.apply.User;
import ua.axiom.apply.model.PriorityAwareSubscriberModel;

import java.util.List;

/**
 * Tests that premium user subscription goes first
 */
public class PremiumUserTest {
    private PriorityAwareSubscriberModel<Product, User> model;

    private final Product product = new Product("book_4342", ProductCategory.BOOKS);
    private final User premiumUser = new User("Puser Name", true, 22);
    private final User averageUser = new User("Auser Name", false, 22);

    @Before
    public void initModel() {
        model = new PriorityAwareSubscriberModel<>();
    }


    @Test
    public void twoCategoriesTest1() {
        model.addSubscriber(product, premiumUser, 1);
        model.addSubscriber(product, averageUser, 1);

        List<User> orderedSubscribers = model.getOrderedSubscribers(product);

    }
}
