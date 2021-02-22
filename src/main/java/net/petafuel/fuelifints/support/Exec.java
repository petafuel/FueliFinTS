package net.petafuel.fuelifints.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Exec {
    private static final Logger LOG = LogManager.getLogger(Exec.class);
    private final String[] cmd;
    private String errorReturn = "";

    public Exec(String[] cmd) {
        this.cmd = cmd;
    }
    public String run() {
        StringBuilder out = new StringBuilder();
        StringBuilder error = new StringBuilder();
        try {
            LOG.debug(Arrays.toString(this.cmd));
            String line;
            Process p = Runtime.getRuntime().exec(this.cmd);
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader
                    (new InputStreamReader(p.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                out.append(line);
            }
            bri.close();
            while ((line = bre.readLine()) != null) {
                error.append(line);
            }
            bre.close();
            p.waitFor();
            LOG.debug("Done.");
        } catch (Exception err) {
            LOG.error("Exception", err);
        }
        this.errorReturn = error.toString();
        if (!error.toString().equals("")) {
            LOG.warn(error.toString());
        }
        return out.toString();
    }

    public String getErrorReturn() {
        return errorReturn;
    }
}
