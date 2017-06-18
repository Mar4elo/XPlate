/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.investsoft.xplate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SMS
 */
public class XPlate extends XPlateScriptEngine implements IXPlate {

    @Override
    public void assign(String varname, Object var) {
        super.vars.put(varname, var);
    }

    @Override
    public void clear_all_assign() {
        super.vars.clear();
    }

    @Override
    public void clear_assign(String varname) {
        this.vars.remove(varname);
    }

    @Override
    public Map get_template_vars() {
        return super.vars;
    }

    @Override
    public Object get_template_vars(String varname) {
        return super.vars.get(varname);
    }

    @Override
    public String fetch(String template) {
        return super.exec(template, this.vars);
    }

    @Override
    public void setCompileDir(String path) {
        super.setPath(PathType.COMPILE, path);
    }

    @Override
    public void setTemplateDir(String path) {
        super.setPath(PathType.TEMPLATE, path);
    }

}
