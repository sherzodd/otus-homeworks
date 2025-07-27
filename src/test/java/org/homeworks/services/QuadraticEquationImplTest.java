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
}