import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;

public class Main {

    // Interface que representa a DLL usando JNA.
    // Cada função declarada aqui é automaticamente vinculada à DLL.
    public interface ImpressoraDLL extends Library {

        // Carregamento da DLL. O caminho deve ser válido no computador onde está executando.
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\kalebe_santana\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll",
                ImpressoraDLL.class);

        // Métodos da DLL disponibilizados pela Elgin
        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin();
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    // Variáveis de configuração e status
    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;

    // Scanner único global
    private static final Scanner scanner = new Scanner(System.in);

    // Método auxiliar para capturar entradas do usuário
    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    // Configura os parâmetros necessários antes de abrir a conexão
    public static void configurarConexao() {
        if (!conexaoAberta) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o tipo de conexão (1 USB, 2 Serial, 3 TCP/IP, 4 Bluetooth, 5 Android): ");
            tipo = scanner.nextInt();

            System.out.println("Digite o modelo da impressora: ");
            modelo = scanner.nextLine(); // limpa buffer
            scanner.nextLine();          // necessário por causa do nextInt

            System.out.println("Digite o tipo de conexão (USB, RS232, TCP/IP): ");
            conexao = scanner.nextLine();

            System.out.println("Digite o parametro (0): ");
            parametro = scanner.nextInt();

        }
    }

    // Tenta abrir a conexão com a impressora
    public static void abrirConexao() {

        // Todas as chamadas devem usar ImpressoraDLL.INSTANCE.nomeDaFuncao
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);

            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }

        } else {
            System.out.println("A conexão já está aberta.");
        }
    }

    // Fecha a conexão se estiver aberta
    public static void fecharConexao() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso.");
            } else {
                System.out.println("Erro ao fechar conexão. Código de erro: " + retorno);
            }

        } else {
            System.out.println("A conexão não está aberta.");
        }
    }

    // Envia um texto simples para a impressora
    public static void ImpressaoTexto() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto("teste", 1, 4, 0);

            if (retorno == 0) {
                System.out.println("Impressão de texto concluída.");
            } else {
                System.out.println("Erro ao imprimir texto. Código de erro: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes de imprimir.");
        }
    }

    // Realiza o corte do papel
    public static void Corte() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.Corte(3);

            if (retorno == 0) {
                System.out.println("Corte realizado.");
            } else {
                System.out.println("Erro ao cortar. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes de cortar.");
        }
    }

    // Imprime um QRCode simples
    public static void ImpressaoQRCode() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);

            if (retorno == 0) {
                System.out.println("QRCode impresso.");
            } else {
                System.out.println("Erro ao imprimir QRCode. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes de imprimir.");
        }
    }

    // Imprime um código de barras
    public static void ImpressaoCodigoBarras() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);

            if (retorno == 0) {
                System.out.println("Código de barras impresso.");
            } else {
                System.out.println("Erro ao imprimir. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes de imprimir.");
        }
    }

    // Avança o papel
    public static void AvancaPapel() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(2);

            if (retorno == 0) {
                System.out.println("Papel avançado.");
            } else {
                System.out.println("Erro ao avançar papel. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes de avançar papel.");
        }
    }

    // Abre gaveta padrão Elgin
    public static void AbreGavetaElgin() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin();

            if (retorno == 0) {
                System.out.println("Gaveta aberta.");
            } else {
                System.out.println("Erro ao abrir gaveta. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes de abrir gaveta.");
        }
    }

    // Abre gaveta genérica
    public static void AbreGaveta() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);

            if (retorno == 0) {
                System.out.println("Gaveta aberta.");
            } else {
                System.out.println("Erro ao abrir gaveta. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes.");
        }
    }

    // Dispara o sinal sonoro da impressora
    public static void SinalSonoro() {
        if (conexaoAberta) {

            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);

            if (retorno == 0) {
                System.out.println("Sinal sonoro emitido.");
            } else {
                System.out.println("Erro ao emitir sinal. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes.");
        }
    }

    // Imprime XML de venda SAT
    public static void ImprimeXMLSAT() {
        if (conexaoAberta) {

            String dados = "path=C:\\Users\\kalebe_santana\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\XMLSAT.xml";

            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, 0);

            if (retorno == 0) {
                System.out.println("XML SAT impresso.");
            } else {
                System.out.println("Erro ao imprimir XML. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes.");
        }
    }

    // Imprime XML de cancelamento SAT
    public static void ImprimeXMLCancelamentoSAT() {
        if (conexaoAberta) {

            String dados = "path=C:\\Users\\kalebe_santana\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\CANC_SAT.xml";

            // Assinatura necessária para o QRCode do cancelamento SAT
            String assQRcode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dados, assQRcode, 0);

            if (retorno == 0) {
                System.out.println("XML de cancelamento impresso.");
            } else {
                System.out.println("Erro ao imprimir XML. Código: " + retorno);
            }

        } else {
            System.out.println("Abra a conexão antes.");
        }
    }

    // Menu principal
    public static void main(String[] args) {

        while (true) {

            // Exibe menu
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA ****************");
            System.out.println("*************************************************\n");

            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4  - Impressao QRCode");
            System.out.println("5  - Impressao Cod Barras");
            System.out.println("6  - Impressao XML SAT");
            System.out.println("7  - Impressao XML Cancelamento SAT");
            System.out.println("8  - Abrir Gaveta Elgin");
            System.out.println("9  - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0  - Fechar Conexao e Sair");

            // Pede a opção do usuário
            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            // Encerra o programa
            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            }

            // Executa a ação correspondente
            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao();
                    ImpressoraDLL.INSTANCE.Corte(4);
                    break;
                case "3":
                    ImpressaoTexto();
                    ImpressoraDLL.INSTANCE.Corte(4);
                    break;
                case "4":
                    ImpressaoQRCode();
                    ImpressoraDLL.INSTANCE.Corte(4);
                    break;
                case "5":
                    ImpressaoCodigoBarras();
                    ImpressoraDLL.INSTANCE.Corte(4);
                    break;
                case "6":
                    ImprimeXMLSAT();
                    ImpressoraDLL.INSTANCE.Corte(4);
                    break;
                case "7":
                    ImprimeXMLCancelamentoSAT();
                    ImpressoraDLL.INSTANCE.Corte(4);
                    break;
                case "8":
                    AbreGavetaElgin();
                    break;
                case "9":
                    AbreGaveta();
                    break;
                case "10":
                    SinalSonoro();
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }

        scanner.close();
    }

    // Método auxiliar para ler arquivos completos como String
    private static String lerArquivoComoString(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}