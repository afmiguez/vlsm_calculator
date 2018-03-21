package edu.afmiguez.tk.vlsm_calculator.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IP {
    private short octets[]=new short[4];


    public IP(String ip){
        if(ip.length()!=32) throw new IllegalArgumentException();
        short octets[]=new short[4];
        for(int i=0;i<4;i++){
            octets[i]=getDecimal(ip.substring(i*8,(i+1)*8));
        }
        this.octets=octets;
    }

    public IP(String octets[]) {
        if(octets.length!=4) throw new IllegalArgumentException();
        int i=0;
        for(String octet:octets){
            short parsedOctet=Short.parseShort(octet);
            if(parsedOctet < 0 || parsedOctet > 255) throw new IllegalArgumentException();
            this.octets[i++]=parsedOctet;
        }
    }

    private IP(short octets[]){
        if(octets.length!=4) throw new IllegalArgumentException();
        this.octets=octets;
    }

    private IP(int cidr){
        if(cidr<0 || cidr > 32) throw new IllegalArgumentException();
        this.octets=getIP(cidr);
    }

    private String toBits(){
        StringBuilder sb=new StringBuilder();
        for(short octet:this.octets){
            sb.append(getBits(octet));
        }
        return sb.toString();
    }

    public IP previousIP(){
        String ipInBits=toBits();
        char arrayChar[]=ipInBits.toCharArray();
        if(arrayChar[31]=='1'){
            arrayChar[31]='0';
        }else {
            boolean carry=true;
            arrayChar[31]='1';
            for (int i = 30; i >= 0; i--) {
                if(carry){
                    if(arrayChar[i]=='0'){
                        arrayChar[i]='1';
                    }else{
                        arrayChar[i]='0';
                        carry=false;
                    }
                }
                else{
                    break;
                }
            }
        }
        StringBuilder ip= new StringBuilder();
        for(char c:arrayChar){
            ip.append(c);
        }
        return new IP(ip.toString());
    }

    public IP nextIP(){
        String ipInBits=toBits();
        char arrayChar[]=ipInBits.toCharArray();
        if(arrayChar[31]=='0'){
            arrayChar[31]='1';
        }else {
            boolean carry=true;
            arrayChar[31]='0';
            for (int i = 30; i >= 0; i--) {
                if(carry){
                    if(arrayChar[i]=='1'){
                        arrayChar[i]='0';
                    }else{
                        arrayChar[i]='1';
                        carry=false;
                    }
                }
                else{
                    break;
                }
            }
        }
        StringBuilder ip= new StringBuilder();
        for(char c:arrayChar){
            ip.append(c);
        }
        return new IP(ip.toString());
    }

//    public IP getNetIP(int cidr){
//        String netPrefix=getNetPrefix(cidr);
//        String octets[]=new String[4];
//        octets[0]="";
//        octets[1]="";
//        octets[2]="";
//        octets[3]="";
//        char charArray[]=netPrefix.toCharArray();
//        int i=0;
//        for(char c:charArray){
//            octets[i/8]+=c;
//            i++;
//        }
//        for(i=0;i<4;i++){
//            octets[i]=getDecimal(octets[i])+"";
//        }
//        return new IP(octets);
//    }

    public String getNetPrefix(int cidr){
        StringBuilder sb=new StringBuilder();
        IP net=applyMask(this,cidr);
        for(int i=0;i<4;i++){
            sb.append(getBits(net.octets[i]));
        }

        return sb.toString().substring(0,cidr);
    }

//    public static String getHostPrefix(int cidr){
//        StringBuilder sb=new StringBuilder();
//        for(int i=0;i<32-cidr;i++){
//            sb.append("0");
//        }
//        return sb.toString();
//    }

    private static short[] getIP(int cidr){
        short octets[]=new short[4];
        int mult=128;
        for(int i=0;i<cidr;i++){
            if(i%8==0 && i!=0){
                mult=128;
            }
            octets[(i/8)]+=mult;
            mult/=2;
        }

        return octets;
    }

    @Override
    public String toString() {
        return "" +
                octets[0] +
                "."+octets[1] +
                "."+octets[2] +
                "."+octets[3]
                ;
    }

    private static char logicalAND(char c1, char mask){
        if(mask=='0') return '0';
        else return c1;
    }

    private static IP applyMask(IP ip, int cidr){
        return applyMask(ip,new IP(cidr));
    }

    private static IP applyMask(IP ip, IP mask){
        short octets[]=new short[4];
        for(int i=0;i<4;i++){
            String ipString=getBits(ip.octets[i]);
            String ipMask=getBits(mask.octets[i]);
            octets[i]=getDecimal(applyMask(ipString,ipMask));
        }
        return new IP(octets);
    }

    private static String applyMask(String ip, String mask){
        StringBuilder result= new StringBuilder();
        for(int i=0;i<8;i++){
            char ipChar=ip.charAt(i);
            char maskChar=mask.charAt(i);
            result.append(logicalAND(ipChar, maskChar));
        }
        return result.toString();
    }

    private static String getBits(short octet){
        StringBuilder sb=new StringBuilder();
        short actualBit=128;
        for(int i=0;i<8;i++){
            if(octet >= actualBit){
                sb.append("1");
                octet-=actualBit;
            }else{
                sb.append("0");
            }
            actualBit/=2;
        }
        return sb.toString();
    }

    private static short getDecimal(String bits){
        short result=0;
        short multiplicand=1;
        if(bits.length()<8){
            int size=bits.length();
            for(;size<8;size++){
                bits+='0';
            }
        }
        for(int i=7;i>=0;i--){
            if(bits.charAt(i)=='1'){
                result+=multiplicand;
            }
            multiplicand*=2;
        }
        return result;
    }
}
