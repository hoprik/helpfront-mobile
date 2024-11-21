package ru.helpfront.base.pages;

import androidx.activity.ComponentActivity;

abstract public class Page {
    public final ComponentActivity activity;
    public final int renderId;
    public static Page currentPage;
    public Page(ComponentActivity activity){
        this.activity = activity;
        this.renderId = 0;
        currentPage = this;
        render();
    }

    public Page(ComponentActivity activity, int renderId){
        this.activity = activity;
        this.renderId = renderId;
        render();
    }

    public abstract void render();
}
