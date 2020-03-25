package com.hntxrj.txerp.im;

public class ImBaseData {
    public static Integer sdkAppId = 1400290495;
    public static String identifier = "system";
    private static String key = "79658d8c62dbd5cf02b5fc80d70ead6005801bcf57efa7b477c3a133aeec3738";
    public static String getUserSig(){
        TLSSigAPIv2 sig = new TLSSigAPIv2(sdkAppId, key);
        return sig.genSig(identifier, 2592000);
    }

    public static long getRandom(){
        long min = 0;
        long max = 4294967295L;
        return min + (long)(Math.random() * (max-min+1));
    }


}
