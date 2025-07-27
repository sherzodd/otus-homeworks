package org.homeworks.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuadraticEquationImplTest {

    @Test
    void testNoRealRoots() {
        QuadraticEquationImpl solver = new QuadraticEquationImpl();
        double[] result = solver.solve(1.0, 0.0, 1.0); // x^2 + 1 = 0

        assertNotNull(result);
        assertEquals(0, result.length, "Expected no real roots");
    }

    @Test
    void testTwoDistinctRoots() {
        var solver = new QuadraticEquationImpl();
        double[] result = solver.solve(1.0, 0.0, -1.0); // x² - 1 = 0

        assertEquals(2, result.length, "Expected two roots");
        assertTrue(contains(result, 1.0));
        assertTrue(contains(result, -1.0));
    }

    @Test
    void testOneRepeatedRoot() {
        var solver = new QuadraticEquationImpl();
        double[] result = solver.solve(1.0, 2.0, 1.0); // (x + 1)² = 0

        assertEquals(1, result.length, "Expected one root");
        assertEquals(-1.0, result[0], 1e-9);
    }

    @Test
    void testCoefficientACannotBeZero() {
        var solver = new QuadraticEquationImpl();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> solver.solve(0.0, 1.0, 1.0)
        );

        assertTrue(exception.getMessage().contains("Coefficient 'a'"));
    }

    @Test
    void testNearZeroDiscriminant() {
        var solver = new QuadraticEquationImpl();

        double c = 1.0 - 3e-9; // D = 4 - 4*(1 - 3e-9) = 12e-9 > 1e-9

        double[] result = solver.solve(1.0, 2.0, c);

        assertEquals(2, result.length, "Expected two distinct roots for near-zero discriminant");

        double expectedRoot = -1.0;
        assertTrue(Math.abs(result[0] - expectedRoot) < 1e-4,
                "First root should be close to -1");
        assertTrue(Math.abs(result[1] - expectedRoot) < 1e-4,
                "Second root should be close to -1");
        assertTrue(Math.abs(result[0] - result[1]) > 1e-9,
                "Roots should be distinct");
    }

    @Test
    void testSpecialDoubleValues() {
        var solver = new QuadraticEquationImpl();

        double[] invalidValues = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};

        for (double a : invalidValues) {
            for (double b : invalidValues) {
                for (double c : invalidValues) {
                    assertThrows(IllegalArgumentException.class, () ->
                                    solver.solve(a, b, c),
                            "Should throw exception for invalid input: a=" + a + ", b=" + b + ", c=" + c
                    );
                }
            }
        }
    }

    private boolean contains(double[] array, double value) {
        double epsilon = 1e-9;
        for (double v : array) {
            if (Math.abs(v - value) < epsilon) return true;
        }
        return false;
    }

}