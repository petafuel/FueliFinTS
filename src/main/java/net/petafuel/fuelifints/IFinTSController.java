package net.petafuel.fuelifints;

import net.petafuel.fuelifints.communication.FinTSCommunicationHandler;
import net.petafuel.fuelifints.exceptions.DependencyResolveException;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.model.Message;
import net.petafuel.fuelifints.protocol.FinTSPayload;

/**
 * Interface für den FinTSController, der den Dialogablauf organisiert
 */
public interface IFinTSController {
    /**
     *
     *
     * @param payload
     * @return
     */
    boolean newRequest(FinTSPayload payload, FinTSCommunicationHandler.CommunicationChannel channel);

    /**
     * Nur für Unit Tests - manuelles Setzen des ExecutorManagers ist normalerweise nicht erforderlich
     *
     * @param manager Executormanager, der genutzt werden soll
     */
    void setExecutor(ExecutorManager manager);

    /**
     * Funktion wird vom {@link net.petafuel.fuelifints.protocol.IFinTSDecryptor} aufgerufen, wenn
     * die Entschlüsselung der Kundennachricht fertiggestellt ist
     *
     * @param payload
     * @param dialog
     */
    void finishedDecryption(FinTSPayload payload, Dialog dialog);

    /**
     * Funktion wird vom {@link net.petafuel.fuelifints.protocol.IFinTSParser} aufgerufen, wenn
     * das Parsing der Kundennachricht abgeschlossen ist
     *
     * @param taskId
     * @param dialog Dialog mit enthaltener Kundennachricht
     */
    void finishedParsing(int taskId, Dialog dialog);

    /**
     * Wird im {@link net.petafuel.fuelifints.protocol.IFinTSExecutor} vor dem eigentlichen execute aufgerufen,
     * um die Vorraussetzungen für das Ausführen der Auftragssegmente des Kunden zu setzen
     *
     * @param elem
     * @param dialog
     * @throws DependencyResolveException
     */
    void injectDependencies(IMessageElement elem, Dialog dialog) throws DependencyResolveException;

    /**
     * Funktion wird vom {@link net.petafuel.fuelifints.protocol.IFinTSExecutor} aufgerufen, wenn
     * das Ausführen der Kundenaufträge abgeschlossen und die Antwortsegmente generiert worden sind
     *
     * @param msg
     */
    void finishedExecuting(Message msg);

    /**
     * Funktion wird vom {@link net.petafuel.fuelifints.protocol.IFinTSEncryptor} aufgerufen, wenn
     * die Antwortnachricht des Servers erfolgreich verschlüsselt wurde
     *
     * @param msg
     */
    void finishedEncryption(Message msg);

    /**
     * Gibt die Anzahl der Dialoge zurück, die gerade laufen und vom FinTSController verwaltet werden
     *
     * @return
     */
    int getRunningDialogCount();


    /**
     * Nur für Unit Tests, um manuell einen FinTSServer zu setzen (bzw zu mocken)
     *
     * @param finTSServer FinTSServer, der vom FinTSController genutzt werden soll
     */
    void setFinTSServer(FinTSServer finTSServer);

    /**
     * Getter für den aktuell benutzten FinTSServer
     *
     * @return FinTSServer, der gerade vom FinTSController genutzt wird
     */
    FinTSServer getFinTSServer();
}
