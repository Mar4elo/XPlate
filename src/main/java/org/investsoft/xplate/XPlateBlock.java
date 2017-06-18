/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.investsoft.xplate;

/**
 *
 * @author SMS
 */
abstract class XPlateBlock {
    public int start;
    public int end;

    public abstract String get();
    //public abstract Boolean validate(String text);
    
    XPlateBlock(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
