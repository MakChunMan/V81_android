package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 30/1/2015.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.annotations.Expose;
import com.imagsky.v81_android.domain.SysObject;

public class FormField extends SysObject {

    private static final long serialVersionUID = 1L;

    @Expose
    private String formfield_label;

    @Expose
    private Integer formfield_displayorder;

    private ModForm form;

    @Expose
    private Integer formfield_type_code;

    public FormField(){}

    public FormField(String label, Integer displayOrder){
        formfield_label = label;
        formfield_displayorder = displayOrder;
    }

    public String getFormfield_label() {
        return formfield_label;
    }

    public void setFormfield_label(String formfield_label) {
        this.formfield_label = formfield_label;
    }

    public Integer getFormfield_displayorder() {
        return formfield_displayorder;
    }

    public void setFormfield_displayorder(Integer formfield_displayorder) {
        this.formfield_displayorder = formfield_displayorder;
    }

    public Integer getFormfield_type_code() {
        return formfield_type_code;
    }

    public void setFormfield_type_code(Integer formfield_code) {
        this.formfield_type_code = formfield_code;
    }

    public ModForm getForm() {
        return form;
    }

    public void setForm(ModForm form) {
        this.form = form;
    }

    public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("FIELD_LABEL");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (FormField.class.isInstance(thisObj)) {
            FormField obj = (FormField) thisObj;
            aHt.put("FIELD_LABEL", obj.formfield_label);
            aHt.put("FIELD_DISPLAY_ORDER", obj.formfield_displayorder);
            aHt.put("FORM_ID", obj.form);
            aHt.put("FIELD_TYPE_CODE", obj.formfield_type_code);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }


}
