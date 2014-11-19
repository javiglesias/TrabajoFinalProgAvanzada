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
	@SuppressWarnings("rawtypes") 
	ArrayList list = new ArrayList<String>();
	String[] habitaciones;
	String[][] Mayo;
	//esto lo usaremos para recibir la info que nos envian desde CI desde usuario
	private static final long serialVerisonUID = 1L;
	protected CyclicBehaviour cyclicBehaviour;
	Scanner sc = new Scanner(System.in);
	String message;
	
	Mayo[0][0]="Vigo";
	Mayo[1][0]="Plasencia";
	Mayo[2][0]="Madrid";
	Mayo[3][0]="Madrid";
	Mayo[4][0]="Madrid";
	//incializamos todos los meses a libres
	for(int i =1; i<=31; i++)
	{
		for(int j=0;j<=4;j++)
		{
			Mayo[j][i]="0";
		}
	}
	habitaciones[0]="2";
	habitaciones[1]="1";
	habitaciones[2]="2";
	habitaciones[3]="1";
	habitaciones[4]="3";
	public void setup()
		{
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
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
			
			cyclicBehaviour = new CyclicBehaviour(this)
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
								list = (ArrayList<String>)mensaje.getContentObject();
					
	//ENVIAMOS MENSAJE PARA PREGUNTARLE A ALOJAMIENTO SI SE PUEDEN RESERVAR LAS FECHAS QUE HA INDICADO EL USUARIO.
	//nosotros usaremos la clase utils para enviar mesajes.
	//AQUI ENVIAREMOS SI HEMOS CONSEGUIDO RESERVAR LOS VIAJES 
					
					if(/*el registro ha sido correcto*/)
					{
						message="Reserva realizada con exito.";
						Utils.enviarMensaje(this.myAgent, "reservaOk", message);
					}
	///O NO LO HEMOS CONSEGUIDO
					if(/*ha sido el registro incorrecto*/)
					{
						message="La reserva no ha podido completarse.";
						Utils.enviarMensaje(this.myAgent, "noReserva", message);
					}
				}
			};
			addBehaviour(cyclicBehaviour);		
		}

	}