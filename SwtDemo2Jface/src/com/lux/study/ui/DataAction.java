package com.lux.study.ui;

public enum DataAction {

    NEW("New"),
    SAVE("Save"),
    DELETE("Delete"),
    CANCEL("Cancel"),
    
    ABOUT("About"),
    
    LOAD_FROM_FILE("Load from file"),
    SAVE_TO_FILE("Save to file"),
	EXIT("Exit");

    private String name;

    DataAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}