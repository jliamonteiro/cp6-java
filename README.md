- Julia Monteiro RM 557023
- Samuel Patrick RM 556461


# 🏦 CP6 - Java Advanced: Plataforma de Pagamentos Assíncrona com RabbitMQ

Este projeto foi desenvolvido para cumprir os requisitos do Desafio CP6 de Java Advanced, focado na implementação de uma arquitetura de microsserviços para processamento de transações bancárias de forma assíncrona.

## 🛠️ Detalhes Técnicos

* **IDE Utilizada:** **IntelliJ IDEA**
* **Framework Principal:** Spring Boot 3.x
* **Broker de Mensagens:** RabbitMQ (Utilizando CloudAMQP)
* **Banco de Dados:** Oracle SQL Developer
* **Linguagem:** Java 17+
* **Dependências:** Spring Data JPA, Spring Boot Starter AMQP, Spring Boot Starter Mail, Lombok.

---

## 🏛️ Arquitetura e Fluxo Assíncrono

O projeto implementa um modelo de "Produtor-Consumidor" com duas filas distintas para garantir que o processamento do pagamento não bloqueie o serviço principal, seguindo o diagrama proposto.

### Diagrama de Fluxo

**O fluxo se divide em Requisição e Resposta, passando por duas filas (Queues):**



### Detalhamento do Fluxo de Código

| Etapa | Componente(s) | Ação no Projeto |
| :--- | :--- | :--- |
| **1. Início da Compra** | `PurchaseController`, `TransactionService` | O endpoint recebe a compra, salva a `TransactionEntity` no Oracle com status **PENDING** e envia a transação para a **fila de Requisição** (`bank.transaction.queue`). |
| **2. Simulação do Banco** | `BankTransactionProcessor` | Consome a Fila de Requisição. Simula o processamento do Banco Tranquilo (atraso de 3s), define o status final (`APPROVED` ou `REJECTED`) e envia o resultado para a **fila de Resposta** (`bank.response.queue`). |
| **3. Fechamento e Notificação** | `EmailConsumer`, `TransactionRepository` | Ouve a Fila de Resposta. Atualiza o status final da transação no **BD Oracle**. **SE APROVADO**, utiliza o `EmailService` para enviar o e-mail de confirmação. |

---

## 🚀 Como Testar (Postman)

O projeto está pronto para ser testado via Postman, simulando um cliente que envia um pedido de compra.

1.  **Inicie** a aplicação Spring Boot.
2.  **Configure** o Postman conforme abaixo:

### Requisição

* **Método:** `POST`
* **URL:** `http://localhost:8080/api/v1/purchase`
* **Body:** `raw` / `JSON`

```json
{
  "orderId": "COMPRA_UNIQ_557023", 
  "buyerEmail": "seu-email-para-receber-a-confirmacao@gmail.com", 
  "amount": 350.50,
  "currency": "BRL",
  "cardNumber": "4111xxxxxxxx1111",
  "cardHolder": "Aluno Java"
}
