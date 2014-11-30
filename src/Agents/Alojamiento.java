/*
 * ALOJAMIENTO RECIBE MENSAJES DE CORTE INGLES A TRAVES DE UN ARRAYLIST(LUGAR,FECHAS).
 * ALOJAMIENTO ENVIA MENSAJES A CORTE INGLES A TRAVES DE UN STRING(RESERVACORRECTA?).
 * Hay que crear un cyclicbehaviour que reciba el mensaje, lo procese y luego envie la respuesta a CorteIngles.
 * */
package Agents;

import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.introspection.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Scanner;

public class Alojamiento {

	String[][] Mayo;
	//esto lo usaremos para recibir la info que nos envian desde CI desde usuario
	//private static final long serialVerisonUID = 1L;
	protected CyclicBehaviour cyclicBehaviour;
	
	//incializamos todos los meses a libres
//Inicializacion de numero de habitaciones del 1 hotel
	public void setup()
		{
			DFAgentDescription dfd = new DFAgentDescription();
			//dfd.setName(getAID()));
			ServiceDescription sd = new ServiceDescription();
			sd.setType("reservaViaje");
			sd.setName("Alojamiento");
			sd.addOntologies("ontology");
			sd.addLanguages(new SLCodec().getName());
			dfd.addServices(sd);
			
			try
			{
				DFService.register(this, dfd);
			}
			catch(Exception e)
			{
				System.out.println("Error en el registro del DFService");
			}
			
			cyclicBehaviour = new CyclicBehaviour()
//solo tiene que haber un cyclicbehaviour para recibir la info, procesarla y reenviarla
			{
				@Override
				public void action() 
				{
//RECIBE MENSAJES DE USUARIO PARA RESERVAR SU VIAJE.
					ACLMessage mensaje = 
							blockingReceive(
									MessageTemplate.and(
											MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
											MessageTemplate.MatchOntology("ontology")));
//con esto conseguimos transmitir lo que nos envia desde CI para reservar el viaje
								ArrayList<String> list =(ArrayList<String>) mensaje.getContentObject();
								 
	//ENVIAMOS MENSAJE PARA PREGUNTARLE A ALOJAMIENTO SI SE PUEDEN RESERVAR LAS FECHAS QUE HA INDICADO EL USUARIO.
	//nosotros usaremos la clase utils para enviar mesajes.
	//AQUI ENVIAREMOS SI HEMOS CONSEGUIDO RESERVAR LOS VIAJES 
					
				}
			};	
		}
	
	public void compruebafecha()
	{
		
	}
	
}
