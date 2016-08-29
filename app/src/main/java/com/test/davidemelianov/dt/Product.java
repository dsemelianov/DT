package com.test.davidemelianov.dt;

/**
 * Created by davidemelianov on 8/24/16.
 */
public class Product {
    public final String title;
    public final String description;
    public final String thumbnail;

    public Product(String t, String d, String th) {
        this.title = t;
        this.description = d;
        this.thumbnail = th;
    }
}