package edu.afmiguez.tk.vlsm_calculator.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Network {
    private IP networkPrefix;
    private IP broadcast;
    private int cidr;

    public Network(IP ip, int cidr){
        this.cidr=cidr;
        this.networkPrefix=getNetworkPrefixAddress(ip);
        this.broadcast=getBroadcastAddress();
    }

    private IP getNetworkPrefixAddress(IP ip){
        StringBuilder netPrefix= new StringBuilder(ip.getNetPrefix(this.cidr));
        for(int i=0;i<32-this.cidr;i++){
            netPrefix.append("0");
        }
        return new IP(netPrefix.toString());
    }

    private IP getBroadcastAddress(){
        StringBuilder netPrefix= new StringBuilder(this.networkPrefix.getNetPrefix(this.cidr));
        for(int i=0;i<32-this.cidr;i++){
            netPrefix.append("1");
        }
        return new IP(netPrefix.toString());
    }

    @Override
    public String toString() {
        return "\nNetwork{" +
                "networkPrefix=" + networkPrefix +
                "/"+cidr+", broadcast=" + broadcast +
                "/" + cidr +
                "}";
    }

}
