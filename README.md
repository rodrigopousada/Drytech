📚 Sistema de Curadoria

Projeto acadêmico desenvolvido com foco em organização de dados, curadoria de conteúdos, ética digital, IA responsável e segurança da informação, utilizando tecnologias modernas da plataforma Java.

🚀 Sobre o Projeto

O Sistema de Curadoria é uma aplicação construída em Java 24, utilizando Java Swing para a interface gráfica. O objetivo é facilitar a análise, visualização e gerenciamento de registros de forma simples, rápida e segura.

O projeto segue uma estrutura modular sem uso de MVC, priorizando clareza, organização e manutenção direta do código.

 
🎯 Objetivo do Sistema

Otimizar e agilizar processos de curadoria

Registrar e consultar informações de forma prática

Filtrar e analisar dados com facilidade

Seguir princípios de ética digital e IA responsável

Garantir segurança e integridade dos dados


🛠️ Tecnologias Utilizadas

Java 24

Java Swing

Flyway (versionamento do banco de dados)

DAO Pattern

Banco de Dados Local MySQL 


🧩 Funcionalidades Implementadas

Cadastro de informações

Listagem e consulta de registros

Filtros e buscas

Interface gráfica intuitiva

Validações básicas

Migrations automáticas com Flyway

Estrutura modular e organizada

🗺️ Roadmap (Futuras Implementações)

 Autenticação de usuário

 Exportação de dados em CSV

 Dashboard analítico

 Tema escuro

 Curadoria automática com IA

 Logs e auditoria

 Mecanismos de segurança avançados
 

📦 Pré-requisitos

Antes de rodar o projeto, certifique-se de ter:

Java 24 instalado

MySQL ou SQLite

Maven ou Gradle (dependendo do seu projeto)

Flyway configurado

🗄️ Configurando o Banco + Flyway

Crie o banco de dados (ex: curadoria_db)

Configure a conexão no projeto

Coloque suas migrations neste caminho:

/resources
 └── db
      └── migration
           ├── V1__create_tables.sql
           ├── V2__insert_initial_data.sql
           └── ...


Flyway aplicará tudo automaticamente na inicialização.


📂 Estrutura do Projeto
/src
 ├── dao/
 ├── database/
 ├── migrations/
 ├── models/
 ├── ui/
 └── utils/
 

▶️ Como Executar o Projeto
1. Clone o repositório
git clone https://github.com/seu-usuario/sistema-curadoria.git

2. Abra a IDE

IntelliJ, Eclipse ou NetBeans.

3. Certifique-se de ter Java 24
java --version

4. Execute a classe principal
Main.java

🧪 Como gerar build (opcional)
Maven
mvn clean install
mvn package

Gradle
gradle build


🔐 Segurança & Ética Digital

O sistema segue diretrizes como:

Tratamento responsável de dados

Minimização de informações sensíveis

Estrutura preparada para logs e auditoria

Boas práticas de ética digital

Alinhamento com princípios de IA responsável


📸 Imagens do Sistema

![Tela Principal](<img width="1349" height="698" alt="unnamed" src="https://github.com/user-attachments/assets/4c24838a-27dd-40a1-9a57-43c3c73ed540" />)
![Exemplo de Uso](<img width="1361" height="711" alt="3dcb9b24-faa7-4df8-8270-b4554327493c" src="https://github.com/user-attachments/assets/6c976656-13d9-4982-97ef-ce99d4158e59" />)


👥 Equipe do Projeto

Desenvolvido por:

Felipe Muniz Felix da Costa
Rodrigo Pousada Vieira
Joao Ricardo Leoncio da Silva
Gabriel Francisco Alves Gomes 
Mariana Garcia Augusto
Jose Maxsuel Nogueira
Felipe Francisco Lemos Sales

“Este projeto só foi possível graças ao esforço, dedicação e parceria de toda a equipe envolvida.”


🐛 Issues / Bugs

Encontrou algum problema?
Abra uma issue descrevendo:

O que aconteceu

Passo a passo para reproduzir

Print ou mensagem de erro (se houver)
