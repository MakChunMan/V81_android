package com.imagsky.v81_android.domain;

/**
 * Created by jasonmak on 30/1/2015.
 */

        import java.util.ArrayList;
        import java.util.List;
        import java.util.TreeMap;

        import com.google.gson.annotations.Expose;
        import com.imagsky.v81_android.domain.SysObject;

public class AppImage extends SysObject {

    private static final long serialVersionUID = 1L;

    private App imageOwnerApp;

    @Expose
    private String imageUrl;

    public AppImage(){
    }


    public AppImage(App owner, String url){
        this.imageOwnerApp = owner;
        this.imageUrl = url;
    }

    public App getImageOwnerApp() {
        return imageOwnerApp;
    }

    public void setImageOwnerApp(App imageOwnerApp) {
        this.imageOwnerApp = imageOwnerApp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static TreeMap<String, Object> getFields(Object thisObj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        if (AppImage.class.isInstance(thisObj)) {
            AppImage obj = (AppImage) thisObj;
            aHt.put("imageOwnerApp", obj.imageOwnerApp);
            aHt.put("imageUrl", obj.imageUrl);
            aHt.putAll(SysObject.getSysFields(obj));
        }
        return aHt;
    }

    public static List getWildFields() {
        return new ArrayList();
    }
}
