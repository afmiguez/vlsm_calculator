package edu.afmiguez.tk.vlsm_calculator.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VLSMCalculator {
    private List<Network> networkList;

    public VLSMCalculator(List<Integer>hostsSize, int numberOfRouterNetworks,Network original) {
        this.networkList=calculateVLSM(hostsSize,numberOfRouterNetworks,original);
    }

        private List<Network> calculateVLSM(List<Integer> hostsSize, int numberOfRouterNetworks,Network original) {
        List<Network> networks=new ArrayList<>();
        IP actualIP=original.getNetworkPrefix();
        for(int size:hostsSize){
            int cidr= getCIDR(size+2);
            Network newNetwork=new Network(actualIP,cidr);
            networks.add(newNetwork);
            actualIP=newNetwork.getBroadcast().nextIP();
        }
        actualIP=original.getBroadcast();
        for(int i=0;i<numberOfRouterNetworks;i++){
            /*
            TODO
             */
            int cidr=30;
            Network newNetwork=new Network(actualIP,cidr);
            networks.add(newNetwork);
            actualIP=newNetwork.getNetworkPrefix().previousIP();

        }

        return networks;
    }

    private static int getCIDR(int size) {
        return (int) (32-Math.ceil(Math.log(size)/Math.log(2)));
    }

    @Override
    public String toString() {
        return "VLSMCalculator{" +
                "networkList=" + networkList +
                '}';
    }

}
