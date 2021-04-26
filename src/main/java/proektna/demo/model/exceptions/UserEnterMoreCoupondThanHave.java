package proektna.demo.model.exceptions;

public class UserEnterMoreCoupondThanHave extends RuntimeException {

    public UserEnterMoreCoupondThanHave() {
        super("user enter more coupons than he have");
    }

}
