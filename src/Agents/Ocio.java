/*
 * OCIO RECIBE MENSAJES DE CORTE INGLES A TRAVES DE UN ARRAYLIST(CIUDAD,DIAS).
 * OCIO ENVIA MENSAJES A CORTE INGLES A TRAVES DE UN STRING.
 * Hay que crear un cyclicbehaviour para que reciba los mensajes de CorteIngles y reenvie la info de actividades.
 * */

package Agents;

import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Scanner;

public class Ocio {
	@SuppressWarnings("rawtypes")
	ArrayList list = new ArrayList<String>();
	private static final long serialVerisonUID = 1L;
	protected CyclicBehaviour cyclicBehaviour;
	Scanner sc = new Scanner(System.in);
	String message;
		public void setup()
		{
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			//dfd.setName("avellana");
			sd.setType("actividadesOcio");
			sd.setName("Ocio");
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
//Solo hay que crear un cyclicbehaviour para los mensajes de CI y enviar.
	{
		@SuppressWarnings("unchecked")
		@Override
		public void action() 
		{
//RECIBE MENSAJES DE USUARIO PARA PEDIR INFO SOBRE EL OCIO.
			ACLMessage mensaje = 
					blockingReceive(
							MessageTemplate.and(
									MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
									MessageTemplate.MatchOntology("ontology")));
//CON ESTO RECIBIMOS LA INFO SOBRE LAS ACTIVIDADES QUE DEBEMOS INFORMAR A CI.
			list = (ArrayList<String>)mensaje.getContentObject();
//nosotros usaremos la clase utils para enviar mesajes.
			message="Actividades de ocio: ";
			Utils.enviarMensaje(this.myAgent, "infOcio", message);
		}
	};
	addBehaviour(cyclicBehaviour);
		}
}
