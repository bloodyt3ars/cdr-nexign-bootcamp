package ru.homework.cdrtest;

import org.testng.annotations.Test;
import ru.homework.cdrtest.component.HighPerformanceRatingServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallCostCalculatorTest {
    @Test
    public void testCalculateUnlimited300CostWithFreeMinutes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int freeMinutes = 10;
        int duration = 5;
        double expectedCost = 0;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculateUnlimited300Cost", int.class, int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, freeMinutes, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }
    @Test
    public void testCalculateUnlimited300CostWithExtraMinutes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int freeMinutes = 10;
        int duration = 20;
        double expectedCost = 10;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculateUnlimited300Cost", int.class, int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, freeMinutes, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testCalculateUnlimited300CostWithoutFreeMinutes() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int freeMinutes = 0;
        int duration = 20;
        double expectedCost = 20;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculateUnlimited300Cost", int.class, int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, freeMinutes, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testCalculatePerMinuteCost() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int duration = 30;
        double expectedCost = 30 * 1.5;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculatePerMinuteCost", int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testCalculateNormalCostWithFreeMinutes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int freeMinutes = 10;
        int duration = 5;
        double expectedCost = 5*0.5;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculateNormalCost", int.class, int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, freeMinutes, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testCalculateNormalCostWithExtraMinutes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int freeMinutes = 10;
        int duration = 30;
        double expectedCost = 10*0.5+20*1.5;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculateNormalCost", int.class, int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, freeMinutes, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testCalculateNormalCostWithoutFreeMinutes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HighPerformanceRatingServer calculator = new HighPerformanceRatingServer();
        int freeMinutes = 0;
        int duration = 30;
        double expectedCost = 30 * 1.5;
        Method method = HighPerformanceRatingServer.class.getDeclaredMethod(
                "calculateNormalCost", int.class, int.class);
        method.setAccessible(true);
        double actualCost = (double) method.invoke(calculator, freeMinutes, duration);
        assertEquals(expectedCost, actualCost, 0.001);
    }
}
