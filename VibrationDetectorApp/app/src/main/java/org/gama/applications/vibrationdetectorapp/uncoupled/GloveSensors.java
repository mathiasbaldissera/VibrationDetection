package org.gama.applications.vibrationdetectorapp.uncoupled;

import java.util.HashMap;
import java.util.Map;

public class GloveSensors {
    private SensorData sensor1;
    private SensorData sensor2;
    private SensorData sensor3;
    private SensorData sensor4;
    private SensorData sensor5;
    private SensorData sensor6;
    private static GloveSensors ME;

    public static final double TO_RAD = 10430.3783505;
    public static final double RESIST = 3276.8;

    public static GloveSensors getInstance() {
        if (ME == null) {
            ME = new GloveSensors();
        }
        return ME;
    }


    private GloveSensors() {
        setSensor1(new SensorData());
        setSensor2(new SensorData());
        setSensor3(new SensorData());
        setSensor4(new SensorData());
        setSensor5(new SensorData());
        setSensor6(new SensorData());
    }

    public void appendDataWithToRadAndResist(double sensor1GX, double sensor1GY, double sensor1GZ,
                                             double sensor1AX, double sensor1AY, double sensor1AZ,
                                             double sensor2GX, double sensor2GY, double sensor2GZ,
                                             double sensor2AX, double sensor2AY, double sensor2AZ,
                                             double sensor3GX, double sensor3GY, double sensor3GZ,
                                             double sensor3AX, double sensor3AY, double sensor3AZ,
                                             double sensor4GX, double sensor4GY, double sensor4GZ,
                                             double sensor4AX, double sensor4AY, double sensor4AZ,
                                             double sensor5GX, double sensor5GY, double sensor5GZ,
                                             double sensor5AX, double sensor5AY, double sensor5AZ,
                                             double sensor6GX, double sensor6GY, double sensor6GZ,
                                             double sensor6AX, double sensor6AY, double sensor6AZ) {

        getSensor1().getGx().add(sensor1GX / TO_RAD);
        getSensor1().getGy().add(sensor1GY / TO_RAD);
        getSensor1().getGz().add(sensor1GZ / TO_RAD);
        getSensor1().getAx().add(sensor1AX / RESIST);
        getSensor1().getAy().add(sensor1AY / RESIST);
        getSensor1().getAz().add(sensor1AZ / RESIST);

        getSensor2().getGx().add(sensor2GX / TO_RAD);
        getSensor2().getGy().add(sensor2GY / TO_RAD);
        getSensor2().getGz().add(sensor2GZ / TO_RAD);
        getSensor2().getAx().add(sensor2AX / RESIST);
        getSensor2().getAy().add(sensor2AY / RESIST);
        getSensor2().getAz().add(sensor2AZ / RESIST);

        getSensor3().getGx().add(sensor3GX / TO_RAD);
        getSensor3().getGy().add(sensor3GY / TO_RAD);
        getSensor3().getGz().add(sensor3GZ / TO_RAD);
        getSensor3().getAx().add(sensor3AX / RESIST);
        getSensor3().getAy().add(sensor3AY / RESIST);
        getSensor3().getAz().add(sensor3AZ / RESIST);

        getSensor4().getGx().add(sensor4GX / TO_RAD);
        getSensor4().getGy().add(sensor4GY / TO_RAD);
        getSensor4().getGz().add(sensor4GZ / TO_RAD);
        getSensor4().getAx().add(sensor4AX / RESIST);
        getSensor4().getAy().add(sensor4AY / RESIST);
        getSensor4().getAz().add(sensor4AZ / RESIST);

        getSensor5().getGx().add(sensor5GX / TO_RAD);
        getSensor5().getGy().add(sensor5GY / TO_RAD);
        getSensor5().getGz().add(sensor5GZ / TO_RAD);
        getSensor5().getAx().add(sensor5AX / RESIST);
        getSensor5().getAy().add(sensor5AY / RESIST);
        getSensor5().getAz().add(sensor5AZ / RESIST);

        getSensor6().getGx().add(sensor6GX / TO_RAD);
        getSensor6().getGy().add(sensor6GY / TO_RAD);
        getSensor6().getGz().add(sensor6GZ / TO_RAD);
        getSensor6().getAx().add(sensor6AX / RESIST);
        getSensor6().getAy().add(sensor6AY / RESIST);
        getSensor6().getAz().add(sensor6AZ / RESIST);

    }


    public SensorData getSensor1() {
        return sensor1;
    }

    public void setSensor1(SensorData sensor1) {
        this.sensor1 = sensor1;
    }

    public SensorData getSensor2() {
        return sensor2;
    }

    public void setSensor2(SensorData sensor2) {
        this.sensor2 = sensor2;
    }

    public SensorData getSensor3() {
        return sensor3;
    }

    public void setSensor3(SensorData sensor3) {
        this.sensor3 = sensor3;
    }

    public SensorData getSensor4() {
        return sensor4;
    }

    public void setSensor4(SensorData sensor4) {
        this.sensor4 = sensor4;
    }

    public SensorData getSensor5() {
        return sensor5;
    }

    public void setSensor5(SensorData sensor5) {
        this.sensor5 = sensor5;
    }

    public SensorData getSensor6() {
        return sensor6;
    }

    public void setSensor6(SensorData sensor6) {
        this.sensor6 = sensor6;
    }
}
