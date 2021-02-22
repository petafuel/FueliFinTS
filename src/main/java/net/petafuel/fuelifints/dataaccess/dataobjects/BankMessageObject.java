package net.petafuel.fuelifints.dataaccess.dataobjects;

public class BankMessageObject
{
	private String subject;
	private String message;
	public BankMessageObject(String subject,String message)
	{
		this.subject = (subject != null) ? subject : "";
		this.message = (message != null) ? message : "";
	}

	public String getMessage()
	{
		return message;
	}

	public String getSubject()
	{
		return subject;
	}
}
