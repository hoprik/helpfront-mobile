package ru.helpfront.base.pages;

import androidx.activity.ComponentActivity;

abstract public class Page {
    public final ComponentActivity activity;
    public Page(ComponentActivity activity){
        this.activity = activity;
        render();
    }

    public abstract void render();
}
