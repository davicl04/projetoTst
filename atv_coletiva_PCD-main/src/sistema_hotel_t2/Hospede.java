package sistema_hotel_t2;

public class Hospede extends Thread {
    private int id;
    private Recepcionista recepcionista;
    private Quarto quarto;
    private int tentativas;
    private static final int MAX_TENTATIVAS = 2;

    public Hospede(int id, Recepcionista recepcionista) {
        this.id = id;
        this.recepcionista = recepcionista;
    }

    @Override
    public void run() {
        tentativas = 0;
        while (tentativas < MAX_TENTATIVAS) {
            System.out.println("Hóspede " + id + " chegou ao hotel (tentativa " + (tentativas + 1) + ").");
            try {
                Thread.sleep(1000); // Aguarda 1 segundo antes de tentar alugar um quarto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recepcionista.receberHospede(this);
            if (quarto != null) {
                try {
                    Thread.sleep(2000); // Hóspede fica no quarto por 2 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                quarto.sairHospede();
                System.out.println("Hóspede " + id + " saiu do quarto " + quarto.getNumero());
                break;
            }
            tentativas++;
        }
        if (quarto == null) {
            System.out.println("Hóspede " + id + " não conseguiu alugar um quarto após duas tentativas. Deixando uma reclamação e indo embora.");
        }
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public int getTentativas() {
        return tentativas;
    }
}


