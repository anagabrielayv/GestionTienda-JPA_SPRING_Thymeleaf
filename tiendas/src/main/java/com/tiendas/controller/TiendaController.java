package com.tiendas.controller;

import java.util.List;

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
import com.tiendas.entity.Colaborador;
import com.tiendas.entity.Tienda;
import com.tiendas.repository.ClienteRepository;
import com.tiendas.repository.ColaboradorRepository;
import com.tiendas.repository.TiendaRepository;
import com.tiendas.service.IEmailService;

@Controller
@RequestMapping("/tienda")
public class TiendaController {
	@Autowired
	TiendaRepository tiendaRepository;
	
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	@Autowired
	IEmailService emailService;
	
	@Autowired
	ClienteRepository clienteRepository;

	
	@PostMapping("/buscarTienda")
	public String buscarTienda(HttpServletRequest request, Tienda objTienda, BindingResult result,
			RedirectAttributes attributes, Model model) {
		
		List<Tienda> listaTiendas = tiendaRepository.findByDistrito(objTienda.getDistrito());
		
		if (listaTiendas == null) {
			model.addAttribute("mensaje", "No hay tienda registrada en ese distrito");			
			model.addAttribute("objTienda", objTienda);
		} else {
			model.addAttribute("objTienda", objTienda);
			model.addAttribute("listaTiendas",listaTiendas);
		}
			
		return "gestionarTiendas";
	}
	
	@PostMapping("/buscarTiendaAsignar")
	public String buscarTiendaAsignar(HttpServletRequest request, Tienda objTienda, BindingResult result,
			RedirectAttributes attributes, Model model) {
		
		List<Tienda> listaTiendas = tiendaRepository.findByDistrito(objTienda.getDistrito());	
		
		if (listaTiendas == null) {
			model.addAttribute("mensaje", "No hay tienda registrada en ese distrito");			
			model.addAttribute("objTienda", objTienda);
		} else {
			model.addAttribute("objTienda", objTienda);
			model.addAttribute("listaTiendas",listaTiendas);
		}
			
		return "asignarJefeTienda";
	}
	
	@GetMapping("NuevaTienda")
	public String NuevaTienda(HttpServletRequest request, Model model) {
		Tienda objTienda = new Tienda();
		model.addAttribute("objTienda", objTienda);
		return "nuevaTienda";
	}
	
	@PostMapping("/registrarTienda")
	public String registrarTienda(HttpServletRequest request, Tienda objTienda, BindingResult result,
			RedirectAttributes attributes, Model model) {
		
			tiendaRepository.save(objTienda);			
			model.addAttribute("mensaje", "La Tienda se registró con éxito");
			
			List <Cliente> listaClientes = clienteRepository.findAll();
			
			for(int i=0;i<listaClientes.size();i++) {
				EmailBean datosEmail = new EmailBean();
				datosEmail.setsNumeroCertificado("N0001");
				datosEmail.setxCertificado(null);
				datosEmail.setsEmailTo(listaClientes.get(i).getUsuario().getCorreo());
				datosEmail.setsSubject("Apertura de Nueva Tienda: "+ objTienda.getDistrito());
				datosEmail.setNombreSolicitante( listaClientes.get(i).getNombres() + " " + listaClientes.get(i).getApellidoPaterno() + " " + listaClientes.get(i).getApellidoMaterno());
				emailService.enviarEmailAperturaTienda(datosEmail); // LLamada para el envio del correo
			}
				
			Tienda obj = new Tienda();
			model.addAttribute("objTienda", obj);		

		return "nuevaTienda";
	}
	
	@GetMapping("/cargarAsignacion/{id}")
	public String cargarAsignacion(@PathVariable("id") int id, 
			RedirectAttributes attributes, Model model) {
			
		Tienda objTienda = tiendaRepository.findById(id);
		model.addAttribute("objTienda", objTienda);		
		
		//Validar si la tienda cuenta con Colaborador Asignado
		List<Colaborador> listaColaboradores = colaboradorRepository.findAll();
		model.addAttribute("listaColaboradores", listaColaboradores);		
		return "asignarJefe";
				
	}
	
	
	@PostMapping("/grabarAsignacion")
	public String grabarAsignacion(HttpServletRequest request, 
			@RequestParam("idColaborador") int idColaborador, 
			Tienda objTienda, BindingResult result,
			RedirectAttributes attributes, Model model) {
		
		if (objTienda.getColaborador()!=null) {
		
		}
		
		Tienda tienda = objTienda;		
		Colaborador colaborador = colaboradorRepository.findById(idColaborador);
		tienda.setColaborador(colaborador);			
		tiendaRepository.save(tienda);				
		
		
		return "redirect:/";
				
	}
}
