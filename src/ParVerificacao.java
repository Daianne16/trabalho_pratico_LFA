import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParVerificacao {
    int statusEquivalencia = 0;//0 = n julgado; 1 = n equivalente; 2 = equivalente.
    String estado1;
    String estado2;
    List<ParVerificacao> dependencias = new ArrayList<>();

    public void efetuaVerificacao(List<ParVerificacao> pares, Automato automato){
        for (int i = 0; i < automato.alfabeto.size(); i++) {
            Transicao trans1 = null;
            Transicao trans2 = null;
            for (int j = 0; j < automato.transicoes.size(); j++) {
                if(automato.transicoes.get(j).origem.equals(this.estado1) && automato.transicoes.get(j).simbolo.equals(automato.alfabeto.get(i))){
                    trans1 = automato.transicoes.get(j);
                }
                if(automato.transicoes.get(j).origem.equals(this.estado2) && automato.transicoes.get(j).simbolo.equals(automato.alfabeto.get(i))){
                    trans2 = automato.transicoes.get(j);
                }
            }
           if(Objects.nonNull(trans1) && Objects.nonNull(trans2)){
               ParVerificacao par12 = null;
               for (int j = 0; j < pares.size(); j++) {
                   if((pares.get(j).estado1.equals(trans1.destino) && pares.get(j).estado2.equals(trans2.destino))
                        || (pares.get(j).estado2.equals(trans1.destino) && pares.get(j).estado1.equals(trans2.destino))
                   ){
                       par12 = (pares.get(j));
                       break;
                   }
               }
               if(Objects.nonNull(par12)){
                   if(par12.statusEquivalencia == 0){
                       dependencias.add(par12);
                   }else if(par12.statusEquivalencia == 1){
                       dependencias.clear();
                       statusEquivalencia = 1;
                       return;
                   }
               }
           }
        }
        statusEquivalencia = 2;
    }

    public String montaEstado(){
        return estado1+"+"+estado2;
    }
}
