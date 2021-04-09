import java.util.*;
import java.util.stream.Collectors;

public class Automato_Servico {
    public static String valorVazio = "$";

    //funcao recursiva para multiplicar dois automatos
    public static Automato multiplica(Automato automato1, Automato automato2, int operador){
        Automato resultante = new Automato();
        List<List<String>> identidades = new ArrayList<>();

       //adiciona todos os caracteres do alfabeto do automato 1 para o resultante
        resultante.alfabeto.addAll(automato1.alfabeto);
        //se o vetor resultante não contém algum caracter do alfabeto do automato 2, ele é adicionado;
        for (int i = 0; i < automato2.alfabeto.size(); i++) {
            if(!resultante.alfabeto.contains(automato2.alfabeto.get(i))){
                resultante.alfabeto.add(automato2.alfabeto.get(i));
            }
        }

        //multiplica estado por estado
        multiplicaEstados(identidades, automato1, automato2, resultante,operador);

        //transicao por tansicao
        multiplicaTransicoes(identidades,automato1,automato2,resultante);

        return resultante;
    }

    //multiplica estados de dois automatos
    public static void multiplicaEstados(List<List<String>> identidades,Automato automato1, Automato automato2,
                                         Automato resultante, int operador){

        for (int i = 0; i < automato1.estados.size(); i++) {
            for (int j = 0; j < automato2.estados.size(); j++) {
                //nome para o estado multiplicado
                String estado = criaNomeNaoUtilizado(resultante, automato1.estados.get(i)+"."+ automato2.estados.get(j));
                identidades.add(Arrays.asList(estado, automato1.estados.get(i), automato2.estados.get(j))); //0 -> resultante; 1 -> original automato1; 2 -> original automato2;

                //adiciona os novos estados no vetor resultante
                resultante.estados.add(estado);

                //se o estado que está sendo analizado do aut 1 e do aut 2 são estados iniciais
                //add  o estado que contem os dois estados iniciais
                if(automato1.estadoInicial.contains(automato1.estados.get(i)) && automato2.estadoInicial.contains(automato2.estados.get(j))){
                    resultante.estadoInicial.add(estado);
                }

                //adiciona todos os estados novos, que contem os finais do aut 1 ou do aut 2
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

    //cria nomes para estado morto
    public static String criaNomeNaoUtilizado(Automato automato, String padraoInicial){
        String aux = padraoInicial;

        //nome para estado morto e estado novo;
        while (automato.estados.contains(aux)){
            aux = aux.concat("-");
        }
        return aux;
    }

    //cria nome para novos estados
    public static String criaNomeNaoUtilizado(List<String> estados, String padraoInicial){
        String aux = padraoInicial;

        while (estados.contains(aux)){
            aux = aux.concat("-");
        }
        return aux;
    }

    //multiplica transicoes de 2 automatos
    public static void multiplicaTransicoes(List<List<String>> identidades,Automato automato1, Automato automato2,
                                            Automato resultante){

        for (int i = 0; i < resultante.estados.size(); i++) {
            for (int j = 0; j < resultante.alfabeto.size(); j++) {
                Transicao trans1 = null;
                Transicao trans2 = null;

                //identidade -> aramzena quem gerou cada transicao
                //transicoes do automato 1
                for (int k = 0; k < automato1.transicoes.size(); k++) {
                    if(identidades.get(i).get(1).equals(automato1.transicoes.get(k).origem) &&
                            automato1.transicoes.get(k).simbolo.equals(resultante.alfabeto.get(j))
                    ){
                        trans1 = automato1.transicoes.get(k);
                        break;
                    }
                }

                //transicoes automato 2
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

    //cria copia do automato selecionado
    public static Automato cria_copia_automato(Automato automato){
        Automato copia_automato = new Automato();

        for (int i = 0; i < automato.alfabeto.size(); i++) {
            copia_automato.alfabeto.add(automato.alfabeto.get(i));
        }
        for (int i = 0; i < automato.estados.size(); i++) {
            copia_automato.estados.add(automato.estados.get(i));
        }
        for (int i = 0; i < automato.estadoInicial.size(); i++) {
            copia_automato.estadoInicial.add(automato.estadoInicial.get(i));
        }
        for (int i = 0; i < automato.estadoFinal.size(); i++) {
            copia_automato.estadoFinal.add(automato.estadoFinal.get(i));
        }

        for (int i = 0; i < automato.transicoes.size(); i++) {
            Transicao trans = new Transicao(automato.transicoes.get(i).origem,automato.transicoes.get(i).destino, automato.transicoes.get(i).simbolo );
            copia_automato.transicoes.add(trans);
        }

        try {
            Arquivo_Servico.escreve_arquivo("C:\\Users\\polo\\Desktop\\p_4\\LFA\\trabalho_pratico\\automatos_salvos_em_texto\\copia_automato.txt", copia_automato);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  copia_automato;
    }

    //monta tabelas com origem, fecho vazio, alfabeto e fecho vazio
    public static Automato retiraTransicoesVazias(Automato automato){
        //tabela = origem = estados, fechoVazio = transicoes vazias, alfabeto = estados chegada alfabeto
        // e fechoVazio2 = transicoes vazias, baseadas nos estados do alfabeto ( englobam o estado
        // que se está analizando)

        Automato afn = new Automato();
        List<Transicao> novasTransicoes= new ArrayList<>();

        for (int i = 0; i < automato.alfabeto.size(); i++) {
            if (!automato.alfabeto.get(i).equals(valorVazio)) {
                for (int j = 0; j < automato.estados.size(); j++) {
                    List<String> fechoVazio = new ArrayList<>();
                    fechoVazio.add(automato.estados.get(j));
                    for (int k = 0; k < automato.transicoes.size(); k++) {
                        if (automato.transicoes.get(k).origem.equals(automato.estados.get(j)) &&
                                automato.transicoes.get(k).simbolo.equals(valorVazio)) {
                            fechoVazio.add(automato.transicoes.get(k).destino);
                        }
                    }

                    fechoVazio = removeRepeticoes(fechoVazio);

                    List<String> gastandoLetra = new ArrayList<>();
                    for (int k = 0; k < automato.transicoes.size(); k++) {
                        if (automato.transicoes.get(k).simbolo.equals(automato.alfabeto.get(i)) &&
                                fechoVazio.contains(automato.transicoes.get(k).origem)) {
                            gastandoLetra.add(automato.transicoes.get(k).destino);
                        }
                    }
                    gastandoLetra = removeRepeticoes(gastandoLetra);

                    List<String> fechoVazio2 = new ArrayList<>();
                    fechoVazio2.addAll(gastandoLetra);
                    for (int k = 0; k < automato.transicoes.size(); k++) {
                        if (automato.transicoes.get(k).simbolo.equals(valorVazio) &&
                                gastandoLetra.contains(automato.transicoes.get(k).origem)) {
                            fechoVazio2.add(automato.transicoes.get(k).destino);
                        }
                    }

                    fechoVazio2 = removeRepeticoes(fechoVazio2);

                    for (int k = 0; k < fechoVazio2.size(); k++) {
                        novasTransicoes.add(new Transicao(automato.estados.get(j), fechoVazio2.get(k), automato.alfabeto.get(i)));
                    }
                }
            }
        }


        for (String letra: automato.alfabeto) { if (!letra.equals(valorVazio)) { afn.alfabeto.add(letra); } }

        for (String estado : automato.estados) { afn.estados.add(estado); }

        for (String estadoInicial : automato.estadoInicial) { afn.estadoInicial.add(estadoInicial); }

        for (String estadoFinal : automato.estadoFinal) { afn.estadoFinal.add(estadoFinal); }

        for (Transicao transicao : automato.transicoes) {
            if(!transicao.simbolo.equals(valorVazio)){
                afn.transicoes.add(transicao);
            }
        }

        for (Transicao novaTransicao : novasTransicoes) {
            if(!possuiTransicaoRepetida(afn.transicoes, novaTransicao)){
                afn.transicoes.add(novaTransicao);
            }
        }

        return afn;
    }

    public static Automato removeNaoDeterminismo(Automato automato){

        Automato afd = new Automato();
        afd = copiaAutomato(automato);

        //adiciona estado morto;
        afd = completaAutomato(afd);

        //torna um afd
        if(afd.estadoInicial.size() > 1){
            String novoEstado = criaNomeNaoUtilizado(afd, "estadoNovo");
            afd.estados.add(novoEstado);

            for (int i = 0; i < afd.estadoInicial.size(); i++) {
                Transicao trans = new Transicao();
                trans.origem = novoEstado;
                trans.destino = afd.estadoInicial.get(i);
                //$ = vazio
                trans.simbolo = valorVazio;

                afd.transicoes.add(trans);
            }

            afd.estadoInicial.clear();
            afd.estadoInicial.add(novoEstado);
        }

        if(afd.verificaTrasicaoVazia()){
            afd = retiraTransicoesVazias(afd);
        }

        List<List<String>> identidades = new ArrayList<>();
        List<String> estadosNovos = new ArrayList<>();
        estadosNovos.addAll(afd.estadoInicial);
        identidades.add(Collections.singletonList(estadosNovos.get(0)));
        List<Transicao> novasTransicoes = new ArrayList<>();

        for (int i = 0; i < estadosNovos.size(); i++) {
            for (String letra : afd.alfabeto) {

                List<Transicao> transicoes = new ArrayList<>();
                for (int j = 0; j < afd.transicoes.size(); j++) {
                    if(identidades.get(i).contains(afd.transicoes.get(j).origem) &&
                        letra.equals(afd.transicoes.get(j).simbolo)){
                        transicoes.add(afd.transicoes.get(j));
                    }
                }
                if(verificaOrigemEstado(transicoes.stream().map(transicao -> transicao.destino)
                        .collect(Collectors.toList()), identidades)){
                    int index = verificaPosicaoEstado(transicoes.stream().map(transicao -> transicao.destino)
                            .collect(Collectors.toList()), identidades);

                    Transicao trans = new Transicao();
                    trans.origem = estadosNovos.get(i);
                    trans.destino = estadosNovos.get(index);
                    trans.simbolo = letra;

                    novasTransicoes.add(trans);

                }else{
                    String estado = "";
                    List<String> estados = transicoes.stream().map(transicao -> transicao.destino).collect(Collectors.toList());
                    estados = removeRepeticoes(estados);
                    estado = estados.stream().map(s -> s).collect(Collectors.joining("_"));

                    estado = criaNomeNaoUtilizado(estadosNovos, estado);
                    estadosNovos.add(estado);
                    identidades.add(transicoes.stream().map(transicao -> transicao.destino).collect(Collectors.toList()));

                    Transicao trans = new Transicao();
                    trans.origem = estadosNovos.get(i);
                    trans.destino = estado;
                    trans.simbolo = letra;

                    novasTransicoes.add(trans);
                }
            }
        }

        afd.transicoes.clear();
        afd.transicoes.addAll(novasTransicoes);

        afd.estados.clear();
        afd.estados.addAll(estadosNovos);

        List<String> estadosFinais = new ArrayList<>();
        for (int i = 0; i < identidades.size(); i++) {
            if(possuiEstadoFinal(afd.estadoFinal, identidades.get(i))){
                estadosFinais.add(estadosNovos.get(i));
            }
        }

        afd.estadoFinal.clear();
        afd.estadoFinal.addAll(estadosFinais);

        return afd;
    }

    //retira estados repetidos, com o mesmo nome;
    public static List<String> removeRepeticoes(List<String> estadosRepetidos){

        List<String> semRepeticao = new ArrayList<>();
        for (int i = 0; i < estadosRepetidos.size(); i++) {
            if(!semRepeticao.contains(estadosRepetidos.get(i))){
                semRepeticao.add(estadosRepetidos.get(i));
            }
        }
        return semRepeticao;
    }

    //verifica se existe trans repetida no automato;
    public static boolean possuiTransicaoRepetida(List<Transicao> trans, Transicao transicao){
        for (Transicao transicaoAtual : trans) {
            if(transicaoAtual.origem.equals(transicao.origem) && transicaoAtual.destino.equals(transicao.destino) &&
                    transicaoAtual.simbolo.equals(transicao.simbolo)){
                return true;
            }
        }
        return false;
    }

    //cria uma copia do automato sleecionado pelo usuario
    public static Automato copiaAutomato(Automato automato){

        //verifica qual foi o aut selecionado, cria copia e retorna;
        Automato automato1 = new Automato();

        for (String letras : automato.alfabeto) { automato1.alfabeto.add(letras); }
        for (String estado : automato.estados) { automato1.estados.add(estado); }
        for (String estadoInicial : automato.estadoInicial) { automato1.estadoInicial.add(estadoInicial); }
        for (String estadoFinal : automato.estadoFinal) { automato1.estadoFinal.add(estadoFinal); }

        for (int i = 0; i < automato.transicoes.size(); i++) {
            automato1.transicoes.add(automato.transicoes.get(i));
        }

        return automato1;
    }

    public static boolean verificaOrigemEstado(List<String> originais, List<List<String>> estadosSalvos){

        for (int i = 0; i < estadosSalvos.size(); i++) {
            if(estadosSalvos.get(i).containsAll(originais) && originais.containsAll(estadosSalvos.get(i))){
                return true;
            }
        }

        return false;
    }

    public static int verificaPosicaoEstado(List<String> originais, List<List<String>> estadosSalvos){

        for (int i = 0; i < estadosSalvos.size(); i++) {
            if(estadosSalvos.get(i).containsAll(originais) && originais.containsAll(estadosSalvos.get(i))){
                return i;
            }
        }

        return -1;
    }

    //verifica se o estado final é igual a identidade criada, após remover o n determinismo;
    public static boolean possuiEstadoFinal(List<String> finais, List<String> identidades){

        for (int i = 0; i < finais.size(); i++) {
            for (int j = 0; j < identidades.size(); j++) {
                if(finais.get(i).equals(identidades.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    //adiciona estado para completar
    public static Automato completaAutomato(Automato automato){
        Automato autCompleto = copiaAutomato(automato);
        String estadoMorto = criaNomeNaoUtilizado(autCompleto, "estadoMorto");
        List<Transicao> trans = new ArrayList<>();

        for (int i = 0; i < autCompleto.alfabeto.size(); i++) {
            Transicao transicao = new Transicao();
            transicao.origem = estadoMorto;
            transicao.destino = estadoMorto;
            transicao.simbolo = autCompleto.alfabeto.get(i);
            trans.add(transicao);
        }

        for (int i = 0; i < autCompleto.alfabeto.size(); i++) {
            for (int j = 0; j < autCompleto.estados.size(); j++) {
                boolean verifica = false;
                for (int k = 0; k < autCompleto.transicoes.size(); k++) {
                    if(autCompleto.transicoes.get(k).origem.equals(autCompleto.estados.get(j)) &&
                        autCompleto.transicoes.get(k).simbolo.equals(autCompleto.alfabeto.get(i))){
                        verifica = true;
                        break;
                    }
                }
                if(verifica == false){
                    Transicao nova = new Transicao();
                    nova.origem = autCompleto.estados.get(j);
                    nova.destino = estadoMorto;
                    nova.simbolo = autCompleto.alfabeto.get(i);
                    trans.add(nova);
                }
            }
        }

        if(trans.size() > autCompleto.alfabeto.size()){
            autCompleto.estados.add(estadoMorto);
            autCompleto.transicoes.addAll(trans);
        }

        return autCompleto;
    }

}
