 📘 Projeto – Sistema PDV com Impressora Elgin i8/i9

 📖 Sobre o Projeto

Este projeto foi desenvolvido como parte da disciplina de **Algoritmos e Programação**. O objetivo é criar uma aplicação Java capaz de se comunicar com a impressora térmica **Elgin i8/i9**, utilizando a biblioteca oficial da Elgin (`E1_Impressora01.dll`) e a ponte JNA (Java Native Access).

O programa simula um sistema de ponto de venda (PDV) simplificado, permitindo realizar diversas operações, como:

- 🔐 Login e autenticação de usuário;
- 🖨️ Configuração e conexão com a impressora;
- 📝 Impressão de textos;
- 📱 Impressão de QR Codes;
- 📊 Impressão de códigos de barras;
- 🧾 Impressão de cupons fiscais (XML SAT);
- 💰 Abertura da gaveta de dinheiro;
- 🔔 Emissão de sinais sonoros.

Todo o desenvolvimento foi realizado utilizando conceitos fundamentais da programação, como variáveis, estruturas condicionais (`if/else`), laços de repetição (`for` e `while`), vetores e métodos.

---

 🧰 Tecnologias Utilizadas

As seguintes tecnologias e ferramentas foram utilizadas no desenvolvimento:

- **Java:** JDK 11 ou superior;
- **IntelliJ IDEA:** IDE recomendada para desenvolvimento;
- **JNA (Java Native Access):** versão 5.15.0 (`jna-5.15.0.jar`);
- **DLL da Elgin:** `E1_Impressora01.dll` (versão 64 bits);
- **Git e GitHub:** para versionamento e hospedagem do projeto.

---

 📁 Estrutura do Projeto

A organização dos arquivos está apresentada abaixo:

```text
/
├── Main.java                  # Código-fonte principal (único arquivo editável)
├── E1_Impressora01.dll        # Biblioteca da impressora (64 bits)
├── libs Java/
│   └── jna-5.15.0.jar         # Biblioteca de comunicação JNA
├── XMLSAT.xml                 # Arquivo de teste para impressão SAT
├── CANC_SAT.xml               # Arquivo de teste para cancelamento SAT
└── README.md                  # Documentação do projeto
```

---

 ⚙️ Como Executar o Projeto

 🔽 1. Clonar ou Baixar o Projeto

Acesse o repositório no GitHub e faça o download dos arquivos pelo botão **Download ZIP** ou clone o projeto utilizando o Git:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

> **Observação:** Substitua o endereço acima pelo link real do repositório.

 🧭 2. Importar no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Clique em **File → Open**.
3. Selecione a pasta raiz do projeto.
4. Caso solicitado, clique em **Trust Project**.

 📦 3. Configurar o JDK e a Biblioteca JNA

1. Acesse **File → Project Structure → Project**.
2. Em **SDK**, selecione um JDK 11 ou superior.
3. Acesse a seção **Libraries**.
4. Clique no botão **+ → Java**.
5. Selecione o arquivo `libs Java/jna-5.15.0.jar`.
6. Confirme a configuração.

Após essa etapa, os erros relacionados ao pacote `com.sun.jna` deverão desaparecer.

 🖨️ 4. Configurar a DLL e o Working Directory

1. Certifique-se de que o arquivo `E1_Impressora01.dll` esteja na raiz do projeto, no mesmo nível do arquivo `Main.java`.
2. Acesse **Run → Edit Configurations**.
3. Selecione a configuração da classe `Main`.
4. Verifique se o campo **Working directory** aponta para a pasta raiz do projeto.
5. Ligue a impressora e conecte o cabo USB ao computador antes de executar o programa.

 ▶️ 5. Executar o Projeto

1. Abra o arquivo `Main.java`.
2. Clique no botão **Run (▶️)** ou utilize o atalho `Shift + F10`.
3. Aguarde a inicialização do sistema e utilize o menu interativo.

> **⚠️ Nota sobre arquitetura 32/64 bits**
>
> A DLL disponibilizada na raiz do projeto é de 64 bits. Caso esteja utilizando uma versão do Java de 32 bits, substitua a DLL pela versão disponível na pasta `DLL 32 bits (x86)`.
>
> Para verificar a arquitetura do Java instalado, execute o comando:
>
> ```bash
> java -version
> ```

---

 🔐 Login de Teste

O sistema possui autenticação de usuário com limite máximo de **3 tentativas de acesso**.

Utilize as seguintes credenciais para acessar o sistema:

| Campo | Credencial |
|:---|:---|
| **Usuário** | `admin` |
| **Senha** | `1234` |

 🛡️ Regra de Segurança

Todos os métodos de impressão verificam se a conexão com a impressora está aberta antes de solicitar qualquer informação ao usuário.

Caso a conexão esteja fechada, o sistema exibe um aviso e retorna automaticamente ao menu principal, evitando solicitações desnecessárias e possíveis erros durante a execução.

---

 🧪 Testes Realizados

Durante o desenvolvimento, foram realizados testes para verificar o funcionamento das principais funcionalidades do sistema.

| Teste | Status |
|:---|:---:|
| Conexão via USB com a impressora Elgin i9 | ✅ |
| Impressão de texto simples com múltiplas cópias | ✅ |
| Impressão de QR Code | ✅ |
| Impressão de código de barras | ✅ |
| Leitura e impressão do arquivo `XMLSAT.xml` | ✅ |
| Leitura e impressão do arquivo `CANC_SAT.xml` | ✅ |
| Abertura da gaveta de dinheiro | ✅ |
| Emissão de sinal sonoro | ✅ |
| Avanço de papel | ✅ |
| Tratamento de erros de conexão fechada | ✅ |
| Tratamento de retorno negativo da impressora | ✅ |
| Tratamento de arquivo não encontrado | ✅ |

---

 👥 Autores

Projeto desenvolvido em grupo pelos seguintes integrantes:

- **Chrislaine Oliveira Da Silva - RA: 246659**
- **Gabriel Costa Correia - RA: 250584**

---

📚 Referências

- Manual da Impressora Elgin i8/i9.
- Documentação do SDK Elgin — Elgin Developer Community.
- Documentação oficial da biblioteca [JNA (Java Native Access)](https://github.com/java-native-access/jna).

---

**Projeto acadêmico desenvolvido para a disciplina de Algoritmos e Programação.**
