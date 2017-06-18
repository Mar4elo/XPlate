/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.investsoft.xplate;
import java.util.regex.*;
/**
 *
 * @author SMS
 */
public class XPlateBlocks {

    public static XPlateBlock getBlock(int start, int end, String text) {
        XPlateBlock ret = null;
        if (isVariable(text)) {
            ret = new XPlateBlockVariable(start, end, text);
        }
        return ret;
    }

    private static boolean isVariable(String text) {
        boolean ret = false;
        String pat = "\\{\\$(\\w.*)\\}";
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(text);
        if(m.find()){
//        String substring = text.substring(1, text.length() - 1).trim();
//        if (substring.substring(0, 1).equals("$")) {
            ret = true;
        }
        return ret;
    }
}
