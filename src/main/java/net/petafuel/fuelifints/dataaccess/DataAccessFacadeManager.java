package net.petafuel.fuelifints.dataaccess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

public class DataAccessFacadeManager {

    private static final Logger LOG = LogManager.getLogger(DataAccessFacadeManager.class);
    private static final ConcurrentHashMap<String, DataAccessFacade> accessFacadeHashMap = new ConcurrentHashMap<>();

    private static final String CLASS_DEBUG = "net.petafuel.fuelifints.dataaccess.DummyAccessFacade";
    private static final String CLASS_PREPAID = "net.petafuel.fuelifints.dataaccess.PrepaidAccess";
    private static final String CLASS_BANKING2 = "net.petafuel.fuelifints.dataaccess.Banking2Access";

    /**
     * Diese Funktion liefert zur übergebenen Bankleitzahl die passende Dataaccessfacade zurück.
     * Dafür wird die entsprechende Properties Datei aus dem Dateisystem ausgelesen.
     * Die Properties Datei befindet sich im Verzeichnis ./config/ und hat das Format
     * <BLZ>.<FACADEIMPL>.properties
     * Sollte keine passende Configdatei gefunden werden, liefert diese Funktion null zurück
     * <p/>
     * Die DataAccessFacade wird dabei gespeichert, es wird also pro Bankleitzahl immer nur eine DataAccessFacade instanziert
     *
     * @param bankId Bankleitzahl, für die eine DataAccessFacade benötigt wird
     * @return DataAccessFacade oder null
     */
    public static DataAccessFacade getAccessFacade(String bankId) {
        try {

            final String banking2properties = "config/" + bankId + ".banking2.properties";
            final String prepaidProperties = "config/" + bankId + ".prepaid.properties";
            final String dummyProperties = "config/" + bankId + ".dummy.properties";

            if (new File(banking2properties).exists()) {
                LOG.info("Banking2-config gefunden: {}", banking2properties);
                if (!accessFacadeHashMap.containsKey(bankId)) {
                    accessFacadeHashMap.put(bankId, instantiateNewFacade(CLASS_BANKING2, banking2properties));
                }
            } else {
                if (new File(prepaidProperties).exists()) {
                    LOG.info("Prepaid-config gefunden: {}", prepaidProperties);
                    if (!accessFacadeHashMap.containsKey(bankId)) {
                        accessFacadeHashMap.put(bankId, instantiateNewFacade(CLASS_PREPAID, prepaidProperties));
                    }
                } else {
                    if (new File(dummyProperties).exists() || bankId.equals("DEBUG")) {
                        LOG.info("Dummy-config gefunden: {} ...(oder DEBUG BankID)", dummyProperties);
                        if (!accessFacadeHashMap.containsKey(bankId)) {
                            accessFacadeHashMap.put(bankId, instantiateNewFacade(CLASS_DEBUG, dummyProperties));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Cannot DataAccessFacade#getAccessFacade for bankId={}", bankId, e);
        }

        DataAccessFacade dataAccessFacade = accessFacadeHashMap.get(bankId);
        LOG.info("BankCode={}, DataAccessFacade={} ", bankId, dataAccessFacade);

        return dataAccessFacade;
    }

    @SuppressWarnings("unchecked")
    private static DataAccessFacade instantiateNewFacade(String classPath, String configPath) {
        try {
            Class facadeClass = Class.forName(classPath);
            Class[] constructorParamClasses = new Class[]{String.class};
            Object[] constructorParamValues = new Object[]{configPath};
            Constructor constructor = facadeClass.getConstructor(constructorParamClasses);
            return (DataAccessFacade) constructor.newInstance(constructorParamValues);
        } catch (Exception e) {
            LOG.error("Cannot instantiateNewFacade! classPath={}, configPath={}", classPath, configPath, e);
        }
        return null;
    }
}
