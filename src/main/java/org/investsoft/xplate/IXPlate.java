/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.investsoft.xplate;

import java.util.List;
import java.util.Map;

/**
 *
 * @author SMS
 */
public interface IXPlate {

    void assign(String varname, Object var);

    void clear_all_assign();

    void clear_assign(String varname);

    Map get_template_vars();

    Object get_template_vars(String varname);

    String fetch(String template);

    void setCompileDir(String path);

    void setTemplateDir(String path);

}
