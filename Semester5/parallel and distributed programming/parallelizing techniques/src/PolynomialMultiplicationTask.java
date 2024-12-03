public class PolynomialMultiplicationTask implements Runnable {
    private final int startIndex;
    private final int endIndex;
    private final Polynomial firstPolynomial;
    private final Polynomial secondPolynomial;
    private final Polynomial resultPolynomial;

    public PolynomialMultiplicationTask(int startIndex, int endIndex, Polynomial firstPolynomial, Polynomial secondPolynomial, Polynomial resultPolynomial) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.firstPolynomial = firstPolynomial;
        this.secondPolynomial = secondPolynomial;
        this.resultPolynomial = resultPolynomial;
    }

    @Override
    public void run() {
        // iterate over the range of coefficients to be computed
        for (int resultIndex = startIndex; resultIndex < endIndex; resultIndex++) {
            // check if the index is within the bounds of the result polynomial
            if (resultIndex > resultPolynomial.getDegree() + 1)
                return;

            // iterate over the coefficients of the input polynomials
            for (int termIndex = 0; termIndex <= resultIndex; termIndex++) {
                // check if the indices are within the bounds of the input polynomials
                if (termIndex <= firstPolynomial.getDegree() && (resultIndex - termIndex) <= secondPolynomial.getDegree()) {
                    // compute the product of corresponding coefficients and update the result polynomial
                    int value = resultPolynomial.getCoefficient(resultIndex) + firstPolynomial.getCoefficient(termIndex) * secondPolynomial.getCoefficient(resultIndex - termIndex);
                    resultPolynomial.setCoefficient(resultIndex, value);
                }
            }
        }
    }
}