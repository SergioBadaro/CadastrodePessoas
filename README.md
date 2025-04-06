# 🗂️ Cadastro de Pessoas

Sistema desenvolvido em **Java com Swing e MySQL** para realizar cadastro, consulta, edição, exclusão e exportação de dados de pessoas.

---

## 🎯 Funcionalidades

- Cadastro de pessoas com os campos:
  - Nome, Sobrenome, Idade, Telefone, Email, Gênero, Profissão, Cidade, Estado
- Tela com interface gráfica (Swing)
- Relatórios filtráveis por:
  - Profissão (dropdown)
  - Cidade (dropdown)
  - Ou exibição completa de todos os registros
- Exportação dos dados para arquivo `.csv`
- Tela de login com autenticação via banco de dados
- Cada usuário pode criar sua conta e fazer login
- Design organizado em abas
- Compatível com `.jar` executável
- Sistema modular e fácil de manter

---

## 🖥️ Tecnologias utilizadas

- `Java` (JDK 17+)
- `Swing` (interface gráfica)
- `MySQL` (persistência de dados)
- `JDBC` (integração com banco)
- `Git` / `GitHub` (controle de versão)

---

## 📁 Estrutura de Pastas

```
CadastroDePessoas/
├── src/
│   ├── MenuSwing.java
│   ├── Login.java
│   ├── Cadastro.java
│   └── Conexao.java
├── out/
│   └── artifacts/
│       └── CadastroDePessoas.jar
├── pessoas_exportadas.csv
├── cadastro.txt
├── README.md
└── run.bat
```

---

## ⚙️ Como executar

### ✅ Pré-requisitos:
- Java JDK instalado (versão 17 ou superior)
- MySQL instalado e configurado
- Banco de dados criado com a tabela `pessoas` e `usuarios`

### 📦 Executar via terminal:

```bash
java -jar CadastroDePessoas.jar
```

### 🖱️ Ou via duplo clique no arquivo `run.bat` (Windows)

---

## 🛢️ Estrutura da Tabela MySQL

### `pessoas`

```sql
CREATE TABLE pessoas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(50),
  sobrenome VARCHAR(50),
  idade INT,
  telefone VARCHAR(20),
  email VARCHAR(100),
  genero VARCHAR(20),
  profissao VARCHAR(50),
  cidade VARCHAR(50),
  estado VARCHAR(50)
);
```

### `usuarios`

```sql
CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL
);
```

---

## 🧪 Testes

Você pode testar:
- Cadastrar novas pessoas
- Filtrar relatórios
- Exportar dados
- Criar usuário e logar
- Verificar se os dados aparecem corretamente no banco

---

## 👨‍💻 Autor

Desenvolvido com 💙 por [Seu Nome Aqui]  
Entre em contato via [LinkedIn](https://www.linkedin.com)

---

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.