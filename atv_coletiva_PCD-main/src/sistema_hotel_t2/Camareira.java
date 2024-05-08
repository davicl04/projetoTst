package sistema_hotel_t2;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Camareira extends Thread {
    private int id;
    private List<Quarto> quartos;
    private final Lock lock;

    public Camareira(int id, List<Quarto> quartos) {
        this.id = id;
        this.quartos = quartos;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while (true) {
            for (Quarto quarto : quartos) {
                if (quarto.getLock().tryLock()) {
                    try {
                        if (!quarto.isChaveNaRecepcao() && !quarto.isOcupado()) {
                            System.out.println("Camareira " + id + " está limpando o quarto " + quarto.getNumero());
                            try {
                                Thread.sleep(1000); // Aguarda 1 segundo antes de limpar o quarto
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            try {
                                Thread.sleep(2000); // Simula a limpeza
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            quarto.setChaveNaRecepcao(true);
                            System.out.println("Camareira " + id + " terminou a limpeza do quarto " + quarto.getNumero());
                        }
                    } finally {
                        quarto.getLock().unlock();
                    }
                }
            }
        }
    }
}


