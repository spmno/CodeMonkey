package com.dawnstep.codemonkey.service.data;

abstract class DatabaseProvider {
	private DataListener databaseListener;
	public void getData(DataListener listener) {
		databaseListener = listener;
		GetDataThread getDataThread = new GetDataThread();
		getDataThread.start();
	}
	
	protected void getDataImp() {
		saveData();
		databaseListener.dataArrived();
	}
	
	class GetDataThread extends Thread {
		
		@Override
		public void run() {
			getDataImp();
		}
	}
	
	protected abstract void saveData();
}
