package Interfaces;

import java.util.Vector;

public class SendHome<T extends NotHomeless<T>>{
	
	public static void sendHome(Vector<NotHomeless<?>> employees) {
		for(int i = 0; i < employees.size(); i++) {
			employees.get(i).goHome();
		}
	}
}
