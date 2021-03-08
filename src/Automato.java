import java.util.ArrayList;
import java.util.List;

public class Automato {
    List<String> alfabeto = new ArrayList<>();
    List<String> estados = new ArrayList<>();
    List<String> estadoInicial = new ArrayList<>();
    List<String> estadoFinal = new ArrayList<>();
    List<Transicao> transicoes = new ArrayList<>();

    public Automato(){ }

    public boolean criaEstado(String estado, boolean estadoInicio, boolean estadoFim){

        //retorna true caso contenha
        if(estados.contains(estado)){ return false; }

        if (estadoInicial != null && estadoInicio == true){ return false; }

        //adiciona o estado no array
        estados.add(estado);

        //torna o estado um estadoInicial
        if(estadoInicio == true){
            estadoInicial.add(estado);
        }

        //torna o estado um estadoFinal
        if(estadoFim == true){
            estadoFinal.add(estado);
        }

        return true;
    }

    public boolean criaTransicao(String origem, String destino, String simbolo){
        //(origem, simbolo) --> destino.

        //verifica se a origem está no conjunto de estados
        if(!estados.contains(origem)){
            return false;
        }

        //verifica se o destino está no conjunto de estados
        if(!estados.contains(destino)){
            return false;
        }

        //verifica se o simbolo existe no alfabeto
        if(!alfabeto.contains(simbolo)){
            return false;
        }

        //Transicao[(origem, simbolo)] = destino.
        transicoes.add(new Transicao(origem, destino, simbolo));

        return true;
    }

    public boolean verificaCadeia(List<String> cadeia){
        List<Boolean> resultados = new ArrayList<>();
        for (int i = 0; i < this.estadoInicial.size(); i++) {
            resultados.add(this.verificaCadeia(cadeia, 0, this.estadoInicial.get(i)));
        }

        for (int i = 0; i < resultados.size(); i++) {
            if(resultados.get(i)){
                return true;
            }
        }

        return false;
    }

    private boolean verificaCadeia(List<String> cadeia, int posicao, String estado_atual){
        //se não deu erro e parou no estado final, cadeia aceita!
        if(posicao >= cadeia.size()){ return estadoFinal.contains(estado_atual); }

        if(!alfabeto.contains(cadeia.get(posicao))){ return false; }

        //AFN ou AFD
        List<Transicao> transicoesFiltradas = new ArrayList<>();
        for (int i = 0; i < transicoes.size(); i++) {
            if(transicoes.get(i).origem.equals(estado_atual) && cadeia.get(posicao).equals(transicoes.get(i).simbolo)){
                transicoesFiltradas.add(transicoes.get(i));
            }
        }

        if(transicoesFiltradas.size() == 0){ return false; }

        List<Boolean> resultados = new ArrayList<>();
        for (int i = 0; i < transicoesFiltradas.size(); i++) {
            resultados.add(verificaCadeia(cadeia,posicao + 1, transicoesFiltradas.get(i).destino));
        }

        for (int i = 0; i < resultados.size(); i++) {
            if(resultados.get(i) == true){
                return true;
            }
        }


        return false;
    }

    public boolean verificaAutomatoCompleto(){
        List<Integer> conts = new ArrayList<>();
        int contador = 0;

        for (int i = 0; i < this.estados.size(); i++) {
            for (int j = 0; j < this.alfabeto.size(); j++) {
                for (int k = 0; k < this.transicoes.size(); k++) {
                    if(this.transicoes.get(k).origem.equals(this.estados.get(i)) &&
                            this.transicoes.get(k).simbolo.equals(this.alfabeto.get(j))){
                        contador++;
                    }
                }
                conts.add(contador);
                contador = 0;
            }
        }

        for (int i = 0; i < conts.size(); i++) {
            if(conts.get(i) == 0){
                return false;
            }
        }

        return true;
    }

    public boolean verificaEstadosInacessiveis(){
        List<String> estadosAlcancados = new ArrayList<>();
        for (int i = 0; i < this.estadoInicial.size(); i++) {
            verificaEstadosInacessiveis(estadosAlcancados, estadoInicial.get(i));
        }

        for (int i = 0; i < this.estados.size(); i++) {
            if(!estadosAlcancados.contains(estados.get(i))){
                return true;
            }
        }

        return false;
    }

    private void verificaEstadosInacessiveis(List<String> estadosAlcancados, String estadoAtual) {
        estadosAlcancados.add(estadoAtual);
        Boolean estadoMorto = true;

        for (int i = 0; i < this.transicoes.size(); i++) {
            if (this.transicoes.get(i).origem.equals(estadoAtual) && !this.transicoes.get(i).destino.equals(estadoAtual)) {
                estadoMorto = false;
                break;
            }
        }

        if (estadoMorto == true) { return; }

        for (int i = 0; i < this.transicoes.size(); i++) {
            if(transicoes.get(i).origem.equals(estadoAtual) && !transicoes.get(i).destino.equals(estadoAtual)
                    && !estadosAlcancados.contains(transicoes.get(i).destino)){
                verificaEstadosInacessiveis(estadosAlcancados, transicoes.get(i).destino);
            }
        }
    }

    public boolean verificaAFN(){

        if(estadoInicial.size() != 1){
            return true;
        }

        for (int i = 0; i < estados.size(); i++) {
            for (int j = 0; j < alfabeto.size(); j++) {
                int cont = 0;
                for (int k = 0; k < transicoes.size(); k++) {
                    if(transicoes.get(k).origem.equals(estados.get(i)) &&
                            transicoes.get(k).simbolo.equals(alfabeto.get(j))){
                        cont++;
                    }
                }
                if(cont > 1){
                    return true;
                }
            }
        }

        return false;
    }

    public void mostraAutomato(){

        System.out.println("Alfabeto: " + this.alfabeto);
        System.out.println("Estados: " + this.estados);
        System.out.println("Estado Inicial: " + this.estadoInicial);
        System.out.println("Estados Finais: " + this.estadoFinal);

        System.out.println("\nTransições: ");
        for (int i = 0; i < transicoes.size(); i++) {
            Transicao trans = transicoes.get(i);
            System.out.println("\t(origem:  " + trans.origem + ", simbolo: " + trans.simbolo + " )= destino: " + trans.destino);
        }
    }

}
