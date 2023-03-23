package com.jimas;

import com.jimas.client.AnalyseEngineSdk;

public class ClientTest {

    public static void main(String[] args) {
        AnalyseEngineSdk.onChargeSuccess("orderid123", "zhangsan");
        AnalyseEngineSdk.onChargeRefund("orderid456", "lisi");
    }
}
