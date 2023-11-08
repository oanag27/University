package model;

public class Sugar implements Deposit{
    private float price;

    public Sugar(float _price){
        price = _price;
    }

    public Sugar(){
        this(0);
    }
    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Sugar{" +
                "price=" + price +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sugar sugar = (Sugar) o;
        return Float.compare(sugar.price, price) == 0;
    }
}
