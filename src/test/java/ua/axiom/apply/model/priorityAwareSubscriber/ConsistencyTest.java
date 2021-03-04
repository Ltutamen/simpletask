package ua.axiom.apply.model.priorityAwareSubscriber;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.axiom.apply.Product;
import ua.axiom.apply.ProductCategory;
import ua.axiom.apply.User;
import ua.axiom.apply.model.PriorityAwareSubscriberModel;

import java.util.Collection;
import java.util.List;

/**
 * Tests that subscriptions dont get lost
 */
public class ConsistencyTest {
    private final static int PRIORITY = 1;

    private final Product product1 = new Product("test_product_1", ProductCategory.BOOKS);
    private final Product product2 = new Product("test_product_2", ProductCategory.BOOKS);

    private final User user1 = new User("First User", false, 22);
    private final User user2 = new User("Second User", false, 22);
    private final User user3 = new User("Third User", false, 22);

    private PriorityAwareSubscriberModel<Product, User> model;

    @Before
    public void initModel() {
        model = new PriorityAwareSubscriberModel<>();
    }

    @Test
    public void testSingleSubscriptionNotLost() {
        model.addSubscriber(product1, user1, PRIORITY);

        List<User> subscribed = model.getOrderedSubscribers(product1);

        Assert.assertEquals(1, subscribed.size());

        Assert.assertEquals(user1, subscribed.iterator().next());
    }

    @Test
    public void differentProductsSubscriptionTest() {
        model.addSubscriber(product1, user1, PRIORITY);
        model.addSubscriber(product2, user1, PRIORITY);

        List<User> product1Subs = model.getOrderedSubscribers(product1);
        Assert.assertEquals(1, product1Subs.size());

        List<User> product2Usbs = model.getOrderedSubscribers(product2);
        Assert.assertEquals(1, product2Usbs.size());
    }

    @Test
    public void fifoOrderTest() {
        model.addSubscriber(product1, user1, PRIORITY);
        model.addSubscriber(product1, user2, PRIORITY);
        model.addSubscriber(product1, user3, PRIORITY);

        List<User> subscribed = model.getOrderedSubscribers(product1);

        Assert.assertEquals(3, subscribed.size());

        User testUser1 = subscribed.get(0);
        Assert.assertEquals(user1, testUser1);

        User testUser2 = subscribed.get(1);
        Assert.assertEquals(user2, testUser2);

        User testUser3 = subscribed.get(2);
        Assert.assertEquals(user3, testUser3);
    }
}
