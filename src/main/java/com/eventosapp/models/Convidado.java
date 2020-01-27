package com.eventosapp.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;


@Entity
public class Convidado {
	
	//sera a chave primaria do meu banco de dados e não permite que seja salvo em branco
	@Id 
	@NotEmpty
	private String rg;
	
	//não deixa salvar em branco
	@NotEmpty
	private String nomeConvidado;
	
	//many to one quer dizer muitos para 1
	@ManyToOne
	private Evento evento;
	
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getNomeConvidado() {
		return nomeConvidado;
	}
	public void setNomeConvidado(String nomeConvidado) {
		this.nomeConvidado = nomeConvidado;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
}
