package ServerSideChat.v1;

public class ClientListManager extends Thread{
	ClientList clientList;
	public ClientListManager(ClientList clientList){
		this.clientList = clientList;
	}

	@Override
	public void run() {
		long inicio = 0;
		long fim = 0; 
		while(true){
			long realTimePassed = fim - inicio;
			long timeForNextUpdate =  clientList.updateConnectedList(realTimePassed);
			inicio = System.currentTimeMillis();
			try {
				this.sleep(timeForNextUpdate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fim = System.currentTimeMillis();
		}
	}

}
