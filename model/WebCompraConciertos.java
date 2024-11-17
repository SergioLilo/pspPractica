package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb{

	private boolean venta = false;
	int entradasDisponibles=0;
	public WebCompraConciertos() {
		super();
	}


	@Override
	public boolean comprarEntrada() {
		synchronized(this) {
			if (venta || entradasDisponibles <= 0) {
				// Log: No hay entradas disponibles o la venta está cerrada.
				System.out.println("[WebCompraConciertos] No se puede comprar entrada: " +
						(venta ? "Venta cerrada." : "No hay entradas disponibles."));
				return false;
			}
			entradasDisponibles--;
			// Log: Entrada comprada, cuántas quedan.
			System.out.println("[WebCompraConciertos] Entrada comprada. Entradas restantes: " + entradasDisponibles);
			notifyAll();
			return true;
		}
    }


	@Override
	public int reponerEntradas(int numeroEntradas) {
		if (venta) {

			System.out.println("[WebCompraConciertos] No se puede reponer: Venta cerrada.");
			return entradasDisponibles;
		}
		entradasDisponibles += numeroEntradas;

		System.out.println("[WebCompraConciertos] Entradas repuestas: " + numeroEntradas +
				". Entradas totales ahora: " + entradasDisponibles);

		return entradasDisponibles;

	}


	@Override
	public void cerrarVenta() {
		// TODO Auto-generated method stub
		venta = true;
		// Log: Venta cerrada.
		System.out.println("[WebCompraConciertos] La venta se ha cerrado.");
		notifyAll(); // Notificar a los hilos que esperan por cambios.
	}


	@Override
	public boolean hayEntradas() {
		// TODO Auto-generated method stub
		return !venta && entradasDisponibles > 0;
	}


	@Override
	public int entradasRestantes() {
		// TODO Auto-generated method stub
		return  entradasDisponibles;
	}



}
