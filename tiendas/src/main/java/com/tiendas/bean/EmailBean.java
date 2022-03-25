package com.tiendas.bean;

public class EmailBean {
	private String sNumeroCertificado;
	private byte[] xCertificado;
	private String sSubject;
	private String sEmailTo;
	private String nombreSolicitante;
	private String clave;
	public String getsNumeroCertificado() {
		return sNumeroCertificado;
	}
	public void setsNumeroCertificado(String sNumeroCertificado) {
		this.sNumeroCertificado = sNumeroCertificado;
	}
	public byte[] getxCertificado() {
		return xCertificado;
	}
	public void setxCertificado(byte[] xCertificado) {
		this.xCertificado = xCertificado;
	}
	public String getsSubject() {
		return sSubject;
	}
	public void setsSubject(String sSubject) {
		this.sSubject = sSubject;
	}
	public String getsEmailTo() {
		return sEmailTo;
	}
	public void setsEmailTo(String sEmailTo) {
		this.sEmailTo = sEmailTo;
	}
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
}
