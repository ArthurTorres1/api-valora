# Valora API 🚀

API do sistema de gestão financeira Valora, desenvolvida com Spring Boot. Este projeto utiliza o **Flyway** para migrações automatizadas de banco de dados, garantindo que toda a equipe tenha a mesma estrutura sem precisar rodar scripts SQL manualmente.

## 🛠️ Tecnologias Utilizadas
* **Java 21**
* **Spring Boot 3**
* **Spring Data JPA / Hibernate**
* **Flyway** (Database Migrations)
* **Oracle Database**
* **Lombok**

---

## ⚙️ Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:
* [Java 21](https://jdk.java.net/21/)
* Maven
* IDE de sua preferência (recomendamos o **IntelliJ IDEA**)
* Acesso a um banco de dados Oracle (ex: ambiente FIAP)

---

## 🚀 Como executar o projeto localmente

### 1. Clone o repositório
```bash
git clone [https://github.com/ArthurTorres1/api-valora.git](https://github.com/ArthurTorres1/api-valora.git)
cd api-valora
```

### 2. Configure as Variáveis de Ambiente (MUITO IMPORTANTE) ⚠️
Por questões de segurança, as credenciais do banco de dados **não estão no código-fonte**. O arquivo `application.properties` espera receber três variáveis de ambiente para se conectar ao banco. 

Você deve configurar as seguintes variáveis na sua IDE:

| Variável | Descrição | Exemplo de Valor |
| :--- | :--- | :--- |
| `DB_URL` | URL de conexão JDBC do Oracle | `jdbc:oracle:thin:@//HOST:PORT/SID` |
| `DB_USER` | Seu usuário do banco de dados | `RM123456` |
| `DB_PASSWORD` | Sua senha do banco de dados | `sua_senha_secreta` |

**Como configurar no IntelliJ IDEA:**
1. Abra o arquivo `ApiApplication.java` e clique no botão verde de "Play" (Run) ao lado do método `main`. Vai dar erro, é normal.
2. No menu superior, ao lado do botão de Play, clique em **`ApiApplication`** > **`Edit Configurations...`**.
3. Procure o campo **`Environment variables`** (se não estiver visível, clique no texto azul `Modify options` e ative-o).
4. Clique no ícone de pasta/tabela no final da linha e adicione as três variáveis acima com os seus dados reais.
5. Clique em `Apply` e `OK`.

### 3. Inicie a aplicação
Com as variáveis configuradas, basta rodar a aplicação através da classe `ApiApplication.java`.

> 🪄 **A Mágica do Flyway:** Você não precisa rodar nenhum script SQL de `CREATE TABLE`! Assim que a aplicação iniciar pela primeira vez, o Flyway detectará o seu banco de dados e construirá todas as tabelas e relacionamentos automaticamente.

---

## 📌 Estrutura do Banco de Dados
A criação das tabelas é gerenciada pela pasta `src/main/resources/db/migration`. 
* O projeto possui a configuração `baseline-on-migrate=true` e `baseline-version=0`. 
* Isso significa que ele funcionará perfeitamente tanto se o seu banco Oracle estiver 100% vazio, quanto se você tiver tabelas residuais de outras aulas nele.
