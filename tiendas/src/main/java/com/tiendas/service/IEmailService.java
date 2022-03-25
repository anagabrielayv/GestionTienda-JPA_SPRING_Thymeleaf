package com.tiendas.service;

import com.tiendas.bean.EmailBean;

public interface IEmailService {
	boolean enviarEmail(EmailBean emailBean);

	boolean enviarEmailRecuperarContrasenha(EmailBean emailBean);
	
	boolean enviarEmailAperturaTienda(EmailBean emailBean);
	
}
