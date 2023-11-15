package model;

public class Salt implements Deposit{
    private float price;

    public Salt(float _price){
        price = _price;
    }
    public Salt(){
        this(0);
    }
    @Override
    public float getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "Salt{" +
                "price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salt salt = (Salt) o;
        return Float.compare(salt.price, price) == 0;
    }
}
