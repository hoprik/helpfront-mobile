package ru.helpfront.base.pages;

import androidx.activity.ComponentActivity;

abstract public class Page {
    public final ComponentActivity activity;
    public final int renderId;
    public Page(ComponentActivity activity){
        this.activity = activity;
        this.renderId = 0;
        render();
    }

    public Page(ComponentActivity activity, int renderId){
        this.activity = activity;
        this.renderId = renderId;
        render();
    }

    public abstract void render();
}
