package org.gama.applications.vibrationdetectorapp.uncoupled;

public class SensorData {

    int maxSize = 800;
    private AutoRemoveVector<Double> ax = new AutoRemoveVector<>(maxSize);
    private AutoRemoveVector<Double> ay = new AutoRemoveVector<>(maxSize);
    private AutoRemoveVector<Double> az = new AutoRemoveVector<>(maxSize);
    private AutoRemoveVector<Double> gx = new AutoRemoveVector<>(maxSize);
    private AutoRemoveVector<Double> gy = new AutoRemoveVector<>(maxSize);
    private AutoRemoveVector<Double> gz = new AutoRemoveVector<>(maxSize);

    public double lastPointsAx(int points) {
        double sum = 0;
        int j = 0;
        for (int i = ax.size() - 1; j < points && i > 0; i--, j++) {
            sum += ax.get(i);
        }
        return sum / j;
    }

    public double lastPointsAy(int points) {
        double sum = 0;
        int j = 0;
        for (int i = ay.size() - 1; j < points && i > 0; i--, j++) {
            sum += ay.get(i);
        }
        return sum / j;
    }

    public double lastPointsAz(int points) {
        double sum = 0;
        int j = 0;
        for (int i = az.size() - 1; j < points && i > 0; i--, j++) {
            sum += az.get(i);
        }
        return sum / j;
    }

    public double lastPointsGx(int points) {
        double sum = 0;
        int j = 0;
        for (int i = gx.size() - 1; j < points && i > 0; i--, j++) {
            sum += gx.get(i);
        }
        return sum / j;
    }

    public double lastPointsGy(int points) {
        double sum = 0;
        int j = 0;
        for (int i = gy.size() - 1; j < points && i > 0; i--, j++) {
            sum += gy.get(i);
        }
        return sum / j;
    }

    public double lastPointsGz(int points) {
        double sum = 0;
        int j = 0;
        for (int i = gz.size() - 1; j < points && i > 0; i--, j++) {
            sum += gz.get(i);
        }
        return sum / j;
    }


    public AutoRemoveVector<Double> getAx() {
        return ax;
    }

    public AutoRemoveVector<Double> getAy() {
        return ay;
    }

    public AutoRemoveVector<Double> getAz() {
        return az;
    }

    public AutoRemoveVector<Double> getGx() {
        return gx;
    }

    public AutoRemoveVector<Double> getGy() {
        return gy;
    }

    public AutoRemoveVector<Double> getGz() {
        return gz;
    }


}
