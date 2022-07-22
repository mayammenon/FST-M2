package examples;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class CalculatorTest {
    Calculator calc;

    @BeforeAll
    public void setUp(){
        calc = new Calculator();
    }

    @Test
    public void additionTest(){
        int result = calc.add(5,10);

        Assert.assertEquals(15, result);
    }

    @Test


}
