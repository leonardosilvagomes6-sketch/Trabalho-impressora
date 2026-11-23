import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import java.nio.file.*;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                Paths.get("E1_Impressora01.dll").toAbsolutePath().toString(), ImpressoraDLL.class); // DLL na raiz do projeto

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

        int FechaConexaoImpressora();

        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        int Corte(int avanco);

        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        int AvancaPapel(int linhas);

        int StatusImpressora(int param);

        int AbreGavetaElgin(int pino, int ti, int tf);

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

    // Login e senha corretos
    private static final String USUARIO = "admin";
    private static final String SENHA = "1234";

    // Chave do QR Code, usada no cancelamento do XML SAT
    private static final String ASS_QRCODE = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgIQ7ZV+vHcRjCZBr7VRh0rH1X6BnVAFDx3wBXxs7fjJ4XDI7PFdYlSjnOmTZaEZQBZbxM8t8xj06QHYAgY8D4A/dPfbfG2QuA==";

    private static boolean conexaoAberta = false;
    private static int tipo = 1;               // 1 = USB (valor padrão para teste)
    private static String modelo = "i9";       // "i8" ou "i9"
    private static String conexao = "USB";
    private static int parametro = 0;
    private static final Scanner scanner = new Scanner(System.in); // Scanner estático e final

    // Função para capturar entradas do usuário
    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    // Mostra a mensagem e devolve um número inteiro. Se digitar letras, pergunta de novo.
    private static int lerInteiro(String mensagem) {
        while (true) {
            String texto = capturarEntrada(mensagem).trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite apenas números.");
            }
        }
    }

    // Lê um arquivo XML e devolve o texto dele (ou null se não achar)
    private static String lerArquivo(String nome) {
        try {
            return new String(Files.readAllBytes(Paths.get(nome)), java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Não foi possível ler o arquivo: " + nome);
            return null;
        }
    }

    // Função para configurar conexão
    public static void configurarConexao() {
        tipo = lerInteiro("Tipo de conexão (1 = USB, 2 = Serial, 3 = Rede, 4 = Bluetooth): ");
        modelo = capturarEntrada("Modelo da impressora (i8 ou i9): ");
        conexao = capturarEntrada("Conexão/porta (ex.: USB): ");
        parametro = lerInteiro("Parâmetro (0 para USB): ");
        System.out.println("Configuração salva.");
    }

    public static void abrirConexao() {
        // Função para abrir a conexão com a impressora
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    public static void fecharConexao() {
        // Função para fechar a conexão com a impressora
        if (!conexaoAberta) {
            System.out.println("Conexão já está fechada.");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
        if (retorno == 0) {
            conexaoAberta = false;
            System.out.println("Conexão fechada com sucesso.");
        } else {
            System.out.println("Erro ao fechar conexão. Código de erro: " + retorno);
        }
    }

    // Pede usuário e senha (3 tentativas). Devolve true se acertar e false se errar as 3.
    public static boolean fazerLogin() {
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            String usuario = capturarEntrada("Usuário: ");
            String senha = capturarEntrada("Senha: ");

            if (usuario.equals(USUARIO) && senha.equals(SENHA)) {
                System.out.println("Login realizado com sucesso!");
                return true;
            }

            int restantes = 3 - tentativa;
            if (restantes > 0) {
                System.out.println("Usuário ou senha incorretos. Tentativas restantes: " + restantes);
            }
        }
        System.out.println("Número máximo de tentativas excedido. Programa encerrado.");
        return false;
    }

    // Opção 4: imprime um texto (exemplo pronto do professor)
    public static void imprimirTexto() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String texto = capturarEntrada("Texto a imprimir: ");
        int copias = lerInteiro("Quantas cópias? ");
        for (int i = 1; i <= copias; i++) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto, 1, 4, 0);
            if (retorno >= 0) {
                System.out.println("Cópia " + i + " impressa!");
            } else {
                System.out.println("Erro ao imprimir. Código: " + retorno);
            }
        }
        ImpressoraDLL.INSTANCE.Corte(5); // corta o papel
    }

    // Opção 5: imprime um QR Code
    public static void imprimirQRCode() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String dados = capturarEntrada("Conteúdo do QR Code: ");
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, 6, 4);
        if (retorno == 0) {
            System.out.println("QR Code impresso!");
        } else {
            System.out.println("Erro ao imprimir QR Code. Código: " + retorno);
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    // Opção 6: imprime um código de barras (CODE 128)
    public static void imprimirCodigoBarras() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
        if (retorno == 0) {
            System.out.println("Código de barras impresso!");
        } else {
            System.out.println("Erro ao imprimir código de barras. Código: " + retorno);
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    // Opção 7: imprime o cupom a partir de um XML SAT
    public static void imprimirXMLSAT() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String nome = "XMLSAT.xml"; // arquivo na raiz do projeto, lido direto
        String xml = lerArquivo(nome);
        if (xml == null) {
            return; // lerArquivo já avisou o erro
        }
        int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(xml, 0);
        if (retorno == 0) {
            System.out.println("Cupom SAT impresso!");
        } else {
            System.out.println("Erro ao imprimir XML SAT. Código: " + retorno);
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    // Opção 8: imprime o cancelamento do SAT
    public static void imprimirXMLCancelamentoSAT() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String nome = "CANC_SAT.xml"; // arquivo na raiz do projeto, lido direto
        String xml = lerArquivo(nome);
        if (xml == null) {
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(xml, ASS_QRCODE, 0);
        if (retorno == 0) {
            System.out.println("Cancelamento SAT impresso!");
        } else {
            System.out.println("Erro ao imprimir cancelamento SAT. Código: " + retorno);
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    // Opção 9: abre a gaveta de dinheiro
    public static void abrirGaveta() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);
        if (retorno == 0) {
            System.out.println("Gaveta aberta!");
        } else {
            System.out.println("Erro ao abrir a gaveta. Código: " + retorno);
        }
    }

    // Opção 10: sinal sonoro
    public static void sinalSonoro() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int quantidade = lerInteiro("Quantos bips? ");
        int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(quantidade, 50, 5);
        if (retorno == 0) {
            System.out.println("Bip!");
        } else {
            System.out.println("Erro no sinal sonoro. Código: " + retorno);
        }
    }

    // Opção 11 (extra): avança o papel
    public static void avancarPapel() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int linhas = lerInteiro("Quantas linhas avançar? ");
        int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);
        if (retorno == 0) {
            System.out.println("Papel avançado!");
        } else {
            System.out.println("Erro ao avançar o papel. Código: " + retorno);
        }
    }

    public static void exibirMenu() {
        System.out.println("\n*************************************************");
        System.out.println("**************** MENU IMPRESSORA *******************");
        System.out.println("*************************************************\n");

        System.out.println("1  - Configurar Conexao");
        System.out.println("2  - Abrir Conexao");
        System.out.println("3  - Fechar Conexao");
        System.out.println("4  - Imprimir Texto");
        System.out.println("5  - Imprimir QRCode");
        System.out.println("6  - Imprimir Codigo de Barras");
        System.out.println("7  - Imprimir XML SAT");
        System.out.println("8  - Imprimir XML Cancelamento SAT");
        System.out.println("9  - Abrir Gaveta");
        System.out.println("10 - Sinal Sonoro");
        System.out.println("11 - Avancar Papel");
        System.out.println("0  - Sair");
    }

    public static void main(String[] args) throws java.io.IOException {
        // Errou 3 vezes: encerra o programa
        if (!fazerLogin()) {
            scanner.close();
            return;
        }

        while (true) {
            exibirMenu();
            int opcao = lerInteiro("\nDigite a opção desejada: ");

            if (opcao == 1) {
                configurarConexao();
            } else if (opcao == 2) {
                abrirConexao();
            } else if (opcao == 3) {
                fecharConexao();
            } else if (opcao == 4) {
                imprimirTexto();
            } else if (opcao == 5) {
                imprimirQRCode();
            } else if (opcao == 6) {
                imprimirCodigoBarras();
            } else if (opcao == 7) {
                imprimirXMLSAT();
            } else if (opcao == 8) {
                imprimirXMLCancelamentoSAT();
            } else if (opcao == 9) {
                abrirGaveta();
            } else if (opcao == 10) {
                sinalSonoro();
            } else if (opcao == 11) {
                avancarPapel();
            } else if (opcao == 0) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        scanner.close(); // Fecha o scanner ao final do programa
    }
}