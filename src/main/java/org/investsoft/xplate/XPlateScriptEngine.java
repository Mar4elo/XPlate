/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.investsoft.xplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.*;

/**
 *
 * @author SMS
 */
class XPlateScriptEngine {

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
    protected Map<String, Object> vars = new HashMap<>();
    private String templatePath, compilePath;

    protected String exec(String template, Map<String, Object> vars) {
        String ret = "";
        try {
            String text = this.prepareTemplate(template);
            vars.forEach((key, val) -> this.engine.put(key, val));
            ret = (String) engine.eval(text);
        } catch (ScriptException | IOException ex) {
            Logger.getLogger(XPlateScriptEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private String prepareTemplate(String template) throws IOException {

//        File f = new File(this.templatePath.concat(template));
//        File fc = new File(this.compilePath.concat(f.lastModified() + "." + template));
        String ret = "";
        Path f = Paths.get(this.templatePath.concat(template));
        Path fc = null;
        if (this.compilePath != null) {
            FileTime lastModifiedTime = Files.getLastModifiedTime(f);
            fc = Paths.get(this.compilePath.concat(lastModifiedTime.toMillis() + "." + template));
        }
        if (this.compilePath == null || (this.compilePath != null && !Files.exists(fc))) {
            String content = this.getFileContent(f);
            content = content.replaceAll("\\'", "\\\\'").replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
            StringBuilder sb = new StringBuilder();
            sb.append("'").append(content).append("'");
            List<XPlateBlock> blocks = getBlocks(content);
            ret = templateMerge(sb, blocks);
//            if(fc.getPath())
            if (this.compilePath != null) {
                Files.write(fc, ret.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            }
        } else {
            ret = this.getFileContent(fc);
        }
        return ret;
    }

    private String templateMerge(StringBuilder template, List<XPlateBlock> blocks) {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            template.replace(blocks.get(i).start + 1, blocks.get(i).end + 1, blocks.get(i).get());
        }
        return template.toString();
    }

    private List<XPlateBlock> getBlocks(String text) {
        Pattern p = Pattern.compile("\\{.*?\\}");
        Matcher m = p.matcher(text);
        List<XPlateBlock> ret = null;
        while (m.find()) {
            if (ret == null) {
                ret = new ArrayList<>();
            }
            XPlateBlock block = XPlateBlocks.getBlock(m.start(), m.end(), text.substring(m.start(), m.end()));
            if (block != null) {
                ret.add(block);
            }
        }
        return ret;
    }

    private String getFileContent(Path fileName) throws IOException {
        String ret = "";
        byte[] encoded = Files.readAllBytes(fileName);
        ret = new String(encoded, StandardCharsets.UTF_8);
        return ret;
    }

    protected void setPath(PathType pathType, String path) {
        if (!path.endsWith("\\")) {
            path = path.concat("\\");
        }
        switch (pathType) {
            case TEMPLATE:
                this.templatePath = path;
                break;
            case COMPILE:
                this.compilePath = path;
                break;
        }
    }

    enum PathType {
        TEMPLATE, COMPILE;
    }
}
