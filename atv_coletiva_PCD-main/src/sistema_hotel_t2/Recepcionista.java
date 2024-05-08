package sistema_hotel_t2;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Recepcionista extends Thread {
    private int id;
    private List<Quarto> quartos;
    private List<Hospede> filaEspera;
    private final Lock lock;
    private static final int MAX_TENTATIVAS = 2;

    public Recepcionista(int id, List<Quarto> quartos, List<Hospede> filaEspera) {
        this.id = id;
        this.quartos = quartos;
        this.filaEspera = filaEspera;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while (true) {
            if (!filaEspera.isEmpty()) {
                lock.lock();
                try {
                    Hospede hospede = filaEspera.remove(0);
                    boolean hospedeRecebido = receberHospede(hospede);
                    if (!hospedeRecebido && hospede.getTentativas() >= MAX_TENTATIVAS) {
                        System.out.println("Hóspede " + hospede.getId() + " não conseguiu alugar um quarto após duas tentativas. Deixando uma reclamação e indo embora.");
                        continue;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public boolean receberHospede(Hospede hospede) {
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.getLock().tryLock()) {
                    try {
                        if (!quarto.isOcupado() && quarto.isChaveNaRecepcao()) {
                            quarto.setOcupado(true);
                            quarto.setChaveNaRecepcao(false);
                            hospede.setQuarto(quarto);
                            System.out.println("Recepcionista " + id + " alocou hóspede " + hospede.getId() + " no quarto " + quarto.getNumero());
                            try {
                                Thread.sleep(1000); // Aguarda 1 segundo após alocar um quarto
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                    } finally {
                        quarto.getLock().unlock();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        System.out.println("Recepcionista " + id + " não encontrou quartos disponíveis. Hóspede " + hospede.getId() + " aguardando.");
        filaEspera.add(hospede);
        return false;
    }
}




