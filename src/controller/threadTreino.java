
package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class threadTreino extends Thread {
	private Semaphore semaforo;
	private int id;
	private int equipe;
	private int melhorVolta;
	private static int cont;
	private static int escuderias[][] = new int[14][3];

	public threadTreino(int id, int equipe, Semaphore semaforo) {
		this.id = id;
		this.semaforo = semaforo;
		this.equipe = equipe;
	}

	@Override
	public void run() {
		try {
			semaforo.acquire();
			volta();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaforo.release();
		}

		salvarTempo();

		if (cont == 14) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ordenarResultados();
			exibirResultados();
			exibirGrid();
		}
	}

	public int randomizar(int tMax, int tMin) {
		Random r = new Random();
		int tempo = r.nextInt(tMax);
		while (tempo < tMin) {
			tempo = r.nextInt(tMax);
		}
		return tempo;
	}

	private void volta() {
		melhorVolta = 10000;
		System.out.println("Equipe " + equipe + " Carro#" + id + " entrou na pista...");

		for (int i = 1; i <= 3; i++) {
			int volta = randomizar(1000, 3000);

			try {
				sleep(volta);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (volta < melhorVolta) {
				melhorVolta = volta;
			}

			System.out.println(
					"Equipe " + equipe + " Carro#" + id + " completou a volta " + i + " em " + (volta / 1000) + "min");
		}
		System.out.println("Equipe " + equipe + " Carro#" + id + " completou as 3 voltas!!" + "Melhor tempo: "
				+ (melhorVolta / 1000) + "min");
	}

	private void salvarTempo() {
		escuderias[cont][0] = id;
		escuderias[cont][1] = equipe;
		escuderias[cont][2] = melhorVolta;
		cont++;
	}

	private void ordenarResultados() {
		int auxId, auxEqp, auxTmp;

		for (int i = 0; i <= 13; i++) {
			for (int j = 0; j <= 12; j++) {
				if (escuderias[i][2] < escuderias[j][2]) {
					auxId = escuderias[i][0];
					auxEqp = escuderias[i][1];
					auxTmp = escuderias[i][2];

					escuderias[i][0] = escuderias[j][0];
					escuderias[i][1] = escuderias[j][1];
					escuderias[i][2] = escuderias[j][2];

					escuderias[j][0] = auxId;
					escuderias[j][1] = auxEqp;
					escuderias[j][2] = auxTmp;
				}
			}
		}
	}

	private void exibirResultados() {
		System.out.println("\n----------------\nMelhores tempos:");
		for (int i = 0; i <= 13; i++) {
			System.out.println("\n" + (i + 1) + "º colocado:\nCarro:        " + escuderias[i][0] + "\nEquipe:       "
					+ escuderias[i][1] + "\nMelhor tempo: " + (escuderias[i][2] / 1000) + "min");
		}
	}

	private void exibirGrid() {
		System.out.println("\n\nGRID DE LARGADA\n carro-equipe\n---------------");
		for (int i = 0; i <= 13; i = i + 2) {
			System.out.println("   " + escuderias[i][0] + "-" + escuderias[i][1] + "\n         "
					+ escuderias[(i + 1)][0] + "-" + escuderias[(i + 1)][1]);
		}
	}

}
