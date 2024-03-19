package com.croot.cdf.util;

import com.croot.cdf.holder.ProjectHolder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CdfModuleUtil {

    public static Module[] getAllModules() {
        Project project = ProjectHolder.getProject();
        if (project == null) return new Module[1];
        return ModuleManager.getInstance(project).getModules();
    }


    public static List<String> getModuleNames() {
        Project project = ProjectHolder.getProject();
        List<String> moduleNames = new ArrayList<>();
        if (project == null)return moduleNames;

        Module[] modules = ModuleManager.getInstance(project).getModules();
        if (modules.length == 0) return moduleNames;
        moduleNames = Arrays.stream(modules).map(Module::getName).collect(Collectors.toList());
        return moduleNames;
    }
}

