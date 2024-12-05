package entity;

import java.awt.Graphics;
import java.io.IOException;

public abstract class Entity {
    protected int x, y;
    protected int speed;
    protected int life;

    public abstract void update();
    public abstract void move();
    public abstract void setDefaultValue();
    public abstract void changeDirection(String nextStep);
    public abstract void chooseDirection();
    public abstract void draw(Graphics g) throws IOException;
}
