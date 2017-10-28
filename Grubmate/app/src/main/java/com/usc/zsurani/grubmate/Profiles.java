package com.usc.zsurani.grubmate;

import android.net.Uri;

/**
 * Created by zsurani on 10/27/17.
 */

public class Profiles {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name; // name

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id; // fbID

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    Uri uri;

    Profiles() {}
}
