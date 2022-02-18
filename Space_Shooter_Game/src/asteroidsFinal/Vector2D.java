package asteroidsFinal;

public final class Vector2D {
    public double x, y;


    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }
    public boolean equals(java.lang.Object o) {
        if (o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            return x == v.x && y == v.y;
        } else
            return false;
    }

    public String toString() {
        return "Vector: " + x + "," + y;
    }

    public double mag() {
        return Math.hypot(x, y);
    }
    public double angle() {
        return Math.atan2(y, x);
    }
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += fac * v.x;
        this.y += fac * v.y;
        return this;
    }
    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }
    public Vector2D rotate(double angle) {
        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        this.x = newX;
        return this;
    }
    public double dist(Vector2D v) {
        return Math.hypot(this.x - v.x, this.y - v.y);

    }
    public Vector2D wrap(double w, double h) {
        if (x > w) x -= w;
        else if (x < 0) x += w;
        if (y > h) y -= h;
        else if (y < 0) y += h;
        return this;
    }

}