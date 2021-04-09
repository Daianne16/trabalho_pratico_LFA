import java.util.*;

public class Main {
    private static List<Automato> automatos = new ArrayList<>();
    public static void main(String[] args){
        //Automato-AFD teste 1
        Automato automato = new Automato();
        automatos.add(preenchendoAutomato(automato));

        //Automato-AFD teste 2
        Automato automato2 = new Automato();
        automatos.add(preenchendoAutomato2(automato2));

        //Automato-AFN teste 3
        Automato automato3 = new Automato();
        automatos.add(preenchendoAutomato3(automato3));

        //Automato-AFN-Vazio teste 4
        Automato automato4 = new Automato();
        automatos.add(preenchendoAutomato4(automato4));

        //Automato-AFN teste 5
        Automato automato5 = new Automato();
        automatos.add(preenchendoAutomato5(automato5));

        Automato automato6 = new Automato();
        automatos.add(teste(automato6));

        Scanner ler = new Scanner(System.in);
        int esc = 0;

        while(esc != 10){
            menuPrincipal();
            esc = ler.nextInt();
            if(esc == 1){
                //escreve todas
                escreve_automato_texto();
            }else if(esc == 2){
                //le todos
                le_automato_texto();
            }else if(esc == 3){
                //cria copia do selecionado
                Automato_Servico.cria_copia_automato(seleciona_automato());
                System.out.println("\n\tAutomato(s) copiado(s)!");
            }else if(esc == 4){
                //equivalencia entre estados
                verificaEquivEstados(seleciona_automato());
            }else if(esc == 5){
                //equivalencia entre automatos
                if(verificaEquivAutomatos(seleciona_automato(),seleciona_automato()) == true){
                    System.out.println("Os automatos são equivalentes!!");
                }else{
                    System.out.println("Os automatos não são equivalentes!!");
                }
            }else if(esc == 6){
                //minimizacao
                Automato aux = minimiza_automato(seleciona_automato());
                if(Objects.nonNull(aux)){
                    aux.mostraAutomato();
                }else{
                    System.out.println("Erro!! Não foi possível realizar a minimização o automato!!");
                }
            }else if(esc == 7){
                //multiplica automato
                multiplicaAutomatos();
            }else if(esc == 8) {
                //elimina transicoes vazias
                Automato_Servico.retiraTransicoesVazias(seleciona_automato()).mostraAutomato();
            }else if(esc == 9) {
                System.out.println("Nessa opção poderá selecionar o AFN-Vazio e o algoritmo fará todo o processo de conversão");
                Automato_Servico.removeNaoDeterminismo(seleciona_automato()).mostraAutomato();
            }else if(esc == 10){
                break;
            }
        }
    }

    //AFD
    public static Automato preenchendoAutomato(Automato teste){

        Transicao t1 = new Transicao("0", "1", "b");
        Transicao t2 = new Transicao("0", "2", "a");
        Transicao t3 = new Transicao("1", "1", "a");
        Transicao t4 = new Transicao("1", "0", "b");
        Transicao t5 = new Transicao("2", "4", "a");
        Transicao t6 = new Transicao("2", "5", "b");
        Transicao t7 = new Transicao("3", "5", "a");
        Transicao t8 = new Transicao("3", "4", "b");
        Transicao t9 = new Transicao("4", "3", "a");
        Transicao t10 = new Transicao("4", "2", "b");
        Transicao t11 = new Transicao("5", "3", "b");
        Transicao t12 = new Transicao("5", "2", "a");
        teste.transicoes = Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);

        teste.alfabeto = Arrays.asList("a", "b");
        teste.estados = Arrays.asList("0", "1", "2", "3", "4", "5");
        teste.estadoInicial = Collections.singletonList("0");
        teste.estadoFinal = Arrays.asList("0", "4", "5");

        return teste;
    }
    //AFD
    public static Automato preenchendoAutomato2(Automato teste){

        Transicao t1 = new Transicao("1", "2", "1");
        Transicao t2 = new Transicao("1", "2", "3");
        Transicao t3 = new Transicao("1", "3", "2");
        Transicao t4 = new Transicao("2", "2", "1");
        Transicao t5 = new Transicao("2", "2", "2");
        Transicao t6 = new Transicao("2", "2", "3");
        Transicao t7 = new Transicao("3", "1", "2");
        Transicao t8 = new Transicao("3", "3", "1");
        Transicao t9 = new Transicao("3", "3", "3");
        teste.transicoes = Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9);

        teste.alfabeto = Arrays.asList("1", "2", "3");
        teste.estados = Arrays.asList("1", "2", "3");
        teste.estadoInicial = Collections.singletonList("1");
        teste.estadoFinal = Arrays.asList("3");

        return teste;
    }
    //AFN
    public static Automato preenchendoAutomato3(Automato teste){

        Transicao t0 = new Transicao("0", "0", "b");
        Transicao t1 = new Transicao("0", "1", "a");
        Transicao t2 = new Transicao("0", "3", "a");
        Transicao t3 = new Transicao("1", "2", "b");
        Transicao t4 = new Transicao("1", "3", "a");
        Transicao t5 = new Transicao("2", "3", "b");
        Transicao t6 = new Transicao("3", "3", "a");
        teste.transicoes = Arrays.asList(t0,t1, t2, t3, t4, t5, t6);

        teste.alfabeto = Arrays.asList("a", "b");
        teste.estados = Arrays.asList("0", "1", "2", "3");
        teste.estadoInicial = Collections.singletonList("0");
        teste.estadoFinal = Arrays.asList("1", "3");

        return teste;
    }
    //AFN-Vazio
    public static Automato preenchendoAutomato4(Automato teste){
        //$ = movimento vazio;
        Transicao t0 = new Transicao("0","1", "$");
        Transicao t1 = new Transicao("0", "2", "a");
        Transicao t2 = new Transicao("1", "1", "a");
        Transicao t3 = new Transicao("1","3",  "b");
        Transicao t4 = new Transicao("2", "1", "c");
        Transicao t5 = new Transicao("2", "2", "b");
        Transicao t6 = new Transicao("2", "3", "$");
        Transicao t7 = new Transicao("3", "3", "c");
        teste.transicoes = Arrays.asList(t0, t1, t2, t3, t4, t5, t6, t7);

        teste.alfabeto = Arrays.asList("a", "b", "c", "$");
        teste.estados = Arrays.asList("0", "1", "2", "3");
        teste.estadoInicial = Collections.singletonList("0");
        teste.estadoFinal = Arrays.asList("3");

        return teste;
    }
    //AFN
    public static Automato preenchendoAutomato5(Automato teste){
        Transicao t0 = new Transicao("1","3", "a");
        Transicao t1 = new Transicao("2", "3", "b");
        Transicao t2 = new Transicao("3", "2", "a");
        teste.transicoes = Arrays.asList(t0, t1, t2);

        teste.alfabeto = Arrays.asList("a", "b");
        teste.estados = Arrays.asList("1", "2", "3");
        teste.estadoInicial =Arrays.asList("1", "2", "3");
        teste.estadoFinal = Arrays.asList("3");

        return teste;
    }

    public static Automato teste(Automato teste){
        Transicao t0 = new Transicao("0","0", "a");
        Transicao t1 = new Transicao("0","0", "b");
        Transicao t2 = new Transicao("0","1", "a");
        Transicao t3 = new Transicao("1", "2", "a");
        Transicao t4 = new Transicao("1", "2", "b");
        Transicao t5 = new Transicao("2", "3", "a");
        Transicao t6 = new Transicao("2", "3", "b");
        Transicao t7 = new Transicao("3", "4", "a");
        Transicao t8 = new Transicao("3", "4", "b");
        Transicao t9= new Transicao("4", "5", "a");
        Transicao t10 = new Transicao("4", "5", "b");
        teste.transicoes = Arrays.asList(t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);

        teste.alfabeto = Arrays.asList("a", "b");
        teste.estados = Arrays.asList("0", "1", "2", "3", "4", "5");
        teste.estadoInicial =Arrays.asList("1");
        teste.estadoFinal = Arrays.asList("5");

        return teste;
    }
    public static void menuPrincipal(){
        System.out.println("\n\t-------------MENU------------");
        System.out.println("\t1. Salvar automato em um arquivo de texto;");
        System.out.println("\t2. Carregar automato de um arquivo de texto;");
        System.out.println("\t3. Criar uma cópia do automato;");
        System.out.println("\t4. Calcular estados equivalentes;");
        System.out.println("\t5. Testar equivalencia entre 2 AFDs;");
        System.out.println("\t6. Calcular automato minimizado para um AFD fornecido;");
        System.out.println("\t7. Multiplicação entre AFDs e Operações de conjunto com AFDs;");
        System.out.println("\t8. Eliminar movimentos vazios no AFN;");
        System.out.println("\t9. Eliminar o não-determinismo no AFN;");
        System.out.println("\t10. Sair.");
        System.out.println("\n\tSua escolha: ");

    }

    //escreve todos os preenchidos
    public static void escreve_automato_texto(){
        for (int i = 0; i < automatos.size(); i++) {
            try {
                Arquivo_Servico.escreve_arquivo("C:\\Users\\polo\\Desktop\\p_4\\LFA\\trabalho_pratico\\automatos_salvos_em_texto\\automato"+(i+1)+".txt", automatos.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(" AFD salvo em um arquivo de texto!");
    }

    //lê todos os preenchidos e escritos no arquivo de texto;
    public static void le_automato_texto(){
        System.out.println("\n\t-----------Automatos carregados do arquivo de texto: --------------\n");
        for (int i = 0; i < automatos.size(); i++) {
            System.out.println("AUTOMATO ("+(i+1)+")");
            try {
                Arquivo_Servico.le_arquivo("C:\\Users\\polo\\Desktop\\p_4\\LFA\\trabalho_pratico\\automatos_salvos_em_texto\\automato"+(i+1)+".txt").mostraAutomato();
                System.out.println("\n\n-----------------------------------");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static Automato seleciona_automato(){
        Automato automato = new Automato();
        int esc = -1;
        Scanner ler = new Scanner(System.in);

        System.out.println("\n\tLembre-se: \nautomato (1) = AFD; \nautomato (2) = AFD; \nautomato (3): AFN; \nautomato (4) = AFN-Vazio\nautomato (5) = AFN\n");
        System.out.println("---------Opções: \n");
        for (int i = 0; i < automatos.size(); i++) {
            System.out.println((i+1) + " " + automatos.get(i).estados);
        }

        while(esc < 0 || esc > automatos.size()){
            System.out.println("Digite sua escolha: ");
            esc = ler.nextInt();

        }

        return automatos.get(esc-1);
    }

    //verifica se é completo, verifica a equivalencia dos estados e minimiza;
    public static Automato minimiza_automato(Automato automato){
        if(automato.estados.size() >= 2 && !automato.verificaEstadosInacessiveis() && automato.verificaAutomatoCompleto() && !automato.verificaAFN()){
            List<ParVerificacao> listaPares = criaTabelaEquivalencia(automato);

            listaPares = verificaEquivalencia(listaPares, automato);

            Automato minimizado = montaAutomatoMinimizado(automato, listaPares);

            return minimizado;
        }

        return null;
    }


    public static List<ParVerificacao> criaTabelaEquivalencia(Automato automato){
        List<ParVerificacao> vetor = new ArrayList<>();
        for (int i = 1; i < automato.estados.size(); i++) {
            for (int j = 0; j < i; j++) {
                ParVerificacao par = new ParVerificacao();

                par.estado1 = automato.estados.get(i);
                par.estado2 = automato.estados.get(j);

                if(automato.estadoFinal.contains(automato.estados.get(i)) !=
                        automato.estadoFinal.contains(automato.estados.get(j))){
                   par.statusEquivalencia = 1;
                }else{
                    par.statusEquivalencia = 0;
                }

                vetor.add(par);

            }
        }
        return vetor;
    }

    public static List<ParVerificacao> verificaEquivalencia(List<ParVerificacao> verificacoes, Automato automato){
        while(true){
            boolean aux = false;
            for (int i = 0; i < verificacoes.size(); i++) {
                //statusEquivalencia = 0 -> n julgado
                if(verificacoes.get(i).statusEquivalencia == 0){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                break;
            }

            for (ParVerificacao parVerificacao : verificacoes) {
                if(parVerificacao.statusEquivalencia != 0){
                    for (int j = 0; j < verificacoes.size(); j++) {
                        if(verificacoes.get(j).dependencias.contains(parVerificacao)){
                            if(parVerificacao.statusEquivalencia == 1){
                                verificacoes.get(j).statusEquivalencia = 1;
                                verificacoes.get(j).dependencias.clear();
                            }else if(parVerificacao.statusEquivalencia == 2){
                                verificacoes.get(j).dependencias.remove(parVerificacao);
                                if(verificacoes.get(j).dependencias.size() == 0){
                                    verificacoes.get(j).statusEquivalencia = 2;
                                }
                            }
                        }
                    }
                }else if(parVerificacao.dependencias.size() == 0){
                    parVerificacao.efetuaVerificacao(verificacoes, automato);
                }
            }
        }

        return verificacoes;
    }

    public static Automato montaAutomatoMinimizado(Automato automato, List<ParVerificacao> pares){
        Automato minimizando = new Automato();

        //adiciona estados equivalentes
        for (int i = 0; i < pares.size(); i++) {
            if(pares.get(i).statusEquivalencia == 2){
                minimizando.estados.add(pares.get(i).montaEstado());
            }
        }

        //adiciona estados não equivalentes
        for (int i = 0; i < automato.estados.size(); i++) {
            boolean achouEquivalente = false;

            for (int j = 0; j < pares.size(); j++) {
                if((pares.get(j).estado1.equals(automato.estados.get(i)) || pares.get(j).estado2.equals(automato.estados.get(i))) &&
                        pares.get(j).statusEquivalencia == 2){
                    achouEquivalente = true;
                }
            }

            if(achouEquivalente == false){
                minimizando.estados.add(automato.estados.get(i));
            }
        }

        //adiciona estados iniciais não equivalentes
        for (int i = 0; i < automato.estadoInicial.size(); i++) {
            boolean achouEIEquivalente = false;

            for (int j = 0; j < pares.size(); j++) {
                if((pares.get(j).estado1.equals(automato.estadoInicial.get(i)) || pares.get(j).estado2.equals(automato.estadoInicial.get(i))) &&
                        pares.get(j).statusEquivalencia == 2){
                    achouEIEquivalente = true;
                }
            }

            if(achouEIEquivalente == false){
                minimizando.estadoInicial.add(automato.estadoInicial.get(i));
            }
        }

        //adiciona estados finais não equivalentes
        for (int i = 0; i < automato.estadoFinal.size(); i++) {
            boolean achouEFEquivalente = false;

            for (int j = 0; j < pares.size(); j++) {
                if((pares.get(j).estado1.equals(automato.estadoFinal.get(i)) || pares.get(j).estado2.equals(automato.estadoFinal.get(i))) &&
                        pares.get(j).statusEquivalencia == 2){
                    achouEFEquivalente = true;
                }
            }

            if(achouEFEquivalente == false){
                minimizando.estadoFinal.add(automato.estadoFinal.get(i));
            }
        }

        //adiciona estados iniciais equivalentes
        for (int j = 0; j < pares.size(); j++) {
            if((automato.estadoInicial.contains(pares.get(j).estado1) || automato.estadoInicial.contains(pares.get(j).estado2)) &&
                    pares.get(j).statusEquivalencia == 2){
                minimizando.estadoInicial.add(pares.get(j).montaEstado());
            }
        }

        //adiciona estados finais equivalentes
        for (int j = 0; j < pares.size(); j++) {
            if((automato.estadoFinal.contains(pares.get(j).estado1) || automato.estadoFinal.contains(pares.get(j).estado2)) &&
                    pares.get(j).statusEquivalencia == 2){
                minimizando.estadoFinal.add(pares.get(j).montaEstado());
            }
        }

        List<String> estadosSemRepeticao = new ArrayList<>();
        for (int i = 0; i < minimizando.estados.size(); i++) {
            if(!estadosSemRepeticao.contains(minimizando.estados.get(i))){
                estadosSemRepeticao.add(minimizando.estados.get(i));
            }
        }
        minimizando.estados.clear();
        minimizando.estados.addAll(estadosSemRepeticao);

        estadosSemRepeticao = new ArrayList<>();
        for (int i = 0; i < minimizando.estadoInicial.size(); i++) {
            if(!estadosSemRepeticao.contains(minimizando.estadoInicial.get(i))){
                estadosSemRepeticao.add(minimizando.estadoInicial.get(i));
            }
        }

        minimizando.estadoInicial.clear();
        minimizando.estadoInicial.addAll(estadosSemRepeticao);

        estadosSemRepeticao = new ArrayList<>();
        for (int i = 0; i < minimizando.estadoFinal.size(); i++) {
            if(!estadosSemRepeticao.contains(minimizando.estadoFinal.get(i))){
                estadosSemRepeticao.add(minimizando.estadoFinal.get(i));
            }
        }

        minimizando.estadoFinal.clear();
        minimizando.estadoFinal.addAll(estadosSemRepeticao);

        //adiciona transições não equivalentes
        for (int i = 0; i < automato.transicoes.size(); i++) {
            boolean achouTransEquivalente = false;

            for (int j = 0; j < pares.size(); j++) {
                if(((pares.get(j).estado1.equals(automato.transicoes.get(i).destino) && pares.get(j).estado2.equals(automato.transicoes.get(i).destino)) ||
                        (pares.get(j).estado1.equals(automato.transicoes.get(i).origem) && pares.get(j).estado2.equals(automato.transicoes.get(i).origem)))
                                && pares.get(j).statusEquivalencia == 2){
                    achouTransEquivalente = true;
                }
            }

            if(achouTransEquivalente == false){
                minimizando.transicoes.add(automato.transicoes.get(i));
            }
        }

        //adiciona transições equivalentes
        for (int k = 0; k < pares.size(); k++) {
            if(pares.get(k).statusEquivalencia == 2){
                minimizando.transicoes.addAll(transformaTransicoes(pares.get(k).estado1, pares.get(k).estado2, pares.get(k).montaEstado(), automato));
            }
        }

        List<Transicao> transSemRepeticao = new ArrayList<>();
        for (int i = 0; i < minimizando.transicoes.size(); i++) {
            boolean achouRepetido = false;
            for (int j = 0; j < transSemRepeticao.size(); j++) {
                if (
                    minimizando.transicoes.get(i).origem.equals(transSemRepeticao.get(j).origem) &&
                    minimizando.transicoes.get(i).simbolo.equals(transSemRepeticao.get(j).simbolo) &&
                    minimizando.transicoes.get(i).destino.equals(transSemRepeticao.get(j).destino)
                ){
                    achouRepetido = true;
                    break;
                }
            }
            if(achouRepetido == false){
                transSemRepeticao.add(minimizando.transicoes.get(i));
            }
        }
        minimizando.transicoes.clear();
        minimizando.transicoes.addAll(transSemRepeticao);

        minimizando.alfabeto.addAll(automato.alfabeto);

        return minimizando;
    }

    private static List<Transicao> transformaTransicoes(String estado1, String estado2,String estadoEquiv, Automato automato) {

        List<Transicao> auxList = new ArrayList<>();
        for (int i = 0; i < automato.transicoes.size(); i++) {
            if(automato.transicoes.get(i).origem.equals(estado1) || automato.transicoes.get(i).origem.equals(estado2)){
                Transicao aux = automato.transicoes.get(i);
                aux.origem = estadoEquiv;
                auxList.add(aux);
            }
            if(automato.transicoes.get(i).destino.equals(estado1) || automato.transicoes.get(i).destino.equals(estado2)){
                Transicao aux = automato.transicoes.get(i);
                aux.destino = estadoEquiv;
                auxList.add(aux);
            }
        }

        return auxList;
    }

    //verifica se estados sao equivalentes
    //se fazem a mesma coisa
    public static void verificaEquivEstados(Automato automato){
        List<ParVerificacao> listaPares = criaTabelaEquivalencia(automato);

        listaPares = verificaEquivalencia(listaPares, automato);

        for (int i = 0; i < listaPares.size(); i++) {
            if(listaPares.get(i).statusEquivalencia == 1) {
                System.out.println("\nOs estados " +listaPares.get(i).estado1+ " e " +listaPares.get(i).estado2+ " não são equivalentes");
            }else if(listaPares.get(i).statusEquivalencia == 2){
                    System.out.println("Os estados " +listaPares.get(i).estado1+ " e " +listaPares.get(i).estado2+ " são equivalentes");
            }
        }
    }

    //equivalencia entre dois automatos
    public static boolean verificaEquivAutomatos(Automato automato1, Automato automato2){
        Automato intermediario = new Automato();

        //passando os estados do automato 1 p/ o intermediario
        for (int i = 0; i < automato1.estados.size(); i++) {
            intermediario.estados.add(automato1.estados.get(i));
        }
        for (int i = 0; i < automato1.estadoInicial.size(); i++) {
            intermediario.estadoInicial.add(automato1.estadoInicial.get(i));
        }
        for (int i = 0; i < automato1.estadoFinal.size(); i++) {
            intermediario.estadoFinal.add(automato1.estadoFinal.get(i));
        }
        for (int i = 0; i < automato1.transicoes.size(); i++) {
            intermediario.transicoes.add(automato1.transicoes.get(i));
        }

        //passando os estados do automato 2 para o intermediario
        List<List<String>> identidades = new ArrayList<>();//[0] -> nova, [1] -> velha
        for (int i = 0; i < automato2.estados.size(); i++) {
            String aux = Automato_Servico.criaNomeNaoUtilizado(intermediario, automato2.estados.get(i));
            identidades.add(Arrays.asList(aux, automato2.estados.get(i)));
            intermediario.estados.add(aux);
        }

        for (int i = 0; i < automato2.estadoInicial.size(); i++) {
            for (int j = 0; j < identidades.size(); j++) {
                if(identidades.get(j).get(1).equals(automato2.estadoInicial.get(i))){
                    intermediario.estadoInicial.add(identidades.get(j).get(0));
                    break;
                }
            }
        }

        for (int i = 0; i < automato2.estadoFinal.size(); i++) {
            for (int j = 0; j < identidades.size(); j++) {
                if(identidades.get(j).get(1).equals(automato2.estadoFinal.get(i))){
                    intermediario.estadoFinal.add(identidades.get(j).get(0));
                    break;
                }
            }
        }

        for (int i = 0; i < automato2.transicoes.size(); i++) {
            Transicao trans = new Transicao(automato2.transicoes.get(i).origem, automato2.transicoes.get(i).destino, automato2.transicoes.get(i).simbolo);
            for (int j = 0; j < identidades.size(); j++) {
                if(identidades.get(j).get(1).equals(trans.origem)){
                    trans.origem = identidades.get(j).get(0);
                }
                if(identidades.get(j).get(1).equals(trans.destino)){
                    trans.destino = identidades.get(j).get(0);
                }
            }
            intermediario.transicoes.add(trans);
        }

        List<ParVerificacao> listaPares = criaTabelaEquivalencia(intermediario);

        listaPares = verificaEquivalencia(listaPares, intermediario);

        for (int i = 0; i < listaPares.size(); i++) {
            if(intermediario.estadoInicial.contains(listaPares.get(i).estado1) && intermediario.estadoInicial.contains(listaPares.get(i).estado2)){
                    return true;
            }
        }

        return false;
    }

    //multiplica dois automatos;
    public static void multiplicaAutomatos(){
        Automato automato1 = seleciona_automato();
        Automato automato2 = seleciona_automato();
        Scanner ler = new Scanner(System.in);

        System.out.println("\n\t------Menu Operações Conjuntos------");
        System.out.println("1. União;"); //resultante terá todos os estados, transicoes e alfabeto dos dois automatos
        System.out.println("2. Intercessão;"); //resultante terá apensas o que tem nos dois
        System.out.println("3. Diferença;"); //resultante terá apenas o que não tem nos dois
        System.out.println("4. Complemento;"); //os q são finais passam a ser n finais e vice versa;
        System.out.println("\n\tSua escolha: ");

        Automato_Servico.multiplica(automato1, automato2, ler.nextInt()).mostraAutomato();
    }

}
