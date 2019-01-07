package com.nfsindustries.vsd;

public class VSDJNI {

    static{
        try{
            System.loadLibrary("VSDJNI");
        }catch(UnsatisfiedLinkError e){
            e.printStackTrace();
        }
    }

    // --- Native VSD method
    public native double processAudio(double[] inputArray);
}
