package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.Random;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	@Override
	public void run() {

		Random random = new Random();
		boolean continuarReponiendo = true;

		while (continuarReponiendo) {
			try {
				synchronized (webCompra) {

					int entradasRestantes = EjemploTicketMaster.TOTAL_ENTRADAS - webCompra.entradasRestantes();


					if (entradasRestantes <= 0) {
						System.out.println("[Promotora] Se alcanzó el total de entradas. Cerrando la venta.");
						webCompra.cerrarVenta();
						continuarReponiendo = false; // Finaliza el ciclo
					} else if (!webCompra.hayEntradas()) {

						int entradasAReponer = Math.min(EjemploTicketMaster.REPOSICION_ENTRADAS, entradasRestantes);
						webCompra.reponerEntradas(entradasAReponer);
						System.out.println("[Promotora] Entradas repuestas: " + entradasAReponer +
								". Total actual en la web: " + webCompra.entradasRestantes());
					}
				}

				if (continuarReponiendo) {
					Thread.sleep(3000 + random.nextInt(8000));
				}

			} catch (InterruptedException e) {
				System.err.println("[Promotora] Hilo interrumpido.");
				continuarReponiendo = false;
			}
		}

		System.out.println("[Promotora] Ha finalizado la reposición de entradas.");
	}

}
