import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Arquivo_Servico {

    //escreve o alfabeto, os estados, estado inicial, estados finais e as transicoes.
    public static void escreve_arquivo (String caminho, Automato automato) throws Exception{
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document documento = documentBuilder.newDocument();

        //Tag estrutura
        Element tagAutomato = documento.createElement("automato");
        documento.appendChild(tagAutomato);

        //Alfabeto
        for (int i = 0; i < automato.alfabeto.size(); i++) {
            Element tagLetra = documento.createElement("letra");
            tagLetra.appendChild(documento.createTextNode(automato.alfabeto.get(i)));
            tagAutomato.appendChild(tagLetra);
        }

        //Estados
        for (int i = 0; i < automato.estados.size(); i++) {
            Element tagEstado = documento.createElement("estado");
            tagEstado.appendChild(documento.createTextNode(automato.estados.get(i)));
            tagAutomato.appendChild(tagEstado);
        }

        //Estado Inicial
        for (int i = 0; i < automato.estadoInicial.size(); i++) {
            Element tagEstadoInicial = documento.createElement("estado_inicial");
            tagEstadoInicial.appendChild(documento.createTextNode(automato.estadoInicial.get(i)));
            tagAutomato.appendChild(tagEstadoInicial);
        }
        //Estados Finais
        for (int i = 0; i < automato.estadoFinal.size(); i++) {
            Element tagEstadoFinal = documento.createElement("estado_final");
            tagEstadoFinal.appendChild(documento.createTextNode(automato.estadoFinal.get(i)));
            tagAutomato.appendChild(tagEstadoFinal);
        }

        //Transições
        for (int i = 0; i < automato.transicoes.size(); i++) {
            Element tagTransicao = documento.createElement("transicao");
            Element tagOrigem = documento.createElement("origem");
            Element tagDestino = documento.createElement("destino");
            Element tagSimbolo = documento.createElement("simbolo");

            tagOrigem.appendChild(documento.createTextNode(automato.transicoes.get(i).origem));
            tagDestino.appendChild(documento.createTextNode(automato.transicoes.get(i).destino));
            tagSimbolo.appendChild(documento.createTextNode(automato.transicoes.get(i).simbolo));

            tagTransicao.appendChild(tagOrigem);
            tagTransicao.appendChild(tagDestino);
            tagTransicao.appendChild(tagSimbolo);

            tagAutomato.appendChild(tagTransicao);
        }

        //Cria o XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(documento);
        StreamResult streamResult = new StreamResult(new File(String.valueOf(caminho)));
        transformer.transform(domSource, streamResult);
    }

    //lê o alfabeto, os estados, estado inicial, estados finais e as transicoes.
    public static Automato le_arquivo(String caminho) throws Exception{
        Automato retorno = new Automato();

        File fXmlFile = new File(caminho);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        //Alfabeto
        NodeList nList = doc.getElementsByTagName("letra");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                retorno.alfabeto.add(eElement.getFirstChild().getTextContent());
            }
        }

        //Estados
        nList = doc.getElementsByTagName("estado");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                retorno.estados.add(eElement.getFirstChild().getTextContent());
            }
        }

        //Estado Inicial
        nList = doc.getElementsByTagName("estado_inicial");
        Node nNode1 = nList.item(0);
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                retorno.estadoInicial.add(eElement.getFirstChild().getTextContent());
            }
        }

        //Estado Final
        nList = doc.getElementsByTagName("estado_final");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                retorno.estadoFinal.add(eElement.getFirstChild().getTextContent());
            }
        }

        //Transicoes
        nList = doc.getElementsByTagName("transicao");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                retorno.transicoes.add(new Transicao(
                        eElement.getElementsByTagName("origem").item(0).getTextContent(),
                        eElement.getElementsByTagName("destino").item(0).getTextContent(),
                        eElement.getElementsByTagName("simbolo").item(0).getTextContent()
                ));
            }
        }

        return retorno;
    }
}
