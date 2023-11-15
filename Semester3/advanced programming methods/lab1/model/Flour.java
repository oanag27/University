package model;

public class Flour implements Deposit {
    private float price;
    public Flour(float _price) {
        price = _price;
    }
    public Flour() {
        this(0);
    }
    @Override
    public float getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Flour{" +
                "price=" + price +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flour flour = (Flour) o;
        return Float.compare(flour.price, price) == 0;
    }

}
