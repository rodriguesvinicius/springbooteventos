package com.eventosapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventosapp.models.Convidado;
import com.eventosapp.models.Evento;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {

	// A anotação abaixo está fazendo um injeção de dependencia
	// ou seja toda vez que precisarmos utlizar a interface abaixo sera criado uma
	// nova instância
	@Autowired
	private EventoRepository er;
	@Autowired
	private ConvidadoRepository cr;

	// Mapeando a rota onde irei chama o template formEvento
	@RequestMapping(value = "/cadastroEvento", method = RequestMethod.GET)
	public String formEvento() {
		return "Eventos/formEvento";
	}

	// Fazendo uma persistencia no banco de dados
	@RequestMapping(value = "/cadastroEvento", method = RequestMethod.POST)
	public String formEvento(@Valid Evento evento , BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique todos os campos");
			return "redirect:/cadastroEvento";
		}
		// usa uma implementação da interface EventoRepository
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso");
		return "redirect:/eventos";
	}

	// Metodo que retorna as listas de eventos existentes dentro do banco de dados
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		// Objeto que ira rederizar a pagina de acorda com os dados persistentes do
		// banco de dados
		ModelAndView mv = new ModelAndView("listaEventos");

		// Iterable é uma interface que determina que uma coleção pode ter seus
		// elementos alcançados
		// por uma estrutura foreach. A interface só possui um método:
		Iterable<Evento> eventos = er.findAll();
		// equivalente ao res.render passando a promisse para ser imprimida e rederizada
		// na pagina!!!
		mv.addObject("eventos", eventos);

		return mv;
	}

	// rota que ira fazer a exclusão de um evento
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo){
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	// rota abaixo faz uma busca detalhada por codigo unico
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		// instanciando a model de evento
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("Eventos/detalhesEvento");
		mv.addObject("evento", evento);

		// passando um lista de convidados de acordo com os eventos registrados no banco
		// de dados
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);

		return mv;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado,
			BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{codigo}";
		}
		// instanciando a model de evento
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso");
		return "redirect:/{codigo}";
	}
	
	// rota que vai deletar um convidado do evento
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg){
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}

}
