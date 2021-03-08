import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Main {
    private static List<Automato> automatos = new ArrayList<>();
    public static void main(String[] args){
        //Automatos teste 1
        Automato automato = new Automato();
        automatos.add(preenchendoAutomato(automato));

        //Automato teste 2
        Automato automato2 = new Automato();
        automatos.add(preenchendoAutomato2(automato2));

        Scanner ler = new Scanner(System.in);
        int esc = 0;

        while(esc != 7){
            menuPrincipal();
            esc = ler.nextInt();
            if(esc == 1){
                escreve_automato_texto(automato);
                escreve_automato_texto(automato2);
            }else if(esc == 2){
                le_automato_texto(automato);
                automato.mostraAutomato();
                le_automato_texto(automato2);
                automato2.mostraAutomato();
            }else if(esc == 3){
                cria_copia_automato(seleciona_automato());
                System.out.println("\n\tAutomato(s) copiado(s)!");
            }else if(esc == 4){

            }else if(esc == 5){

            }else if(esc == 6){
                Automato aux = minimiza_automato(seleciona_automato());
                if(Objects.nonNull(aux)){
                    aux.mostraAutomato();
                }else{
                    System.out.println("Erro!! Não foi possível realizar a minimização o automato!!");
                }
            }else if(esc == 7){

            }else if(esc == 8){
                break;
            }
        }
    }

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

        teste.mostraAutomato();
        return teste;
    }

    public static Automato preenchendoAutomato2(Automato teste){

        Transicao t1 = new Transicao("0", "1", "b");
        Transicao t2 = new Transicao("0", "2", "a");
        Transicao t3 = new Transicao("1", "1", "a");
        Transicao t4 = new Transicao("1", "0", "b");
        Transicao t5 = new Transicao("2", "4", "a");
        Transicao t12 = new Transicao("2", "5", "b");
        Transicao t6 = new Transicao("3", "4", "b");
        Transicao t7 = new Transicao("3", "5", "a");
        Transicao t8 = new Transicao("4", "2", "b");
        Transicao t9 = new Transicao("4", "5", "a");
        Transicao t10 = new Transicao("5", "3", "b");
        Transicao t11 = new Transicao("5", "2", "a");
        teste.transicoes = Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);

        teste.alfabeto = Arrays.asList("a", "b");
        teste.estados = Arrays.asList("0", "1", "2", "3", "4", "5");
        teste.estadoInicial = Collections.singletonList("0");
        teste.estadoFinal = Arrays.asList("0", "4", "5");


        //System.out.println(teste.verificaCadeia(Arrays.asList("b","a","a","a","a","b"), "0"));
        //System.out.println(teste.verificaCadeia(Arrays.asList("b","a","a","a","a"), "0"));

        return teste;
    }

    public static void menuPrincipal(){
        System.out.println("\n\t-------------MENU------------");
        System.out.println("1. Salvar automato em um arquivo de texto;");
        System.out.println("2. Carregar automato de um arquivo de texto;");
        System.out.println("3. Criar uma cópia do automato;");
        System.out.println("4. Calcular estados equivalentes;");
        System.out.println("5. Testar equivalencia entre 2 AFDs;");
        System.out.println("6. Calcular automato minimizado para um AFD fornecido;");
        System.out.println("7. Sair.");
        System.out.println("\n\tSua escolha: ");

    }

    public static void escreve_automato_texto(Automato automato){
        for (int i = 0; i < automatos.size(); i++) {
            try {
                Arquivo_Servico.escreve_arquivo("C:\\Users\\polo\\Desktop\\p_4\\LFA\\trabalho_pratico\\automatos_salvos_em_texto\\automato"+(i+1)+".txt", automato);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(" AFD salvo em um arquivo de texto!");
    }

    public static void le_automato_texto(Automato automato){
        System.out.println("\n\t-----------Automato carregado do arquivo de texto: --------------\n");
        for (int i = 0; i < automatos.size(); i++) {
            try {
                automato = Arquivo_Servico.le_arquivo("C:\\Users\\polo\\Desktop\\p_4\\LFA\\trabalho_pratico\\automatos_salvos_em_texto\\automato"+(i+1)+".txt");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static Automato seleciona_automato(){
        Automato automato = new Automato();
        int esc = -1;
        Scanner ler = new Scanner(System.in);

        for (int i = 0; i < automatos.size(); i++) {
            System.out.println((i+1) + " " + automatos.get(i).estados);
        }

        while(esc < 0 || esc > automatos.size()){
            System.out.println("Digite sua escolha: ");
            esc = ler.nextInt();

        }

        return automatos.get(esc-1);
    }

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
                    minimizando.transicoes.get(i).origem.equals(minimizando.transicoes.get(j).origem) &&
                    minimizando.transicoes.get(i).simbolo.equals(minimizando.transicoes.get(j).simbolo) &&
                    minimizando.transicoes.get(i).destino.equals(minimizando.transicoes.get(j).destino)
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

}
