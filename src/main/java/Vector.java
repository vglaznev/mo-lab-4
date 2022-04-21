public record Vector(double x1, double x2) {

    public static Vector sum(Vector a, Vector b) {
        return new Vector(a.x1 + b.x1, a.x2 + b.x2);
    }

    public static Vector mul(double a, Vector vector) {
        return new Vector(a * vector.x1, a * vector.x2);
    }

    public static Vector sub(Vector a, Vector b) {
        return new Vector(a.x1 - b.x1, a.x2 - b.x2);
    }

    public Double[] getAsArray() {
        return new Double[]{x1, x2};
    }

    public static Vector getOrtX1() {
        return new Vector(1, 0);
    }

    public static Vector getOrtX2() {
        return new Vector(0, 1);
    }

    public Vector copy() {
        return new Vector(x1, x2);
    }
}
