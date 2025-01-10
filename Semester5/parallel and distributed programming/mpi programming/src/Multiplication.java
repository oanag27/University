import java.util.ArrayList;
import java.util.List;

public class Multiplication {
    public static Polynomial combineResults(Object[] polynomials) { //multiple partial results (from workers) into a single polynomial
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= ((Polynomial) polynomials[0]).getDegree(); i++)
            coefficients.add(0);
        Polynomial result = new Polynomial(coefficients);
        // add all the polynomials to the result
        for (Object polynomial: polynomials)
            result = addPolynomials(result, (Polynomial) polynomial);
        return result;
    }

    public static Polynomial multiplySequentiallyWithBounds(int start, int end, Polynomial firstPolynomial, Polynomial secondPolynomial) {
        List<Integer> coefficients = new ArrayList<>();

        for (int i = 0; i <= firstPolynomial.getDegree() + secondPolynomial.getDegree(); i++)
            coefficients.add(0);

        Polynomial result = new Polynomial(coefficients);
        // multiply the coefficients of firstPolynomial that are within the bounds with p2
        for (int i = start; i < end; i++) {
            for (int j = 0; j <= secondPolynomial.getDegree(); j++) {
                int value = result.getCoefficient(i + j)+ firstPolynomial.getCoefficient(i) * secondPolynomial.getCoefficient(j);
                result.setCoefficient(i + j, value);
            }
        }

        return result;
    }

    public static Polynomial multiplySequentially(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= firstPolynomial.getDegree() + secondPolynomial.getDegree(); i++)
            coefficients.add(0);
        Polynomial result = new Polynomial(coefficients);
        for (int i = 0; i <= firstPolynomial.getDegree(); i++) {
            for (int j = 0; j <= secondPolynomial.getDegree(); j++) {
                int value = result.getCoefficient(i + j)+ firstPolynomial.getCoefficient(i) * secondPolynomial.getCoefficient(j);
                result.setCoefficient(i + j, value);
            }
        }
        return result;
    }

    private static Polynomial addPolynomials(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        List<Integer> coefficients = new ArrayList<>();

        for (int i = 0; i <= Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree()); i++) {
            int coefficient1 = (i <= firstPolynomial.getDegree()) ? firstPolynomial.getCoefficient(i) : 0;
            int coefficient2 = (i <= secondPolynomial.getDegree()) ? secondPolynomial.getCoefficient(i) : 0;

            coefficients.add(coefficient1 + coefficient2);
        }

        return new Polynomial(coefficients);
    }

    private static Polynomial subtractPolynomials(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        List<Integer> coefficients = new ArrayList<>();

        for (int i = 0; i <= Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree()); i++) {
            int coefficient1 = (i <= firstPolynomial.getDegree()) ? firstPolynomial.getCoefficient(i) : 0;
            int coefficient2 = (i <= secondPolynomial.getDegree()) ? secondPolynomial.getCoefficient(i) : 0;
            coefficients.add(coefficient1 - coefficient2);
        }
        return new Polynomial(coefficients);
    }

    private static Polynomial addLeadingZeros(Polynomial polynomial, int count) {
        List<Integer> coefficients = new ArrayList<>(polynomial.getCoefficients());
        // add zeros to the front (left) of the original coefficients list
        for (int i = 0; i < count; i++)
            coefficients.add(0, 0);
        return new Polynomial(coefficients);
    }

    public static Polynomial multiplyKaratsuba(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        // use regular multiplication if polynomial is too small to split
        if (firstPolynomial.getDegree() < 2 || secondPolynomial.getDegree() < 2)
            return multiplySequentially(firstPolynomial, secondPolynomial);

        // find the middle to split the polynomials in half
        int n = Math.min(firstPolynomial.getDegree(), secondPolynomial.getDegree()) / 2;

        // split the polynomials into low part (small terms) and high part (big terms)
        Polynomial p1Low = new Polynomial(firstPolynomial.getCoefficients().subList(0, n));
        Polynomial p1High = new Polynomial(firstPolynomial.getCoefficients().subList(n, firstPolynomial.getLength()));
        Polynomial p2Low = new Polynomial(secondPolynomial.getCoefficients().subList(0, n));
        Polynomial p2High = new Polynomial(secondPolynomial.getCoefficients().subList(n, secondPolynomial.getLength()));

        // P1(X) * Q1(X) <=> p1High * p2High
        Polynomial p1p2High = multiplyKaratsuba(p1High, p2High);
        // (P1(X)+P2(X)) * (Q1(X)+Q2(X)) <=> (p1High+p1Low) * (p2High+p2Low)
        Polynomial p1p2HighLow = multiplyKaratsuba(addPolynomials(p1High, p1Low), addPolynomials(p2High, p2Low));
        // P2(X) * Q2(X) <=> p1Low * p2Low
        Polynomial p1p2Low = multiplyKaratsuba(p1Low, p2Low);

        // [P1(X) * Q1(X)] * X^2n <=> add 2 * n zeros to p1High * p2High
        Polynomial r1 = addLeadingZeros(p1p2High, 2 * n);
        // [(P1(X)+P2(X)) * (Q1(X)+Q2(X)) - P1(X)*Q1(X) - P2(X)*Q2(X)] * X^n <=> add n zeros to (p1High+p1Low) * (p2High+p2Low) - p1High*p2High - p1Low*p2Low
        Polynomial r2 = addLeadingZeros(subtractPolynomials(subtractPolynomials(p1p2HighLow, p1p2High), p1p2Low), n);

        // add the final computed elements
        return addPolynomials(addPolynomials(r1, r2), p1p2Low);
    }
}