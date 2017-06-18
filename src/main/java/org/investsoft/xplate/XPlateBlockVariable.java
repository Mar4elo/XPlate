/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.investsoft.xplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SMS
 */
public class XPlateBlockVariable extends XPlateBlock {

    private String name;

    public XPlateBlockVariable(int start, int end, String text) {
        super(start, end);
        String pat = "\\{\\$(\\w.*)\\}";
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(text);
        if (m.find()) {
            this.name = m.group(1);
        }
    }

    @Override
    public String get() {
        StringBuilder sb = new StringBuilder();
        sb.append("'+").append(this.name).append("+'");
        return sb.toString();
    }

}
