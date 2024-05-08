package sistema_hotel_t2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    private int numero;
    private boolean ocupado;
    private boolean chaveNaRecepcao;
    private final Lock lock;

    public Quarto(int numero) {
        this.numero = numero;
        this.ocupado = false;
        this.chaveNaRecepcao = true; // Inicialmente a chave está na recepção
        this.lock = new ReentrantLock();
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public boolean isChaveNaRecepcao() {
        return chaveNaRecepcao;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }

    public int getNumero() {
        return numero;
    }

    public Lock getLock() {
        return lock;
    }

    public void sairHospede() {
        lock.lock();
        try {
            this.ocupado = false;
        } finally {
            lock.unlock();
        }
    }
}


