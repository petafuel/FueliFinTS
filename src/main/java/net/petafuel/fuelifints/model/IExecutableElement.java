package net.petafuel.fuelifints.model;

import java.util.List;

public interface IExecutableElement {

    public static enum StatusCode {
        OK, INFO, WARNING, ERROR
    }

    public StatusCode execute(Dialog dialog);

    public List<IMessageElement> getReplyMessageElements();

    public IMessageElement getStatusElement();
}
