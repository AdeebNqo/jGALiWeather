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

    @Override
    public int apply(double value) {
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
