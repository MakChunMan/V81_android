package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 30/1/2015.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.annotations.Expose;
import com.imagsky.v81_android.domain.SysObject;

public class ModForm extends Module {

    private static final long serialVersionUID = 1L;

    public ModForm(){
        super.moduleType = Module.ModuleTypes.ModForm;
    }

    @Expose
    private String form_name;

    private App form_app;

    @Expose
    private Set<FormField> form_fields;

    public String getForm_name() {
        return form_name;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }

    public App getForm_app() {
        return form_app;
    }

    public void setForm_app(App form_app) {
        this.form_app = form_app;
    }

    public Set<FormField> getForm_fields() {
        return form_fields;
    }

    public void setForm_fields(Set<FormField> form_fields) {
        this.form_fields = form_fields;
    }

    @Override
    public String getModuleTitle() {
        return form_name;
    }

    public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("FORM_NAME");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (ModForm.class.isInstance(thisObj)) {
            ModForm obj = (ModForm) thisObj;
            aHt.put("FORM_NAME", obj.form_name);
            aHt.put("FORM_APP", obj.form_app);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }

}

