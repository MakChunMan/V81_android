package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 30/1/2015.
 */

import com.google.gson.annotations.Expose;
import com.imagsky.v81_android.R;

import java.lang.Comparable;



public abstract class Module extends SysObject implements Comparable<Module>{

    private static final long serialVersionUID = 1L;

    @Expose
    protected ModuleTypes moduleType;

    public static enum ModuleTypes{
        ModDefault,
        ModAboutPage,
        ModForm,
        ModShopCatalog
    };

    private AppImage modIcon;

    @Expose
    private int modDisplayOrder;

    public String getModuleTypeName() {
        return moduleType.name();
    }

    public ModuleTypes getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleTypes moduleType) {
        this.moduleType = moduleType;
    }

    public void setModuleType(String moduleTypeName){
        this.moduleType = ModuleTypes.valueOf(moduleTypeName);
    }

    public abstract String getModuleTitle();

    public AppImage getModIcon() {
        return modIcon;
    }

    public void setModIcon(AppImage modIcon) {
        this.modIcon = modIcon;
    }

    public int getModDisplayOrder() {
        return modDisplayOrder;
    }

    public void setModDisplayOrder(int modDisplayOrder) {
        this.modDisplayOrder = modDisplayOrder;
    }

    public int compareTo(Module other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        // other and 0 if they are supposed to be equal

        return Double.compare(this.getModDisplayOrder(), other.getModDisplayOrder());
    }

    public int getDefaultModuleIconId(){
        if(this.moduleType == ModuleTypes.ModAboutPage)
            return R.drawable.icon_info;
        else if(this.moduleType == ModuleTypes.ModForm)
            return R.drawable.icon_form;
        else
            return R.drawable.icon_shop;
    }
}

