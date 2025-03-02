# JavaFX CRUD - Estudo de JavaFX com MySQL

## 📌 Sobre o Projeto

Este projeto foi desenvolvido com base no curso do Professor Nélio Alves com o objetivo de estudar e praticar o desenvolvimento de aplicações desktop utilizando **JavaFX** integrado com um banco de dados **MySQL**. 
A aplicação implementa um **CRUD completo** para duas entidades: **Seller** (Vendedor) e **Department** (Departamento), permitindo a realização de operações básicas como criação, leitura, atualização e exclusão de registros.

## 🛠 Tecnologias Utilizadas

- **Java SE 21**
- **JavaFX SDK 23.0.2**
- **MySQL**
- **JDBC**

## 📂 Estrutura do Projeto

O projeto possui uma estrutura simples, focada no aprendizado e seguindo o padrão MVC (Model-View-Controller). As classes são:

### 📌 Entidades

- **Seller**: Representa um vendedor, contendo um atributo referenciando um **Department**.
- **Department**: Representa um departamento dentro da empresa.

### 🔗 Relacionamento

- Cada **Seller** pertence a um **Department**.
- Um **Department** pode ter vários **Sellers** associados.

## ⚙ Funcionalidades Implementadas

✔ CRUD completo para **Seller** (Vendedor) e **Department** (Departamento).
✔ Interface gráfica desenvolvida com **JavaFX**. 
✔ Conexão com **MySQL** utilizando **JDBC** puro.
✔ Atualização dinâmica da tabela conforme operações são realizadas.
✔ Tratamento de exceções para garantir estabilidade do sistema.

## 🖥 Demonstração do Funcionamento

A aplicação apresenta uma interface simples, onde é possível:

1. **Listar** os registros armazenados no banco de dados.
2. **Adicionar** novos vendedores e departamentos.
3. **Atualizar** registros existentes.
4. **Excluir** registros de maneira segura.

## 🛠 Como Executar o Projeto

### 📌 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **JDK 21**
- **JavaFX SDK 23.0.2**
- **Servidor MySQL**

### 📌 Passos para execução

1. **Clonar o repositório**
   ```bash
   git clone [https://github.com/seu-usuario/javafx-study.git](https://github.com/FabioBritto/javafx-study.git)
   cd javafx-study
   ```
2. **Configurar o banco de dados**
   - Criar um banco de dados MySQL e executar os scripts SQL para as tabelas.
3. **Executar o projeto**
   - Configurar corretamente o caminho do JavaFX no ambiente de execução.
   - Rodar a aplicação através da classe principal.

## 📬 Contato

📧 E-mail: [fabio.tritono@gmail.com](mailto\:fabio.tritono@gmail.com)\
🐙 LinkedIn: [Meu LinkedIn](https://www.linkedin.com/in/fabio-britto-399223252/)

Embora o JavaFX não seja mais amplamente utilizado no mercado, este projeto teve um propósito claro: explorar o desenvolvimento de uma aplicação completa, do backend ao frontend, dentro do ecossistema Java. A ideia foi aprofundar meus conhecimentos sem a necessidade de recorrer a outras tecnologias para a interface gráfica, permitindo uma experiência prática de ponta a ponta. 🚀

