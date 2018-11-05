package ServerSideChat.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import Utils.ConnectionParams;

public class ClientList extends CopyOnWriteArrayList<ClientData>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<ConnectionParams> getAllConnectedIps(){
		Iterator<ClientData> iterador = this.iterator();
		ArrayList<ConnectionParams> connectedIps = new ArrayList<ConnectionParams>();
		while(iterador.hasNext()){
			ClientData client = iterador.next();
			connectedIps.add(new ConnectionParams(client.getIp(), client.getPort()) );
		}
		return connectedIps;
		
	}
	public long updateConnectedList(long timePassed){
		Iterator<ClientData> iterator =this.iterator();
		long minRemainingTime = Long.MAX_VALUE;
		while(iterator.hasNext()){
			ClientData clientData = (ClientData)iterator.next();
			long newRemainingTime = clientData.getRemaningTimeToUpdate() -timePassed;
			//System.out.println("Atualizando o tempo até a proxima verificação do cliente: "+ clientData.getIp() + " tempo até a proxima atualização é: " + newRemainingTime);
			if(newRemainingTime > 0){
				if(newRemainingTime < minRemainingTime ){
					minRemainingTime = newRemainingTime;
				}
				clientData.setRemaningTimeToUpdate(newRemainingTime);
				this.set(this.indexOf(clientData), clientData);
			}else{
				this.remove(clientData);
			}
		}
		minRemainingTime = minRemainingTime > 10000 ? 10000 : minRemainingTime;
		return minRemainingTime;
		
	}
	public void keepAlive(ClientData client) {
		if(this.contains(client)){
			client.setRemaningTimeToUpdate(10000);
			this.set(this.indexOf(client), client);
		}else{
			this.add(client);
		}
		
	}

}
