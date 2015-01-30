package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 30/1/2015.
 */

import com.google.gson.annotations.Expose;
import com.imagsky.v81_android.domain.SysObject;

public abstract class Module extends SysObject {

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

}

