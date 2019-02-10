package cryptomessenger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Base64;

import javax.swing.JOptionPane;

public class Formatter {
    public Formatter() {
    }
    public String toHexString(byte[] arg) {
        String formattedString="";
        try {
            return Base64.getEncoder().encodeToString(arg);
        }catch ( Exception e){
            JOptionPane.showMessageDialog(null,"Encoding problem with format in the Formatter Class please change the encode format");
            //TODO: Set an alert window for that issue
        }
        return formattedString;
    }
}
