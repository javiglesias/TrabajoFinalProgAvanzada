package Agents;

import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.introspection.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.Scanner;

//el corte ingles sera el server de nuestra aplicacion
public class CorteIngles {
	@SuppressWarnings("unused")
	private static final long serialVerisonUID = 1L;
	protected CyclicBehaviour cyclicBehaviour;
	Scanner sc = new Scanner(System.in);
	String message;
	
		@SuppressWarnings("serial")
		public void setup()
		{

			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			//dfd.setName("avellana");
			sd.setType("reservaViaje");
			sd.setName("CorteIngles");
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
					
					try {
						System.out.println("mensaje: "
								+mensaje.getContentObject());
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//ENVIAMOS MENSAJE PARA PREGUNTARLE A ALOJAMIENTO SI SE PUEDEN RESERVAR LAS FECHAS QUE HA INDICADO EL USUARIO.
					//nosotros usaremos la clase utils para enviar mesajes.
					System.out.println("Escriba el mensaje: ");
					message = sc.nextLine();
					//aqui se enviara la Ciudad,Hotel,Dias que se quieren reservar
					Utils.enviarMensaje(this.myAgent, "Alojamiento", message);
				}
			};
			addBehaviour(cyclicBehaviour);
			
//AQUI DEFINIMOS LOS MENSAJES QUE RECIBIRA CORTEINGLES REFERENTE A OCIO
			sd.setType("reservaViaje");
			dfd.addServices(sd);
			
			cyclicBehaviour = new CyclicBehaviour(this)
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
					
//ENVIAMOS MENSAJE PARA PREGUNTARLE A OCIO PARA QUE NOS DEVUELVA LAS ACTIVIDADES DISPONIBLES.
					//nosotros usaremos la clase utils para enviar mesajes.
					System.out.println("Escriba el mensaje: ");
					message = sc.nextLine();
					//aqui se enviara la Ciudad,Hotel,Dias que se quieren reservar
					Utils.enviarMensaje(this.myAgent, "actividadesOcio", message);
				}
			};
			addBehaviour(cyclicBehaviour);
		}
}
