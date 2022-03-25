package com.tiendas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tiendas.entity.Colaborador;
import com.tiendas.repository.ColaboradorRepository;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	@PostMapping("/buscarColaborador")
	public String buscarColaborador(HttpServletRequest request, Colaborador objColaborador, BindingResult result,
			RedirectAttributes attributes, Model model) {
		List<Colaborador> listaColaborador = colaboradorRepository.findByApellidoPaterno(objColaborador.getApellidoPaterno());
		
		if (listaColaborador == null) {
			model.addAttribute("mensaje", "El usuario no existe en la BD");			
			model.addAttribute("objColaborador", objColaborador);
		} else {
			model.addAttribute("objColaborador", objColaborador);
			model.addAttribute("listaColaborador",listaColaborador);
		}
			
		return "gestionarColaboradores";
	}
	
	@GetMapping("NuevoColaborador")
	public String NuevoColaborador(HttpServletRequest request, Model model) {
		Colaborador objColaborador = new Colaborador();
		model.addAttribute("objColaborador", objColaborador);
		return "nuevoColaborador";
	}
	
	@PostMapping("/registrarColaborador")
	public String registrarColaborador(HttpServletRequest request, Colaborador objColaborador, BindingResult result,
			RedirectAttributes attributes, Model model) {
		
		Colaborador colaboradorBD = colaboradorRepository.findByDni(objColaborador.getDni());

		if (colaboradorBD != null) {			
			model.addAttribute("mensaje", "El usuario ya existe");
			model.addAttribute("objColaborador", objColaborador);

		} else {
			colaboradorRepository.save(objColaborador);
			model.addAttribute("mensaje", "El usuario ha sido registrado con éxito");
			Colaborador obj = new Colaborador();
			model.addAttribute("objColaborador", obj);
		}

		return "redirect:/";
	}
	
}
