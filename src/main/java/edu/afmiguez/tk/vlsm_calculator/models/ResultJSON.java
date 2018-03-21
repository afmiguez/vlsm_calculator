package edu.afmiguez.tk.vlsm_calculator.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultJSON {
    private String network;
    private String broadcast;
    private int cidr;

    public ResultJSON(String network, String broadcast, int cidr) {
        this.network = network;
        this.broadcast = broadcast;
        this.cidr = cidr;
    }
}
