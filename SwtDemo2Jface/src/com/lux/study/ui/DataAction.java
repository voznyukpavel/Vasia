package com.lux.study.ui;

public enum DataAction {

    NEW("New"),
    SAVE("Save"),
    DELETE("Delete"),
    CANCEL("Cancel");

    private String name;

    DataAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}