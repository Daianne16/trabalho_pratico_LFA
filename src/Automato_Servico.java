import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Automato_Servico {
    public static Automato multiplica(Automato automato1, Automato automato2, int operador){
        Automato resultante = new Automato();
        List<List<String>> identidades = new ArrayList<>();

        resultante.alfabeto.addAll(automato1.alfabeto);
        for (int i = 0; i < automato2.alfabeto.size(); i++) {
            if(!resultante.alfabeto.contains(automato2.alfabeto.get(i))){
                resultante.alfabeto.add(automato2.alfabeto.get(i));
            }
        }

        multiplicaEstados(identidades, automato1, automato2, resultante,operador);

        multiplicaTransicoes(identidades,automato1,automato2,resultante);

        return resultante;
    }

    public static void multiplicaEstados(List<List<String>> identidades,Automato automato1, Automato automato2,
                                         Automato resultante, int operador){

        for (int i = 0; i < automato1.estados.size(); i++) {
            for (int j = 0; j < automato2.estados.size(); j++) {
                String estado = criaNomeNaoUtilizado(resultante, automato1.estados.get(i)+"."+ automato2.estados.get(j));
                identidades.add(Arrays.asList(estado, automato1.estados.get(i), automato2.estados.get(j))); //0 -> resultante; 1 -> original automato1; 2 -> original automato2;

                resultante.estados.add(estado);

                if(automato1.estadoInicial.contains(automato1.estados.get(i)) && automato2.estadoInicial.contains(automato2.estados.get(j))){
                    resultante.estadoInicial.add(estado);
                }

                if(operador == 1){
                    if(automato1.estadoFinal.contains(automato1.estados.get(i)) || automato2.estadoFinal.contains(automato2.estados.get(j))){
                        resultante.estadoFinal.add(estado);
                    }
                }else if(operador == 2){
                    if(automato1.estadoFinal.contains(automato1.estados.get(i)) && automato2.estadoFinal.contains(automato2.estados.get(j))){
                        resultante.estadoFinal.add(estado);
                    }
                }else if(operador == 3){
                    if(automato1.estadoFinal.contains(automato1.estados.get(i)) && !automato2.estadoFinal.contains(automato2.estados.get(j))){
                        resultante.estadoFinal.add(estado);
                    }
                }else if(operador == 4){
                    if(!automato1.estadoFinal.contains(automato1.estados.get(i))){
                        resultante.estadoFinal.add(estado);
                    }
                }
            }
        }

    }

    public static String criaNomeNaoUtilizado(Automato automato, String padraoInicial){
        String aux = padraoInicial;

        while (automato.estados.contains(aux)){
            aux = aux.concat("-");
        }
        return aux;
    }

    public static void multiplicaTransicoes(List<List<String>> identidades,Automato automato1, Automato automato2,
                                            Automato resultante){

        for (int i = 0; i < resultante.estados.size(); i++) {
            for (int j = 0; j < resultante.alfabeto.size(); j++) {
                Transicao trans1 = null;
                Transicao trans2 = null;

                for (int k = 0; k < automato1.transicoes.size(); k++) {
                    if(identidades.get(i).get(1).equals(automato1.transicoes.get(k).origem) &&
                            automato1.transicoes.get(k).simbolo.equals(resultante.alfabeto.get(j))
                    ){
                        trans1 = automato1.transicoes.get(k);
                        break;
                    }
                }
                for (int k = 0; k < automato2.transicoes.size(); k++) {
                    if(identidades.get(i).get(2).equals(automato2.transicoes.get(k).origem) &&
                            automato2.transicoes.get(k).simbolo.equals(resultante.alfabeto.get(j))
                    ){
                        trans2 = automato2.transicoes.get(k);
                        break;
                    }
                }

                if(Objects.nonNull(trans1) && Objects.nonNull(trans2)){
                    String destino = null;
                    for (int k = 0; k < resultante.estados.size(); k++) {
                        if(identidades.get(k).get(1).equals(trans1.destino) && identidades.get(k).get(2).equals(trans2.destino)){
                            destino = resultante.estados.get(k);
                            break;
                        }
                    }
                    if(Objects.nonNull(destino)){
                        Transicao transicao = new Transicao();
                        transicao.origem = resultante.estados.get(i);
                        transicao.simbolo = resultante.alfabeto.get(j);
                        transicao.destino = destino;

                        resultante.transicoes.add(transicao);
                    }
                }
            }
        }

    }

}
