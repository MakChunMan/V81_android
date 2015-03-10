package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 29/1/2015.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.imagsky.v81_android.domain.SysObject;

public class App extends SysObject {

    private static final long serialVersionUID = 1L;

    private String APP_NAME;

    private String APP_DESC;

    private int APP_TYPE; // 0 : Free

    private String APP_STATUS; //

    private String APP_TEMPLATE;

    private AppImage APP_MAINMENU_BG;

    private AppImage APP_ICON;

    private Collection<Module> modules = new ArrayList<Module>();

    public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("APP_NAME");
        returnList.add("APP_DESC");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (App.class.isInstance(thisObj)) {
            App obj = (App) thisObj;
            aHt.put("APP_NAME", obj.APP_NAME);
            aHt.put("APP_DESC", obj.APP_DESC);
            aHt.put("APP_TYPE", obj.APP_TYPE);
            aHt.put("APP_STATUS", obj.APP_STATUS);
            aHt.put("APP_TEMPLATE",obj.APP_TEMPLATE);
            aHt.put("APP_MAINMENU_BG",obj.APP_MAINMENU_BG);
            aHt.put("APP_ICON", obj.APP_ICON);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }

    public Collection<Module> getModules() {
        if(modules==null){
            modules = new ArrayList<Module>();
        }
        return modules;
    }

    public void setModules(Collection<Module> modules) {
        this.modules = modules;
    }

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void setAPP_NAME(String aPP_NAME) {
        APP_NAME = aPP_NAME;
    }

    public String getAPP_DESC() {
        return APP_DESC;
    }

    public void setAPP_DESC(String aPP_DESC) {
        APP_DESC = aPP_DESC;
    }

    public int getAPP_TYPE() {
        return APP_TYPE;
    }

    public void setAPP_TYPE(int aPP_TYPE) {
        APP_TYPE = aPP_TYPE;
    }

    public String getAPP_STATUS() {
        return APP_STATUS;
    }

    public void setAPP_STATUS(String aPP_STATUS) {
        APP_STATUS = aPP_STATUS;
    }

    public String getAPP_TEMPLATE() {
        return APP_TEMPLATE;
    }

    public void setAPP_TEMPLATE(String APP_TEMPLATE) {
        this.APP_TEMPLATE = APP_TEMPLATE;
    }

    public AppImage getAPP_MAINMENU_BG() {
        return APP_MAINMENU_BG;
    }

    public void setAPP_MAINMENU_BG(AppImage APP_MAINMENU_BG) {
        this.APP_MAINMENU_BG = APP_MAINMENU_BG;
    }

    public AppImage getAPP_ICON() {
        return APP_ICON;
    }

    public void setAPP_ICON(AppImage APP_ICON) {
        this.APP_ICON = APP_ICON;
    }
}
