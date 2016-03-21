package jgaliweather.configuration.partition_reader;

public class CrispInterval implements Set {

    private float a;
    private float b;
    private String name;
    private String mode;

    public CrispInterval(float a, float b) {
        this.a = a;
        this.b = b;
        this.name = null;
        this.mode = "Closed";
    }

    public CrispInterval(float a, float b, String name, String mode) {
        this.a = a;
        this.b = b;
        this.name = name;
        this.mode = mode;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public double apply(double value) {
        switch (mode) {
            case "Closed":
                if (value >= a && value <= b) {
                    return 1;
                } else {
                    return 0;
                }
            case "Open":
                if (value > a && value < b) {
                    return 1;
                } else {
                    return 0;
                }
            case "RightClosed":
                if (value > a && value <= b) {
                    return 1;
                } else {
                    return 0;
                }
            case "LeftClosed":
                if (value >= a && value < b) {
                    return 1;
                } else {
                    return 0;
                }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "CrispInterval " + name + ": " + mode + " {" + a + ", " + b + "}";
    }
}
