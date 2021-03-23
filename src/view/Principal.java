package view;

import java.util.concurrent.Semaphore;

import controller.threadTreino;

public class Principal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(5);
		for (int id = 1; id <= 14; id++)
		{
			for (int equipe = 1; equipe < 8; equipe ++)
			{
				Thread t = new threadTreino(id, equipe, semaforo);
				t.start();				
			}
		}
	}

}
