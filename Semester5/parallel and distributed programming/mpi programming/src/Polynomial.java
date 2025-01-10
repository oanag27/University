import java.io.Serializable;
import java.util.List;

// enable the Java Object Serialization mechanism to convert instances of the Polynomial
// class into a byte stream that can be sent over the MPI communication channel

public class Polynomial implements Serializable {
    private final List<Integer> coefficients;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public int getLength() {
        return coefficients.size();
    }

    public int getDegree() {
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
        StringBuilder result = new StringBuilder();
        for (int degree = coefficients.size() - 1; degree >= 0; degree--) {
            int coefficient = coefficients.get(degree);
            if (coefficient != 0) {
                if (result.length() > 0)
                    result.append(" + ");
                if (degree == 0 || coefficient != 1)
                    result.append(coefficient);
                if (degree > 0)
                    result.append("x");
                if (degree > 1)
                    result.append("^").append(degree);
            }
        }
        return result.toString();
    }
}