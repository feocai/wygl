import java.math.BigDecimal;

public class demo {

    public static void main(String[] args) {
        BigDecimal first = new BigDecimal("7");
        BigDecimal second = new BigDecimal("2");

//        System.out.println("a + b = " + first.add(second));
//        System.out.println("a - b = " + first.subtract(second));
//        System.out.println("a * b = " + first.multiply(second));
        System.out.println("a / b = " + first.divide(second));
    }
}
