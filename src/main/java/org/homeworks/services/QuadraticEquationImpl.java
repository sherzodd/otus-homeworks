package org.homeworks.services;

import org.homeworks.interfaces.QuadraticEquation;

public class QuadraticEquationImpl implements QuadraticEquation {

    @Override
    public double[] solve(double a, double b, double c) {
        double epsilon = 1e-9;

        if (Math.abs(a) < epsilon) {
            throw new IllegalArgumentException("Coefficient 'a' must not be zero");
        }

        double discriminant = b * b - 4 * a * c;

        if (discriminant < -epsilon) {
            return new double[0]; // No real roots
        } else if (Math.abs(discriminant) < epsilon) {
            double root = -b / (2 * a);
            return new double[]{root}; // One real root
        } else {
            double sqrt = Math.sqrt(discriminant);
            double root1 = (-b + sqrt) / (2 * a);
            double root2 = (-b - sqrt) / (2 * a);
            return new double[]{root1, root2}; // Two real roots
        }
    }
}
