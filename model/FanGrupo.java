package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.Random;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}

	@Override
	public  void run() {


		Random random = new Random();
		boolean seguirComprando = true;

			while (entradasCompradas < EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
				try {
					seguirComprando = true;
						System.out.println("[Fan " + numeroFan + "] Intentando comprar una entrada...");
						boolean exito = webCompra.comprarEntrada();

						if (exito) {
							entradasCompradas++;
							System.out.println("[Fan " + numeroFan + "] ¡Entrada comprada! Total compradas: " + entradasCompradas);
						} else if (!webCompra.hayEntradas()) {

							synchronized (webCompra){
							System.out.println("[Fan " + numeroFan + "] No hay más entradas disponibles. Dejando de intentar.");
							webCompra.wait();
							seguirComprando = false;
							}

						}
					System.out.println("[Fan " + numeroFan + " zzzzzz");
						Thread.sleep(1000 + random.nextInt(3000));





				} catch (InterruptedException e) {
					System.err.println("[Fan " + numeroFan + "] Hilo interrumpido.");
					seguirComprando = false;
				}catch (Exception e){
					System.out.println(e);
				}
			}

			if (webCompra.hayEntradas()) {
				System.out.println("[Fan " + numeroFan + "] Terminó de comprar con " + entradasCompradas + " entradas.");
			} else {
				System.out.println("[Fan " + numeroFan + "] Venta cerrada. No se compraron más entradas.");
			}
	}

	public void muestraEntradasCompradas() {

		System.out.println("[Fan " + numeroFan + "] Total de entradas compradas: " + entradasCompradas);
		//TODO: muestra las entradasCompradas en el log
	}


}
