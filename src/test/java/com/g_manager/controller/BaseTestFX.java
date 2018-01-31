package com.g_manager.controller;

import com.g_manager.Main;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

/**
 * Created by Nikolenko Oleh on 06.01.2018.
 */
public abstract class BaseTestFX extends ApplicationTest{

    @Before
    public void setUppClass() throws Exception{
        ApplicationTest.launch(Main.class);
    }

    @BeforeClass
    public static void setupHeadlessMode() {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @SuppressWarnings("unchecked")
    public  <T extends Node> T find(String query, Class<T> clazz) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}
