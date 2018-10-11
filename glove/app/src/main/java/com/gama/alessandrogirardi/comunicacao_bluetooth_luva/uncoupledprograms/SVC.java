package com.gama.alessandrogirardi.comunicacao_bluetooth_luva.uncoupledprograms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.gama.alessandrogirardi.comunicacao_bluetooth_luva.uncoupled.GloveSensors;
import com.gama.alessandrogirardi.comunicacao_bluetooth_luva.uncoupled.IPredictScreen;
import com.google.gson.Gson;

public class SVC {
    private boolean isWorking = false;
    private IPredictScreen predictScreen;

    public boolean isWorking() {
        return isWorking;
    }

    private boolean lockPredict;

    public void lockPredict() {
        this.lockPredict = true;
    }
    public void unlockPredict() {
        this.lockPredict = false;
    }

    public boolean isPredictLocked(){
        return lockPredict;
    }

    private enum Kernel {LINEAR, POLY, RBF, SIGMOID}

    private class Classifier {
        private int nClasses;
        private int nRows;
        private int[] classes;
        private double[][] vectors;
        private double[][] coefficients;
        private double[] intercepts;
        private int[] weights;
        private String kernel;
        private Kernel kkernel;
        private double gamma;
        private double coef0;
        private double degree;
    }

    private Classifier clf;
    private static SVC ME;

    public static SVC getInstance(File file) throws FileNotFoundException {
        //   SVC svc = new SVC("Files/data.json");
        if (ME == null) {
            ME = new SVC();
        }

        String jsonStr = new Scanner(file).useDelimiter("\\Z").next();
        prepareJson(jsonStr);
        return ME;
    }

    public static SVC getInstance(InputStream file) {
        //   SVC svc = new SVC("Files/data.json");
        if (ME == null) {
            ME = new SVC();
        }
        String jsonStr = new Scanner(file).useDelimiter("\\Z").next();
        prepareJson(jsonStr);
        return ME;
    }

    private static void prepareJson(String jsonStr) {
        ME.clf = new Gson().fromJson(jsonStr, Classifier.class);
        ME.clf.classes = new int[ME.clf.nClasses];
        for (int i = 0; i < ME.clf.nClasses; i++) {
            ME.clf.classes[i] = i;
        }
        ME.clf.kkernel = Kernel.valueOf(ME.clf.kernel.toUpperCase());
    }


    private SVC() {

    }

    public void startPredict(IPredictScreen predictScreen) {
        this.predictScreen = predictScreen;
        isWorking = true;
        lockPredict=false;
        waitTheStablishment();
    }

    public void stopPredict() {
        if (isWorking) {
            isWorking = false;
        }
    }

    private char predict(double[] features) {
        double[] kernels = new double[this.clf.vectors.length];
        double kernel;
        switch (this.clf.kkernel) {
            case LINEAR:
                // <x,x'>
                for (int i = 0; i < this.clf.vectors.length; i++) {
                    kernel = 0.;
                    for (int j = 0; j < this.clf.vectors[i].length; j++) {
                        kernel += this.clf.vectors[i][j] * features[j];
                    }
                    kernels[i] = kernel;
                }
                break;
            case POLY:
                // (y<x,x'>+r)^d
                for (int i = 0; i < this.clf.vectors.length; i++) {
                    kernel = 0.;
                    for (int j = 0; j < this.clf.vectors[i].length; j++) {
                        kernel += this.clf.vectors[i][j] * features[j];
                    }
                    kernels[i] = Math.pow((this.clf.gamma * kernel) + this.clf.coef0, this.clf.degree);
                }
                break;
            case RBF:
                // exp(-y|x-x'|^2)
                for (int i = 0; i < this.clf.vectors.length; i++) {
                    kernel = 0.;
                    for (int j = 0; j < this.clf.vectors[i].length; j++) {
                        kernel += Math.pow(this.clf.vectors[i][j] - features[j], 2);
                    }
                    kernels[i] = Math.exp(-this.clf.gamma * kernel);
                }
                break;
            case SIGMOID:
                // tanh(y<x,x'>+r)
                for (int i = 0; i < this.clf.vectors.length; i++) {
                    kernel = 0.;
                    for (int j = 0; j < this.clf.vectors[i].length; j++) {
                        kernel += this.clf.vectors[i][j] * features[j];
                    }
                    kernels[i] = Math.tanh((this.clf.gamma * kernel) + this.clf.coef0);
                }
                break;
        }

        int[] starts = new int[this.clf.nRows];
        for (int i = 0; i < this.clf.nRows; i++) {
            if (i != 0) {
                int start = 0;
                for (int j = 0; j < i; j++) {
                    start += this.clf.weights[j];
                }
                starts[i] = start;
            } else {
                starts[0] = 0;
            }
        }

        int[] ends = new int[this.clf.nRows];
        for (int i = 0; i < this.clf.nRows; i++) {
            ends[i] = this.clf.weights[i] + starts[i];
        }

        if (this.clf.nClasses == 2) {
            for (int i = 0; i < kernels.length; i++) {
                kernels[i] = -kernels[i];
            }
            double decision = 0.;
            for (int k = starts[1]; k < ends[1]; k++) {
                decision += kernels[k] * this.clf.coefficients[0][k];
            }
            for (int k = starts[0]; k < ends[0]; k++) {
                decision += kernels[k] * this.clf.coefficients[0][k];
            }
            decision += this.clf.intercepts[0];
            if (decision > 0) {
                return 0;
            }
            return 1;
        }

        double[] decisions = new double[this.clf.intercepts.length];
        for (int i = 0, d = 0, l = this.clf.nRows; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                double tmp = 0.;
                for (int k = starts[j]; k < ends[j]; k++) {
                    tmp += this.clf.coefficients[i][k] * kernels[k];
                }
                for (int k = starts[i]; k < ends[i]; k++) {
                    tmp += this.clf.coefficients[j - 1][k] * kernels[k];
                }
                decisions[d] = tmp + this.clf.intercepts[d];
                d++;
            }
        }

        int[] votes = new int[this.clf.intercepts.length];
        for (int i = 0, d = 0, l = this.clf.nRows; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                votes[d] = decisions[d] > 0 ? i : j;
                d++;
            }
        }

        int[] amounts = new int[this.clf.nClasses];
        for (int i = 0, l = votes.length; i < l; i++) {
            amounts[votes[i]] += 1;
        }

        int classVal = -1, classIdx = -1;
        for (int i = 0, l = amounts.length; i < l; i++) {
            if (amounts[i] > classVal) {
                classVal = amounts[i];
                classIdx = i;
            }
        }
        return formatPredict(this.clf.classes[classIdx]);
    }

    private void waitTheStablishment() {

        System.out.println("preparando...");
        Thread thread = new Thread() {

            ArrayList<Double> table = new ArrayList<>();
            ArrayList<Double> tablePrevious = new ArrayList<>();
            ArrayList<Double> tableDiff = new ArrayList<>();
            int state = 3;
            int nextState = 0;

            public void run() {
                System.out.println("pronto");
                // TODO arruamar speecher]
                isWorking = true;
                predictScreen.getReadyToSpeak();

                while (isWorking()) {
                    System.out.printf("teste");
                    if (!lockPredict) {
                        System.out.println("teste2");
                        switch (state) {
                            case 1:

                                double max_variation;
                                if (GloveSensors.getInstance().getSensor6().getAz().size() > 2) {
                                    // Log.d("state", "" + 1);
                                    System.out.println("Moving...");
                                    table.add(GloveSensors.getInstance().getSensor1().getAx().get(GloveSensors.getInstance().getSensor1().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor1().getAy().get(GloveSensors.getInstance().getSensor1().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor1().getAz().get(GloveSensors.getInstance().getSensor1().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor2().getAx().get(GloveSensors.getInstance().getSensor2().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor2().getAy().get(GloveSensors.getInstance().getSensor2().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor2().getAz().get(GloveSensors.getInstance().getSensor2().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor3().getAx().get(GloveSensors.getInstance().getSensor3().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor3().getAy().get(GloveSensors.getInstance().getSensor3().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor3().getAz().get(GloveSensors.getInstance().getSensor3().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor4().getAx().get(GloveSensors.getInstance().getSensor4().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor4().getAy().get(GloveSensors.getInstance().getSensor4().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor4().getAz().get(GloveSensors.getInstance().getSensor4().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor5().getAx().get(GloveSensors.getInstance().getSensor5().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor5().getAy().get(GloveSensors.getInstance().getSensor5().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor5().getAz().get(GloveSensors.getInstance().getSensor5().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor6().getAx().get(GloveSensors.getInstance().getSensor6().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor6().getAy().get(GloveSensors.getInstance().getSensor6().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor6().getAz().get(GloveSensors.getInstance().getSensor6().getAz().size() - 1));

                                    tablePrevious.add(GloveSensors.getInstance().getSensor1().getAx().get(GloveSensors.getInstance().getSensor1().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor1().getAy().get(GloveSensors.getInstance().getSensor1().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor1().getAz().get(GloveSensors.getInstance().getSensor1().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor2().getAx().get(GloveSensors.getInstance().getSensor2().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor2().getAy().get(GloveSensors.getInstance().getSensor2().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor2().getAz().get(GloveSensors.getInstance().getSensor2().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor3().getAx().get(GloveSensors.getInstance().getSensor3().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor3().getAy().get(GloveSensors.getInstance().getSensor3().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor3().getAz().get(GloveSensors.getInstance().getSensor3().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor4().getAx().get(GloveSensors.getInstance().getSensor4().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor4().getAy().get(GloveSensors.getInstance().getSensor4().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor4().getAz().get(GloveSensors.getInstance().getSensor4().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor5().getAx().get(GloveSensors.getInstance().getSensor5().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor5().getAy().get(GloveSensors.getInstance().getSensor5().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor5().getAz().get(GloveSensors.getInstance().getSensor5().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor6().getAx().get(GloveSensors.getInstance().getSensor6().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor6().getAy().get(GloveSensors.getInstance().getSensor6().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor6().getAz().get(GloveSensors.getInstance().getSensor6().getAz().size() - 2));
                                    for (int i = 0; i < table.size(); i++) {

                                        double valorAtual = table.get(i);
                                        double valorAnterior = tablePrevious.get(i);
                                        double result = valorAnterior - valorAtual;
                                        if (result < 0) {
                                            result *= -1;
                                        }
                                        tableDiff.add(result);
                                    }

                                    max_variation = Collections.max(tableDiff);
                                } else {
                                    max_variation = 100;
                                }
                                // Log.d("MAX_VARIATION", "" + max_variation);
                                int estavel = 0;
                                if (max_variation < 0.2) {
                                    estavel++;
                                } else {
                                    estavel = 0;
                                }

                                if (estavel >= 1) {
                                    nextState = 2;
                                } else {
                                    nextState = 1;
                                }

                                break;
                            case 2:
                                // Log.d("state", "" + 2);
                                System.out.println("Predicting...");
                                table.add(GloveSensors.getInstance().getSensor1().getGx().get(GloveSensors.getInstance().getSensor1().getGx().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor1().getGy().get(GloveSensors.getInstance().getSensor1().getGy().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor1().getGz().get(GloveSensors.getInstance().getSensor1().getGz().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor2().getGx().get(GloveSensors.getInstance().getSensor2().getGx().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor2().getGy().get(GloveSensors.getInstance().getSensor2().getGy().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor2().getGz().get(GloveSensors.getInstance().getSensor2().getGz().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor3().getGx().get(GloveSensors.getInstance().getSensor3().getGx().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor3().getGy().get(GloveSensors.getInstance().getSensor3().getGy().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor3().getGz().get(GloveSensors.getInstance().getSensor3().getGz().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor4().getGx().get(GloveSensors.getInstance().getSensor4().getGx().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor4().getGy().get(GloveSensors.getInstance().getSensor4().getGy().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor4().getGz().get(GloveSensors.getInstance().getSensor4().getGz().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor5().getGx().get(GloveSensors.getInstance().getSensor5().getGx().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor5().getGy().get(GloveSensors.getInstance().getSensor5().getGy().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor5().getGz().get(GloveSensors.getInstance().getSensor5().getGz().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor6().getGx().get(GloveSensors.getInstance().getSensor6().getGx().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor6().getGy().get(GloveSensors.getInstance().getSensor6().getGy().size() - 1));
                                table.add(GloveSensors.getInstance().getSensor6().getGz().get(GloveSensors.getInstance().getSensor6().getGz().size() - 1));
                                final double[] args = new double[18];
                                for (int i = 0; i < 18; i++) {
                                    args[i] = table.get(i);
                                }
                                char pred = predict(args);
                                System.out.println("predicted" + pred);
                                if (pred != (char) 0) {
                                    // System.out.println(waitTheStablishment);
                                    predictScreen.appendCharaterToScreen(pred);
                                    if (pred == ' ') {
                                        Thread t = new Thread() {
                                            public void run() {
                                                predictScreen.speak();

                                            }
                                        };
                                        t.start();

                                    }
                                }
                                // TODO descobrir o pq da exceção
                                nextState = 3;
                                break;
                            case 3:

                                double maxVariationAc;
                                if (GloveSensors.getInstance().getSensor6().getAz().size() > 2) {
                                    // Log.d("state", "" + 3);
                                    System.out.println("Stopped");
                                    table.add(GloveSensors.getInstance().getSensor1().getAx().get(GloveSensors.getInstance().getSensor1().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor1().getAy().get(GloveSensors.getInstance().getSensor1().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor1().getAz().get(GloveSensors.getInstance().getSensor1().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor2().getAx().get(GloveSensors.getInstance().getSensor2().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor2().getAy().get(GloveSensors.getInstance().getSensor2().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor2().getAz().get(GloveSensors.getInstance().getSensor2().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor3().getAx().get(GloveSensors.getInstance().getSensor3().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor3().getAy().get(GloveSensors.getInstance().getSensor3().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor3().getAz().get(GloveSensors.getInstance().getSensor3().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor4().getAx().get(GloveSensors.getInstance().getSensor4().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor4().getAy().get(GloveSensors.getInstance().getSensor4().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor4().getAz().get(GloveSensors.getInstance().getSensor4().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor5().getAx().get(GloveSensors.getInstance().getSensor5().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor5().getAy().get(GloveSensors.getInstance().getSensor5().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor5().getAz().get(GloveSensors.getInstance().getSensor5().getAz().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor6().getAx().get(GloveSensors.getInstance().getSensor6().getAx().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor6().getAy().get(GloveSensors.getInstance().getSensor6().getAy().size() - 1));
                                    table.add(GloveSensors.getInstance().getSensor6().getAz().get(GloveSensors.getInstance().getSensor6().getAz().size() - 1));

                                    tablePrevious.add(GloveSensors.getInstance().getSensor1().getAx().get(GloveSensors.getInstance().getSensor1().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor1().getAy().get(GloveSensors.getInstance().getSensor1().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor1().getAz().get(GloveSensors.getInstance().getSensor1().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor2().getAx().get(GloveSensors.getInstance().getSensor2().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor2().getAy().get(GloveSensors.getInstance().getSensor2().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor2().getAz().get(GloveSensors.getInstance().getSensor2().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor3().getAx().get(GloveSensors.getInstance().getSensor3().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor3().getAy().get(GloveSensors.getInstance().getSensor3().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor3().getAz().get(GloveSensors.getInstance().getSensor3().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor4().getAx().get(GloveSensors.getInstance().getSensor4().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor4().getAy().get(GloveSensors.getInstance().getSensor4().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor4().getAz().get(GloveSensors.getInstance().getSensor4().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor5().getAx().get(GloveSensors.getInstance().getSensor5().getAx().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor5().getAy().get(GloveSensors.getInstance().getSensor5().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor5().getAz().get(GloveSensors.getInstance().getSensor5().getAz().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor6().getAx().get(GloveSensors.getInstance().getSensor6().getAx().size() - 2));
                                    //////// PRANDO

                                    tablePrevious.add(GloveSensors.getInstance().getSensor6().getAy().get(GloveSensors.getInstance().getSensor6().getAy().size() - 2));
                                    tablePrevious.add(GloveSensors.getInstance().getSensor6().getAz().get(GloveSensors.getInstance().getSensor6().getAz().size() - 2));
                                    for (int i = 0; i < table.size(); i++) {
                                        double valorAtual = table.get(i);
                                        double valorAnterior = tablePrevious.get(i);
                                        double result = valorAnterior - valorAtual;
                                        if (result < 0) {
                                            result *= -1;
                                        }
                                        tableDiff.add(result);
                                    }

                                    maxVariationAc = Collections.max(tableDiff);
                                } else {
                                    maxVariationAc = 0;
                                }
                                if (maxVariationAc > 1.5) {
                                    nextState = 1;
                                } else {
                                    nextState = 3;
                                }
                                break;

                        }

                        state = nextState;
                        table.clear();
                        tableDiff.clear();
                        tablePrevious.clear();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                System.out.println("State");
                // Deallocate the Synthesizer.
                predictScreen.closeTheSpeaker();
            }

        };
        thread.start();

    }


    private static char formatPredict(int pred) {
        if (pred < 26)
            return (char) (pred + 65);
        else if (pred == 26) {
            return ' ';
        } else {
            return (char) 0;
        }
    }


}