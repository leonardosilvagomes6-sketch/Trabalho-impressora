#🖨️ Sistema de Automação Comercial - Impressora Térmica Elgin
📝 Descrição do Projeto
Este software foi construído como requisito avaliativo para a disciplina de Algoritmos e Programação. Trata-se de uma aplicação console desenvolvida em Java projetada para gerenciar e enviar comandos para impressoras térmicas não fiscais da linha Elgin (modelos i8 e i9).

A comunicação entre a linguagem Java e o hardware é feita por meio da biblioteca nativa da Elgin (E1_Impressora01.dll), utilizando a tecnologia JNA (Java Native Access) como ponte de ligação. O sistema funciona como um mini-PDV (Ponto de Venda), oferecendo um ambiente seguro com login e um painel de controle para disparar comandos de hardware, como impressão de recibos, geração de códigos de barras, QR Codes, abertura de gaveta e emissão de alertas sonoros.

🛠️ Requisitos e Ferramentas
Para que o projeto funcione corretamente, o ambiente deve contar com:

Java Development Kit (JDK): Versão 11 ou superior.

Ambiente de Desenvolvimento: IntelliJ IDEA (recomendado para facilitar a importação das bibliotecas).

Biblioteca de Integração: JNA 5.15.0 (arquivo jna-5.15.0.jar).

Driver/Arquivo Nativo: E1_Impressora01.dll (versão 64 bits) fornecida pela Elgin.

Versionamento: Git e GitHub.

📂 Organização dos Arquivos
A estrutura de diretórios do projeto foi organizada da seguinte forma:

Main.java: Arquivo central que contém toda a lógica do sistema, menus e chamadas nativas.

E1_Impressora01.dll: Arquivo binário essencial para a comunicação com o equipamento.

libs Java/: Pasta que armazena a dependência jna-5.15.0.jar.

XMLSAT.xml e CANC_SAT.xml: Arquivos de simulação utilizados para testar as funções de impressão de cupons fiscais.

README.md: Manual de instruções e documentação do repositório.

🚀 Guia de Instalação e Uso
1. Obtenção do Código:
Faça o download do repositório ou utilize o comando abaixo no terminal:

bash
git clone https://github.com/seu-usuario/seu-repositorio.git
2. Preparando o IntelliJ IDEA:

Abra a IDE, vá em File > Open e selecione a pasta raiz do projeto. Aceite a confiança no projeto (Trust Project) se for solicitado.

Acesse File > Project Structure > Project e certifique-se de que um SDK Java (versão 11+) está selecionado.

3. Vinculando a Biblioteca JNA:

Ainda na tela de estrutura do projeto, acesse a aba Libraries.

Clique no ícone +, escolha Java e selecione o arquivo jna-5.15.0.jar que está na pasta libs Java. Aplique e confirme.

4. Configuração da DLL e Execução:

Verifique se o arquivo E1_Impressora01.dll está na mesma pasta do arquivo Main.java.

No menu superior, vá em Run > Edit Configurations. Na aba de configuração da classe principal, assegure-se de que o campo Working directory aponta para a pasta raiz do projeto (onde a DLL está localizada).

Conecte a impressora via cabo USB, ligue-a e clique em Run (▶) para iniciar o programa.

Nota técnica: Se o Java instalado em sua máquina for de 32 bits, será necessário substituir a DLL da raiz pela versão de 32 bits (localizada na pasta DLL 32 bits). Você pode verificar a arquitetura digitando java -version no prompt de comando.

🔑 Acesso ao Sistema
Para utilizar as funcionalidades, o sistema exige autenticação. O usuário tem direito a 3 tentativas de acesso.

Login: admin

Senha: 1234

🗂️ Painel de Opções
Após o login, o menu principal é exibido em loop até que a opção de saída seja escolhida. As funcionalidades são:

1 - Configuração: Define o tipo de conexão (USB, Serial, Rede), modelo da impressora e porta.

2 - Conectar: Estabelece a comunicação com o equipamento.

3 - Desconectar: Encerra a conexão ativa.

4 - Texto: Solicita um texto e a quantidade de vias, enviando para a impressão.

5 - QR Code: Gera e imprime um QR Code a partir de um texto informado.

6 - Código de Barras: Emite um código de barras no padrão CODE 128.

7 - Cupom SAT: Lê o arquivo XMLSAT.xml e imprime o cupom correspondente.

8 - Cancelamento SAT: Lê o arquivo CANC_SAT.xml e realiza a impressão de cancelamento com a chave de segurança.

9 - Gaveta: Aciona o pulso elétrico para abertura da gaveta de dinheiro.

10 - Alerta Sonoro: Emite bipes na impressora de acordo com a quantidade solicitada.

11 - Avanço: Move o papel para frente conforme o número de linhas desejado.

0 - Encerrar: Fecha a comunicação de forma segura e finaliza o programa.

Segurança: Todas as operações de hardware (impressão, gaveta, etc.) validam primeiramente se a conexão está ativa. Caso não esteja, o sistema bloqueia a ação e exibe uma mensagem de aviso, evitando erros de execução.

✅ Validações e Testes
O código foi submetido a testes para garantir a robustez do sistema, incluindo:

Estabelecimento de conexão USB com o modelo Elgin i9.

Impressão sequencial de textos em múltiplas vias.

Renderização de QR Codes e códigos de barras.

Leitura e envio de arquivos XML para a impressora.

Acionamento da gaveta e sinalizadores sonoros.

Tratamento de exceções (arquivos ausentes, erros de comunicação com a porta).

👨‍💻 Desenvolvedores
Chrislaine Oliveira da Silva — RA: 246659

Gabriel Costa Correia — RA: 250584

📚 Referências Bibliográficas e Técnicas
Documentação oficial do SDK da Elgin para impressoras térmicas.

Manual de integração e comandos ESC/POS (Elgin i8/i9).

Guia de uso da biblioteca Java Native Access (JNA).
