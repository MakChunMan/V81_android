package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 29/1/2015.
 */
//import com.google.gson.annotations.Expose;
//TODO: Add Json lib
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;

public abstract class SysObject implements java.io.Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String sys_guid;

    private String sys_content_type;

    private String sys_cma_name;

    private String sys_clfd_guid;

    private String sys_master_lang_guid;

    private Boolean sys_is_node;

    private Boolean sys_is_live;

    private Date sys_live_dt;

    private Date sys_exp_dt;

    private Boolean sys_is_published;

    private Integer sys_priority;

    private String sys_creator;

    private Date sys_create_dt;

    private String sys_updator;

    private Date sys_update_dt;

    //GETTER SETTER

    public String getSys_guid() {
        return sys_guid;
    }
    public void setSys_guid(String sysGuid) {
        sys_guid = sysGuid;
    }
    public String getSys_content_type() {
        return sys_content_type;
    }
    public void setSys_content_type(String sysContentType) {
        sys_content_type = sysContentType;
    }
    public String getSys_cma_name() {
        return sys_cma_name;
    }
    public void setSys_cma_name(String sysCmaName) {
        sys_cma_name = sysCmaName;
    }
    public String getSys_clfd_guid() {
        return sys_clfd_guid;
    }
    public void setSys_clfd_guid(String sysClfdGuid) {
        sys_clfd_guid = sysClfdGuid;
    }
    public String getSys_master_lang_guid() {
        return sys_master_lang_guid;
    }
    public void setSys_master_lang_guid(String sysMasterLangGuid) {
        sys_master_lang_guid = sysMasterLangGuid;
    }
    public boolean isSys_is_node() {
        if(sys_is_node==null) return false;
        return sys_is_node;
    }
    public void setSys_is_node(Boolean sysIsNode) {
        sys_is_node = sysIsNode;
    }
    public boolean isSys_is_live() {
        if(sys_is_live==null) return false;
        return sys_is_live;
    }
    public void setSys_is_live(Boolean sysIsLive) {
        sys_is_live = sysIsLive;
    }
    public Date getSys_live_dt() {
        return sys_live_dt;
    }
    public void setSys_live_dt(Date sysLiveDt) {
        sys_live_dt = sysLiveDt;
    }
    public Date getSys_exp_dt() {
        return sys_exp_dt;
    }
    public void setSys_exp_dt(Date sysExpDt) {
        sys_exp_dt = sysExpDt;
    }

    //2013:09-16 Change return type from Boolean to boolean for JSON return
    public boolean isSys_is_published() {
        if(sys_is_published==null) return false;
        return sys_is_published;
    }
    public void setSys_is_published(Boolean sysIsPublished) {
        sys_is_published = sysIsPublished;
    }
    public Integer getSys_priority() {
        return sys_priority;
    }
    public void setSys_priority(Integer sysPriority) {
        sys_priority = sysPriority;
    }
    public String getSys_creator() {
        return sys_creator;
    }
    public void setSys_creator(String sysCreator) {
        sys_creator = sysCreator;
    }
    public Date getSys_create_dt() {
        return sys_create_dt;
    }
    public void setSys_create_dt(Date sysCreateDt) {
        sys_create_dt = sysCreateDt;
    }
    public String getSys_updator() {
        return sys_updator;
    }
    public void setSys_updator(String sysUpdator) {
        sys_updator = sysUpdator;
    }
    public Date getSys_update_dt() {
        return sys_update_dt;
    }
    public void setSys_update_dt(Date sysUpdateDt) {
        sys_update_dt = sysUpdateDt;
    }


    public static TreeMap<String, Object> getSysFields(SysObject obj){
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        aHt.put("sys_guid", obj.sys_guid);
        aHt.put("sys_content_type", obj.sys_content_type);
        aHt.put("sys_cma_name", obj.sys_cma_name);
        aHt.put("sys_clfd_guid", obj.sys_clfd_guid);
        aHt.put("sys_master_lang_guid", obj.sys_master_lang_guid);
        aHt.put("sys_is_node", obj.sys_is_node);
        aHt.put("sys_is_live", obj.sys_is_live);
        aHt.put("sys_live_dt", obj.sys_live_dt);
        aHt.put("sys_exp_dt", obj.sys_exp_dt);
        aHt.put("sys_is_published", obj.sys_is_published);
        aHt.put("sys_priority", obj.sys_priority);
        aHt.put("sys_creator", obj.sys_creator);
        aHt.put("sys_create_dt", obj.sys_create_dt);
        aHt.put("sys_updator", obj.sys_updator);
        aHt.put("sys_update_dt", obj.sys_update_dt);
        return aHt;
    }

    public static final String orderBySysPriority = "sys_priority";
    public static final String orderByCreateDate = "sys_create_dt";
    public static final String orderByLiveDate = "sys_live_dt";

    /***
     * Clone system object field except GUID
     * @param inObj
     */
    public void setSystemField(SysObject inObj){
        if(inObj.sys_content_type!=null)
            this.sys_content_type =inObj.sys_content_type;
        if(inObj.sys_cma_name!=null)
            this.sys_cma_name =inObj.sys_cma_name;
        if(inObj.sys_clfd_guid!=null)
            this.sys_clfd_guid =inObj.sys_clfd_guid;
        if(inObj.sys_master_lang_guid!=null)
            this.sys_master_lang_guid =inObj.sys_master_lang_guid;
        if(inObj.sys_is_node!=null)
            this.sys_is_node =inObj.sys_is_node;
        if(inObj.sys_is_live!=null)
            this.sys_is_live =inObj.sys_is_live;
        if(inObj.sys_live_dt!=null)
            this.sys_live_dt =inObj.sys_live_dt;
        if(inObj.sys_exp_dt!=null)
            this.sys_exp_dt =inObj.sys_exp_dt;
        if(inObj.sys_is_published!=null)
            this.sys_is_published =inObj.sys_is_published;
        if(inObj.sys_priority!=null)
            this.sys_priority =inObj.sys_priority;
    }
}

