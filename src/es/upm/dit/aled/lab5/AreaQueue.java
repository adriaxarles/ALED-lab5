package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	private Queue<Patient> waitQueue = new LinkedList<>();

	// TODO
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
	}

	/*
	 * Y es que ahora ya no es suficiente con comprobar que se cumple la condición
	 * de ocupación (que numPatients sea menor que capacity), sino que también es
	 * necesario que el paciente que quiere entrar sea el primero de la cola de
	 * espera. Esta cola de espera está representada por el atributo waitQueue. A la
	 * hora de implementar AreaQueue, tenga en cuenta los siguientes consejos:
	 * 
	 * Queue no es una clase, sino un interfaz. Por tanto, no puede crear
	 * directamente objetos de Queue, sino que tendrá que usar su implementación:
	 * LinkedList.
	 * 
	 * Tendrá que utilizar los siguientes métodos del interfaz Queue:
	 * 
	 * • add(Patient p): Añade a p al final de la cola.
	 * 
	 * • remove(): Devuelve el Patient que se encuentra al principio de la cola y lo
	 * elimina, de tal forma que el siguiente elemento de la cola pasa a ser el
	 * primero.
	 * 
	 * • peek(): Devuelve el Patient que se encuentra al principio de la cola. La
	 * diferencia con remove() es que peek() no elimina (saca) al elemento de la
	 * cola, simplemente permite ver cuál es.
	 */

	@Override
	public synchronized void enter(Patient p) {
		boolean heEsperado = false;
		waitQueue.add(p);
		while (numPatients >= capacity || waitQueue.peek() != p) {
			try {
				if (!heEsperado) {
					waiting++;
					heEsperado = true;
				}
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if (heEsperado) {
			waiting--;
		}
		waitQueue.remove(); // remove(p) tb válido.
		notifyAll();
		numPatients++;
	}
	/*
	 * Ya me he asegurado que siempre quito el primero de todas formas
	 * 
	 * remove(), sin argumento, es de la interfaz Queue. Saca siempre el primero de
	 * la cola y lo devuelve.
	 * 
	 * remove(p), con argumento, es de Collection. Busca a p recorriendo la cola
	 * desde el principio y quita la primera aparición que encuentra, esté donde
	 * esté.
	 */
}
