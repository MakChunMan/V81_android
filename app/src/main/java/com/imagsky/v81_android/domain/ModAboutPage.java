package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 30/1/2015.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.annotations.Expose;
import com.imagsky.v81_android.domain.SysObject;

public class ModAboutPage extends Module {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ModAboutPage(){
        super.moduleType = Module.ModuleTypes.ModAboutPage;
    }

    @Expose
    private String pageTitle;

    @Expose
    private String pageAbout;

    @Expose
    private String pageDescription;

    @Expose
    private AppImage pageImage;

    @Expose
    private String pageFacebookLink;

    @Expose
    private String pageEmail;

    @Expose
    private String pageAddress;

    public static List getWildFields() {
        List returnList = new ArrayList();
        returnList.add("ABT_TITLE");
        returnList.add("ABT_ABOUT");
        return returnList;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (ModAboutPage.class.isInstance(thisObj)) {
            ModAboutPage obj = (ModAboutPage) thisObj;
            aHt.put("ABT_TITLE", obj.pageTitle);
            aHt.put("ABT_ABOUT", obj.pageAbout);
            aHt.put("ABT_DESC", obj.pageDescription);
            aHt.put("ABT_IMAGE", obj.pageImage);
            aHt.put("ABT_FACEBOOK", obj.pageFacebookLink);
            aHt.put("ABT_EMAIL", obj.pageEmail);
            aHt.put("ABT_ADDRESS", obj.pageAddress);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }
    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageAbout() {
        return pageAbout;
    }

    public void setPageAbout(String pageAbout) {
        this.pageAbout = pageAbout;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    public AppImage getPageImage() {
        return pageImage;
    }

    public void setPageImage(AppImage pageImage) {
        this.pageImage = pageImage;
    }

    public String getPageFacebookLink() {
        return pageFacebookLink;
    }

    public void setPageFacebookLink(String pageFacebookLink) {
        this.pageFacebookLink = pageFacebookLink;
    }

    public String getPageEmail() {
        return pageEmail;
    }

    public void setPageEmail(String pageEmail) {
        this.pageEmail = pageEmail;
    }

    public String getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    @Override
    public String getModuleTitle() {
        return this.pageTitle;
    }


}
