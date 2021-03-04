package ua.axiom.apply;

import ua.axiom.apply.model.PriorityAwareSubscriberModel;

import java.util.List;

public class BackToStockServiceImp implements BackToStockService {
    private final static Integer HIGH_PRIORITY = 1;
    private final static Integer AVERAGE_PRIORITY = 2;

    private final static Integer SENIOR_AGE_THRESHOLD = 70;

    //  nothing was said about Spring
    private final PriorityAwareSubscriberModel<Product, User> subscriberModel = new PriorityAwareSubscriberModel<>();

    public BackToStockServiceImp() {
    }

    @Override
    public void subscribe(User user, Product product) {
        Integer priority = AVERAGE_PRIORITY;

        if(user.isPremium()) {
            priority = HIGH_PRIORITY;
        }

        if(user.getAge() > SENIOR_AGE_THRESHOLD && product.getCategory() == ProductCategory.MEDICAL) {
            priority = HIGH_PRIORITY;
        }

        //  Doc says "also some users may have priority for particular product categories", but User class has no sign of it

        subscriberModel.addSubscriber(product, user, priority);

    }

    @Override
    public List<User> subscribedUsers(Product product) {
        return subscriberModel.getOrderedSubscribers(product);
    }
}
