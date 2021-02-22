package net.petafuel.fuelifints.dataaccess.dataobjects;

import net.petafuel.jsepa.util.BankDateCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HolidaysObject {
    private static final Logger LOG = LogManager.getLogger(HolidaysObject.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *
     * @param ls_art core1 specific
     * @param ls_sequenz core1 specific
     * @param sepaDueDate DueDate of the SDD
     * @param cutOffHour Time of day when the cutOff is reached
     * @param cutOffMinutes Time of day when the cutOff is reached
     * @param cutOffAdditionalDays Days to add after the cutOff Time is reached for the specific Bank
     * @param sddDueDateAdditionalDays Days to always add to the Due Date of SEPA Direct Debit Transactions
     * @param checkCore1 is the direct debit of type core1
     * @return Date
     * @throws UnsupportedOperationException
     */
	public static Date checkDueDate(String ls_art, String ls_sequenz, Date sepaDueDate, int cutOffHour, int cutOffMinutes, int cutOffAdditionalDays, int sddDueDateAdditionalDays, boolean checkCore1) throws UnsupportedOperationException
	{
        // Calculate target executiondate and compare with xml executiondate:
        Calendar cutOffTime = new GregorianCalendar();
        cutOffTime.set(Calendar.HOUR_OF_DAY, cutOffHour);
        cutOffTime.set(Calendar.MINUTE, cutOffMinutes);

        Calendar todayTime = new GregorianCalendar();
        if (todayTime.after(cutOffTime)) {
            //if the cutoff time is reached, add the additional cutoff days
            sddDueDateAdditionalDays += cutOffAdditionalDays;
        }
        if (checkCore1) {
            try {
                sddDueDateAdditionalDays += BankDateCalculator.getAdditionalDayCount(ls_art, ls_sequenz);
            } catch (IllegalArgumentException e) {
                LOG.warn("Ungültige SEPA-Lastschrift-Art oder -Sequenz");
                throw new UnsupportedOperationException("Ungültige SEPA-Lastschrift-Art oder -Sequenz");
            }
        } else {
            // Always add one day as SDD is to be inquired to the bank at least one day in advance
            sddDueDateAdditionalDays++;
        }
        Calendar targetExecutionTime = BankDateCalculator.getTarget2DiffDate(sddDueDateAdditionalDays);
        //Change Calender.HOUR_OF_DAY to ensure sepaDueDate is after targetExecutionTime if both have the same date, but differ in time
        targetExecutionTime.set(Calendar.HOUR_OF_DAY, 0);
        targetExecutionTime.set(Calendar.MINUTE, 0);
        targetExecutionTime.set(Calendar.SECOND, 0);
        Calendar executionTime = new GregorianCalendar();
        executionTime.setTime(sepaDueDate);
        executionTime.set(Calendar.HOUR_OF_DAY, 0);
        executionTime.set(Calendar.MINUTE, 0);
        executionTime.set(Calendar.SECOND, 1);
        GregorianCalendar maxExecutionTime = new GregorianCalendar();
        maxExecutionTime.setTime(new Date());
        maxExecutionTime.set(Calendar.HOUR_OF_DAY, 0);
        maxExecutionTime.set(Calendar.MINUTE, 0);
        maxExecutionTime.set(Calendar.SECOND, 1);
        maxExecutionTime.add(Calendar.DAY_OF_YEAR, 14);
        LOG.info("Days to add: {}, TargetExecutionTime: {}, ExecutionTime: {}, MaxExecutionTime: {}", sddDueDateAdditionalDays, sdf.format(targetExecutionTime.getTime()), sdf.format(executionTime.getTime()), sdf.format(maxExecutionTime.getTime()));
        if (!(executionTime.getTime().after(targetExecutionTime.getTime()))) {
            LOG.warn("Zeitraum für Mindestvorlaufzeit stimmt nicht");
            throw new UnsupportedOperationException("Zeitraum fuer Mindestvorlaufzeit stimmt nicht");
        }
        if (!(executionTime.getTime().before(maxExecutionTime.getTime()))) {
            LOG.warn("Zeitraum von maximal 2 Wochen wurde nicht eingehalten");
            throw new UnsupportedOperationException("Zeitraum von maximal 2 Wochen wurde nicht eingehalten");
        }
        return sepaDueDate;
    }
}
