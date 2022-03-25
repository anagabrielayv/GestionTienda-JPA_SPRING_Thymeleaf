package com.tiendas.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tiendas.bean.EmailBean;
import com.tiendas.entity.Cliente;
import com.tiendas.entity.Usuario;
import com.tiendas.repository.ClienteRepository;
import com.tiendas.repository.UsuarioRepository;
import com.tiendas.service.IEmailService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	IEmailService emailService;

	@PostMapping("/validarUsuario")
	public String validarUsuario(HttpServletRequest request, Usuario objUsuario, Model model,
			RedirectAttributes attributes) {
		Usuario usuarioBD = usuarioRepository.findByCorreoAndPassword(objUsuario.getCorreo(), objUsuario.getPassword());
		if (usuarioBD != null && usuarioBD.getActivo() == 1) {
			/* Vamos a crear una variable en sesion */
			request.getSession().setAttribute("id_usuario", usuarioBD.getId());
			return "principal";
		} else {
			/* Aqui el usuario existe pero no esta activo */
			if (usuarioBD != null) {
				model.addAttribute("mensaje",
						"El usuario no se encuentra activo. Revise su correo para realizar la activación");
			} else {
				model.addAttribute("mensaje", "Usuario y/o contraseña incorrecta");
			}
		}
		Usuario objUsuario1 = new Usuario();
		model.addAttribute("objUsuario", objUsuario1);
		return "index.html";
	}

	@PostMapping("/registrarUsuario")
	public String registrarUsuario(HttpServletRequest request, Usuario objUsuario, BindingResult result,
			RedirectAttributes attributes, Model model) {
		Usuario usuarioBD = usuarioRepository.findByCorreo(objUsuario.getCorreo());

		if (usuarioBD != null) {
			model.addAttribute("mensaje", "El usuario ya existe");
			model.addAttribute("objUsuario", objUsuario);

		} else {
			objUsuario.setActivo(0);
			usuarioRepository.save(objUsuario);
			
			Cliente cliente = new Cliente();
			cliente.setNombres(objUsuario.getNombre());
			cliente.setApellidoPaterno(objUsuario.getApePaterno());
			cliente.setApellidoMaterno(objUsuario.getApeMaterno());
			cliente.setDni("4444444");
			cliente.setUsuario(objUsuario);
			clienteRepository.save(cliente);

			model.addAttribute("mensaje", "El usuario ha sido registrado con éxito");
			// agregar correo de activacion de cuenta
			// Para enviarle el correo de activacion de su cuenta
			EmailBean datosEmail = new EmailBean();
			datosEmail.setsNumeroCertificado("N0001");
			datosEmail.setxCertificado(null);
			datosEmail.setsEmailTo(objUsuario.getCorreo());
			datosEmail.setsSubject("Activacion de Cuenta");
			datosEmail.setNombreSolicitante(
					objUsuario.getNombre() + " " + objUsuario.getApePaterno() + " " + objUsuario.getApeMaterno());
			emailService.enviarEmail(datosEmail); // LLamada para el envio del correo
			Usuario objUsuario2 = new Usuario();
			model.addAttribute("objUsuario", objUsuario2);

		}

		return "nuevoUsuario";
	}

	@GetMapping("/mostrarNuevoUsuario")
	public String mostrarNuevoUsuario(HttpServletRequest request, Model model) {
		Usuario objUsuario = new Usuario();
		model.addAttribute("objUsuario", objUsuario);
		return "nuevoUsuario";
	}

	@GetMapping("/mostrarRecuperarContrasenha")
	public String mostrarRecuperarContrasenha(HttpServletRequest request, Model model) {
		Usuario objUsuario = new Usuario();
		model.addAttribute("objUsuario", objUsuario);
		return "recuperarContrasenha";
	}

	@PostMapping("/recuperarContrasenha")
	public String recuperarContrasenha(HttpServletRequest request, Usuario objUsuario, BindingResult result,
			RedirectAttributes attributes, Model model) {
		Usuario objUsuarioBD = usuarioRepository.findByCorreo(objUsuario.getCorreo());
		if (objUsuarioBD == null) {
			model.addAttribute("mensaje", "El correo ingresado no existe en la BD");
			model.addAttribute("objUsuario", objUsuario);
		} else {
			model.addAttribute("objUsuario", objUsuarioBD);
			EmailBean emailBean = new EmailBean();
			emailBean.setClave(objUsuarioBD.getPassword());
			emailBean.setNombreSolicitante(
					objUsuarioBD.getNombre() + " " + objUsuarioBD.getApePaterno() + " " + objUsuarioBD.getApeMaterno());
			emailBean.setsEmailTo(objUsuarioBD.getCorreo());
			emailBean.setsSubject("Recuperar Contraseña");
			emailBean.setsNumeroCertificado("N0001");
			emailBean.setxCertificado(null);
			emailService.enviarEmailRecuperarContrasenha(emailBean);
			model.addAttribute("mensaje", "Se le ha enviado un correo de recuperacion de contraseña");
		}
		return "recuperarContrasenha";
	}

	@GetMapping("/activarCuenta/{email}")
	public String grabarReserva(@PathVariable("email") String email, RedirectAttributes attributes,
			HttpServletRequest request, Model model) {
		Usuario objUsuarioBD = usuarioRepository.findByCorreo(email);
		objUsuarioBD.setActivo(1);
		usuarioRepository.save(objUsuarioBD); // Este save hace un update en realidad !!!
		model.addAttribute("mensaje", "Su cuenta ha sido activada. Ya puede ingresar al sistema");
		Usuario objUsuario = new Usuario();
		model.addAttribute("objUsuario", objUsuario);
		return "index.html";
	}

	@PostMapping("/modificarContrasenha")	
	public String modificarContrasenha(HttpServletRequest request,@RequestParam(name = "contra1") String contra1,@RequestParam(name = "contra2") String contra2, Usuario objUsuario, BindingResult result, RedirectAttributes attributes, Model model) {
		
		if (contra1.equals(contra2)) {		
			Usuario objUsuarioBD = usuarioRepository.findByCorreo(objUsuario.getCorreo());
			objUsuarioBD.setPassword(contra2);
			usuarioRepository.save(objUsuarioBD);
			model.addAttribute("mensaje", "Contraseña actualizada");			
			Usuario objUsuario1 = new Usuario();
			model.addAttribute("objUsuario", objUsuario1);			
			return "redirect:/";
		}			
		else {
			model.addAttribute("mensaje", "Las contraseñas no coinciden");
			System.out.println("terrible oremos");			
		}			
		return "redirect:/mostrarModificarContrasenha/"+objUsuario.getCorreo();
		
	}
	

}
