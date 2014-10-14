package com.fis.esme.persistence;

// default package
// Generated Nov 27, 2013 2:12:26 PM by Hibernate Tools 3.4.0.CR1

/**
 * EsmeSmsRouting generated by hbm2java
 */
public class EsmeSmsRouting implements java.io.Serializable {

	private long routingId;
	private EsmeServices esmeServices;
	private EsmeShortCode esmeShortCode;
	private EsmeSmsCommand esmeSmsCommand;
	private EsmeCp esmeCp;

	public EsmeSmsRouting() {
	}

	public EsmeSmsRouting(long routingId) {
		this.routingId = routingId;
	}

	public EsmeSmsRouting(long routingId, EsmeServices esmeServices,
			EsmeShortCode esmeShortCode, EsmeSmsCommand esmeSmsCommand,
			EsmeCp esmeCp) {
		this.routingId = routingId;
		this.esmeServices = esmeServices;
		this.esmeShortCode = esmeShortCode;
		this.esmeSmsCommand = esmeSmsCommand;
		this.esmeCp = esmeCp;
	}

	public long getRoutingId() {
		return this.routingId;
	}

	public void setRoutingId(long routingId) {
		this.routingId = routingId;
	}

	public EsmeServices getEsmeServices() {
		return this.esmeServices;
	}

	public void setEsmeServices(EsmeServices esmeServices) {
		this.esmeServices = esmeServices;
	}

	public EsmeShortCode getEsmeShortCode() {
		return this.esmeShortCode;
	}

	public void setEsmeShortCode(EsmeShortCode esmeShortCode) {
		this.esmeShortCode = esmeShortCode;
	}

	public EsmeSmsCommand getEsmeSmsCommand() {
		return esmeSmsCommand;
	}

	public void setEsmeSmsCommand(EsmeSmsCommand esmeSmsCommand) {
		this.esmeSmsCommand = esmeSmsCommand;
	}

	public EsmeCp getEsmeCp() {
		return esmeCp;
	}

	public void setEsmeCp(EsmeCp esmeCp) {
		this.esmeCp = esmeCp;
	}
}
