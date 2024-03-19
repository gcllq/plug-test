package com.croot.cdf.holder;

import com.intellij.openapi.project.Project;

public class ProjectHolder {


    private static Project project = null;

    public static Project getProject() {
        return project;
    }

    public static void setProject(Project project) {
        ProjectHolder.project = project;
    }
}
