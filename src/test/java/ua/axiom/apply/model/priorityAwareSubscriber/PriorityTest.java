package ua.axiom.apply.model.priorityAwareSubscriber;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.axiom.apply.Product;
import ua.axiom.apply.ProductCategory;
import ua.axiom.apply.User;
import ua.axiom.apply.model.PriorityAwareSubscriberModel;

import java.util.List;
import java.util.stream.Collectors;

public class PriorityTest {
    private final static int HIGH_PRIORITY = 1;
    private final static int MEDIUM_PRIORITY = 2;
    private final static int LOW_PRIORITY = 3;


    private PriorityAwareSubscriberModel<Product, User> model;


    private final Product product = new Product("test_product", ProductCategory.DIGITAL);


    private final User lowPrUser1 = new User("test_lowpuser1", false, 22);
    private final User lowPrUser2 = new User("test_lowpuser2", false, 22);
    private final User medPrUser1 = new User("test_medpuser1", false, 22);
    private final User medPrUser2 = new User("test_medpuser2", false, 22);
    private final User highPrUser1 = new User("test_highpuser1", false, 22);
    private final User highPrUser2 = new User("test_highpuser2", false, 22);

    @Before
    public void beforeClass() {
        model = new PriorityAwareSubscriberModel<>();
    }

    @Test
    public void priorityTest() {
        model.addSubscriber(product, lowPrUser1, LOW_PRIORITY);
        model.addSubscriber(product, medPrUser1, MEDIUM_PRIORITY);
        model.addSubscriber(product, highPrUser1, HIGH_PRIORITY);
        model.addSubscriber(product, highPrUser2, HIGH_PRIORITY);
        model.addSubscriber(product, medPrUser2, MEDIUM_PRIORITY);
        model.addSubscriber(product, lowPrUser2, LOW_PRIORITY);

        List<User> orderedSubscribers = model.getOrderedSubscribers(product);

        Assert.assertEquals(6, orderedSubscribers.size());

        List<User> highPriorityUsers = orderedSubscribers.stream().limit(2).collect(Collectors.toList());
        Assert.assertTrue(highPriorityUsers.contains(highPrUser1) && highPriorityUsers.contains(highPrUser2));

        List<User> mediumPriorityList = orderedSubscribers.stream().skip(2).limit(2).collect(Collectors.toList());
        Assert.assertTrue(mediumPriorityList.contains(medPrUser1) && mediumPriorityList.contains(medPrUser2));

        List<User> lowPriorityList = orderedSubscribers.stream().skip(4).limit(2).collect(Collectors.toList());
        Assert.assertTrue(lowPriorityList.contains(lowPrUser1) && lowPriorityList.contains(lowPrUser2));
    }
}
