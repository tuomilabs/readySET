package org.tuomilabs.readySET;

public class Card {

    public int shape = 0;
    public int color = 0;
    public int number = 0;
    public int fill = 0;
    public Card(int a, int b, int c, int d) {
        shape = a;
        color = b;
        number = c;
        fill = d;
    }
    public Card() {
        shape = 0;
        color = 0;
        number = 0;
        fill = 0;
    }

    public void setCard(Card x){
        this.shape = x.getShape();
        this.color = x.getColor();
        this.number = x.getNumber();
        this.fill = x.getFill();
    }

    public void setShape(int x){
        this.shape = x;
    }

    public void setColor(int x){
        this.color = x;
    }

    public void setNumber(int x){
        this.number = x;
    }

    public void setFill(int x){
        this.fill = x;
    }

    public int getShape(){
        return this.shape;
    }

    public int getColor(){
        return this.color;
    }

    public int getNumber(){
        return this.number;
    }

    public int getFill(){
        return this.fill;
    }

    public boolean compare(Card x){
        if((this.shape == x.getShape()) && (this.color == x.getColor()) && (this.number == x.getNumber()) && (this.number == x.getNumber())){
            return true;
        }
        else return false;
    }

    public Card findMissing(Card x){
        Card z = new Card();

        if(x.getShape() == this.shape) z.setShape(this.shape);
        else z.setShape( (30 - (this.shape + x.getShape()))%3);

        if(x.getColor() == this.color) z.setColor(this.color);
        else z.setColor( (30 - (this.color + x.getColor()) )%3 );

        if(x.getNumber() == this.number) z.setNumber(this.number);
        else z.setNumber( (30 - (this.number + x.getNumber()))%3 );

        if(x.getFill() == this.fill) z.setFill(this.fill);
        else z.setFill( (30 - (this.fill + x.getFill()))%3 );

        return z;
    }

}
