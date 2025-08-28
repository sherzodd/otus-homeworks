package org.homeworks.services;

import org.homeworks.interfaces.QuadraticEquation;

public class QuadraticEquationImpl implements QuadraticEquation {

    @Override
    public double[] solve(double a, double b, double c) {
        double epsilon = 1e-9;

        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) ||
                Double.isInfinite(a) || Double.isInfinite(b) || Double.isInfinite(c)) {
            throw new IllegalArgumentException("Coefficients must be finite numbers");
        }

        if (Math.abs(a) < epsilon) {
            throw new IllegalArgumentException("Coefficient 'a' must not be zero");
        }

        double discriminant = b * b - 4 * a * c;

        if (discriminant > epsilon) {
            double sqrtD = Math.sqrt(discriminant);
            return new double[]{
                    (-b + sqrtD) / (2 * a),
                    (-b - sqrtD) / (2 * a)
            };
        } else if (Math.abs(discriminant) < epsilon) {
            double root = -b / (2 * a);
            return new double[]{root};
        } else {
            return new double[]{};
        }
    }
}
