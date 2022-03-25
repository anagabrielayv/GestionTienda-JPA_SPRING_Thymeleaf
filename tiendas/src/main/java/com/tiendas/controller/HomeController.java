package com.tiendas.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tiendas.entity.Usuario;
import com.tiendas.entity.Colaborador;
import com.tiendas.entity.Tienda;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		Usuario objusuario = new Usuario();
		model.addAttribute("objUsuario", objusuario);
		return "index";
	}
	
	@GetMapping("/mostrarGestionarTienda")
	public String mostrarGestionarTienda(HttpServletRequest request, Model model) {
		Tienda objTienda = new Tienda();
		model.addAttribute("objTienda", objTienda);
		return "gestionarTiendas";
	
	}
	@GetMapping("/mostrarGestionarColaborador")
	public String mostrarGestionarColaborador(HttpServletRequest request, Model model) {
		Colaborador objColaborador = new Colaborador();
		model.addAttribute("objColaborador", objColaborador);
		return "gestionarColaboradores";
	
	}
	
	@GetMapping("/mostrarAsignarTienda")
	public String mostrarAsignarTienda(HttpServletRequest request, Model model) {
		Tienda objTienda = new Tienda();
		model.addAttribute("objTienda", objTienda);
		return "asignarJefeTienda";
	
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		if(request.getSession().getAttribute("id_usuario")!=null){
			request.getSession().removeAttribute("id_usuario");
		}
		return "redirect:/";
	}
	
	@GetMapping("/mostrarModificarContrasenha/{correo}")
	public String mostrarModificarContrasenha(@PathVariable("correo") String correo, RedirectAttributes attributes, HttpServletRequest request, Model model) throws ParseException {
		Usuario objusuario = new Usuario();
		objusuario.setCorreo(correo);
		model.addAttribute("objUsuario", objusuario);
		return "modificarContrasenha";
	
	}
}
