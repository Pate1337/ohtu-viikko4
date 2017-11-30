
package ohtu.intjoukkosovellus;

import java.util.ArrayList;
import java.util.List;

public class IntJoukko {

//    public final static int KAPASITEETTI = 5, // aloitustalukon koko
//                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int taulukonKoko;
    private int kasvatusKoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(5, 5);

    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, 5);

    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");//heitin vaan jotain :D
        }
        taulukonKoko = kapasiteetti;
        ljono = new int[taulukonKoko];
        //alustus turhaa, automaattisesti kaikki arvot taulukossa 0
        alkioidenLkm = 0;
        this.kasvatusKoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {

        if (taulukkoTyhja()) {
            ljono[0] = luku;
            alkioidenLkm++;
            return true;
        }
        if (!taulukkoSisaltaaLuvun(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (taulukkoTaynna()) {
                kasvataTaulukkoa();
            }
            return true;
        }
        return false;
    }
    
    public void kasvataTaulukkoa() {
        int[] apuTaulukko = ljono.clone();
        ljono = new int[taulukonKoko + kasvatusKoko];
        for (int i = 0; i < taulukonKoko; i++) {
            ljono[i] = apuTaulukko[i];
        }
        taulukonKoko = taulukonKoko + kasvatusKoko;
    }
    
    public boolean taulukkoTaynna() {
        if (alkioidenLkm == taulukonKoko) {
            return true;
        }
        return false;
    }
    
    public boolean taulukkoTyhja() {
        if (alkioidenLkm == 0) {
            return true;
        }
        return false;
    }

    public boolean taulukkoSisaltaaLuvun(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        if (!taulukkoSisaltaaLuvun(luku)) {
            return false;
        }
        List<Integer> apuLista = poistaLukuListalta(taulukkoListana(), luku);
        listaTaulukkona(apuLista);
        for (int i = 0; i < apuLista.size(); i++) {
            ljono[i] = apuLista.get(i);
        }
        alkioidenLkm--;
        return true;
    }
    
    public void listaTaulukkona(List<Integer> lista) {
        ljono = new int[taulukonKoko];
        for (int i = 0; i < lista.size(); i++) {
            ljono[i] = lista.get(i);
        }
    }
    
    public List<Integer> taulukkoListana() {
        List<Integer> apuLista = new ArrayList<>();
        for (int i = 0; i < alkioidenLkm; i++) {
            apuLista.add(ljono[i]);
        }
        return apuLista;
    }
    
    public List<Integer> poistaLukuListalta(List<Integer> lista, int poistettava) {
        List<Integer> apuLista = lista;
        for (int i = 0; i < lista.size(); i++) {
            if (apuLista.get(i) == poistettava) {
                apuLista.remove(i);
                break;
            }
        }
        return apuLista;
    }


    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + ljono[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i];
                tuotos += ", ";
            }
            tuotos += ljono[alkioidenLkm - 1];
            tuotos += "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }
 
        return z;
    }
    
    
        
}