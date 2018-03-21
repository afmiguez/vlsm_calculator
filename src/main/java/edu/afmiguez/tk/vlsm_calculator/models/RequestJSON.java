package edu.afmiguez.tk.vlsm_calculator.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestJSON {
    private Integer[] hostsSize;
    private int numberOfRouterNetworks;
    private String ip;
    private int cidr;
}
