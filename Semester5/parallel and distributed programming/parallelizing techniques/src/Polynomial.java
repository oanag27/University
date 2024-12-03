import java.util.List;

public class Polynomial {
    private final List<Integer> coefficients;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public int getLength() {  //nr of coefficients
        return coefficients.size();
    }

    public int getDegree() {  //the degree of the polynomial
        return coefficients.size() - 1;
    }

    public int getCoefficient(int index) {
        return coefficients.get(index);
    }

    public void setCoefficient(int index, int element) {
        coefficients.set(index, element);
    }

    @Override
    public String toString() {
        StringBuilder polynomialString = new StringBuilder();
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            int coefficient = coefficients.get(i);
            if (coefficient != 0) {
                if (polynomialString.length() > 0) {
                    polynomialString.append(" + ");
                }
                if (i == 0 || coefficient != 1) {
                    polynomialString.append(coefficient);
                }
                if (i > 0) {
                    polynomialString.append("x");
                }
                if (i > 1) {
                    polynomialString.append("^").append(i);
                }
            }
        }
        return polynomialString.toString();
    }
}