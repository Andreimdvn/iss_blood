package Main;

import com.sun.javafx.application.LauncherImpl;

public class Main {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(MainApplication.class, PreloaderApp.class, args);
    }
}
