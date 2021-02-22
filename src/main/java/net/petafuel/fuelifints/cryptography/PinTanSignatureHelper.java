package net.petafuel.fuelifints.cryptography;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.client.LegitimationInfo;
import net.petafuel.fuelifints.protocol.fints3.FinTS3Controller;
import net.petafuel.fuelifints.protocol.fints3.segments.HNSHA;
import net.petafuel.fuelifints.protocol.fints3.segments.HNSHK;

public class PinTanSignatureHelper implements SignatureHelper {
    @Override
    public ReturnDataObject validateSignature(byte[] bytes, HNSHK hnshk, HNSHA hnsha, Dialog dialog) {
        /*
         * In PIN/TAN wird keine Signatur der Nachricht erstellt.
         * Es werden nur das Passwort und die Tan eingestellt.
         */
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(hnshk.getSchluesselname().getKreditsinstitutkennung().getKreditinstitutscode());
        String userId = hnshk.getSchluesselname().getBenutzerkennung();
        String pin = hnsha.getBenutzerdefinierteSignatur().getPin();
        ReturnDataObject checkSignature = dataAccessFacade.checkUserPin(userId, pin, dialog);
        if (!checkSignature.isSuccess()) {
            dialog.getLegitimationsInfo().setUserId(userId);
            dialog.setUserId(userId);
            dialog.getLegitimationsInfo().setCustomerId(userId);
        }
        String tan = hnsha.getBenutzerdefinierteSignatur().getTan();

        //TAN zwischenspeichern für Prepaid Backend
        if (dialog.getLegitimationsInfo() != null) {
            LegitimationInfo legitimationInfo = dialog.getLegitimationsInfo();
            legitimationInfo.setTanResponse(tan);
        }

        if (tan == null && dialog.getTransactionInfo() != null && dialog.getLegitimationsInfo().isStrongAuthenticated()) {
            //falls keine TAN mit geschickt wurde, aber der Kunde stark authentifiziert ist
            //dann wird auch keine benötigt
            checkSignature.setSuccess(true);
        } else if(tan == null && dialog.getTransactionInfo() != null) {
            //falls eine TAN erwartet, aber keine mitgeliefert wird schlägt die TAN Prüfung fehl
            //und es sollen keine Aufträge ausgeführt werden, unabhängig ob die PIN korrekt ist
            checkSignature.setSuccess(false);
        } else if (dialog.getTransactionInfo() != null && checkSignature.isSuccess()) {
            //es wurde eine TAN übermittelt und es gibt das Transaktions-Objekt
			ReturnDataObject checkTan = dataAccessFacade.checkTan(dialog.getDialogId(), dialog.getLegitimationsInfo(), dialog.getClientProductInfo(),tan, dialog.getTransactionInfo().getAuftragsHashwert());
			checkSignature.setSuccess(checkTan.isSuccess());
			if(!"".equals(checkTan.getMessage()))
			{
				checkSignature.setMessage(checkTan.getMessage());
			}
			if(!"".equals(checkTan.getReturnCode()))
			{
				checkSignature.setReturnCode(checkTan.getReturnCode());
			}

			if (checkTan.isSuccess()) {
                dialog.getLegitimationsInfo().setStrongAuthenticated(true);
                FinTS3Controller.getInstance().updateLegitimationInfo(dialog.getDialogId(), dialog.getLegitimationsInfo());
            }
        }
        return checkSignature;
    }

    @Override
    public byte[] sign(byte[] toSign, HNSHK hnshk) {
        return null;  //  Kein Validierungsresultat bei PIN / TAN
    }
}
