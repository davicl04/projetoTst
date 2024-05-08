package sistema_hotel_t2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Quarto> quartos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            quartos.add(new Quarto(i + 1));
        }

        List<Hospede> filaEspera = new ArrayList<>();
        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Recepcionista recepcionista = new Recepcionista(i + 1, quartos, filaEspera);
            recepcionistas.add(recepcionista);
            recepcionista.start();
        }

        for (int i = 0; i < 50; i++) {
            Hospede hospede = new Hospede(i + 1, recepcionistas.get(i % recepcionistas.size()));
            hospede.start();
        }

        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Camareira camareira = new Camareira(i + 1, quartos);
            camareiras.add(camareira);
            camareira.start();
        }
    }
}

