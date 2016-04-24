package jgaliweather.configuration.partition_reader;

public class CrispInterval implements Set {

    private double a;
    private double b;
    private String name;
    private String mode;

    public CrispInterval(double a, double b) {
        this.a = a;
        this.b = b;
        this.name = null;
        this.mode = "Closed";
    }

    public CrispInterval(double a, double b, String name, String mode) {
        this.a = a;
        this.b = b;
        this.name = name;
        this.mode = mode;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
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
