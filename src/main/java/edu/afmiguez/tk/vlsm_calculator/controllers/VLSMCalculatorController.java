package edu.afmiguez.tk.vlsm_calculator.controllers;

import edu.afmiguez.tk.vlsm_calculator.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class VLSMCalculatorController {

    @ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String resolveException() {
        return "error";
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ResultJSON> calculateNetworks(@RequestBody RequestJSON requestBody){
        IP net=new IP(requestBody.getIp().split("\\."));
        VLSMCalculator calculator=new VLSMCalculator(Arrays.asList(requestBody.getHostsSize()),requestBody.getNumberOfRouterNetworks(),new Network(net,requestBody.getCidr()));
        List<ResultJSON> resultJSONList=new ArrayList<>();
        for(Network network:calculator.getNetworkList()){
            resultJSONList.add(new ResultJSON(network.getNetworkPrefix().toString(),network.getBroadcast().toString(),network.getCidr()));
        }
        return resultJSONList;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(){
        System.out.println("test");
    }

}
