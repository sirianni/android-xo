package com.ericsirianni.xo;

import java.util.List;

import com.google.inject.Module;

import roboguice.application.GuiceApplication;

public class MainApplication extends GuiceApplication {

    @Override
    protected void addApplicationModules(List<Module> modules) {
        super.addApplicationModules(modules);
        modules.add(new MainModule()); 
    }
    
}
