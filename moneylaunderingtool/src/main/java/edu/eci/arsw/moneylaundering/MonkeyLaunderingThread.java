package edu.eci.arsw.moneylaundering;

public class MonkeyLaunderingThread extends Thread {
	
		private MoneyLaundering money;
		private int ini;
		private int fin;
		public boolean pause;
		
		public MonkeyLaunderingThread(MoneyLaundering money,int ini,int fin) {
			this.money=money;
			this.ini=ini;
			this.fin=fin;
			this.pause=false;
		}
		@Override
		public void run() {
			synchronized(money) {
				while(pause) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				money.processTransactionData(ini,fin);
			}	
		}
}
