package Utils;

import Controller.ControlledScreen;
import javafx.scene.Node;

public class ViewDetails {
    private Node node;
    private ControlledScreen controlledScreen;

    public ViewDetails(Node node,ControlledScreen controlledScreen) {
        this.node = node;
        this.controlledScreen = controlledScreen;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ControlledScreen getControlledScreen() {
        return controlledScreen;
    }

    public void setControlledScreen(ControlledScreen controlledScreen) {
        this.controlledScreen = controlledScreen;
    }
}
