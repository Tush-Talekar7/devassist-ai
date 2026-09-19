# DevAssist AI 

DevAssist AI is a **Generative AI-powered document assistant** that allows organizations to build a searchable knowledge base from internal documents.

HR/Admin users can upload company documents, while employees can ask questions in natural language. The application uses **Retrieval-Augmented Generation (RAG)** to retrieve relevant information from the uploaded documents and generate context-aware answers using an LLM.

The project is built as a **Java/Spring Boot microservices application** with Spring AI and a vector database.

---

##  Project Objective

The goal of DevAssist AI is to build a practical enterprise-style GenAI application while combining:

* Java backend development
* Spring Boot microservices
* Generative AI
* LLMs
* RAG
* Embeddings
* Vector databases
* Semantic search
* Document processing
* Secure REST APIs
* Kafka-based asynchronous processing
* Dockerized deployment

The project is also being developed as a hands-on learning project to understand how modern GenAI applications are designed and implemented.

---

## Architecture

### Current high-level flow

```text
                    ┌─────────────────┐
                    │   HR / ADMIN    │
                    └────────┬────────┘
                             │
                       Upload Document
                             │
                             ▼
                  ┌─────────────────────┐
                  │  Document Service   │
                  │    Spring Boot      │
                  └──────────┬──────────┘
                             │
                    Store PDF + DB record
                             │
                             ▼
                  ┌─────────────────────┐
                  │     AI Service      │
                  │    Spring AI        │
                  └──────────┬──────────┘
                             │
                    Extract document text
                             │
                         Chunking
                             │
                       Embeddings
                             │
                             ▼
                  ┌─────────────────────┐
                  │      pgvector       │
                  │   Vector Database   │
                  └──────────┬──────────┘
                             │
                        Similarity Search
                             │
                             ▼
                         User Query
                             │
                             ▼
                     Relevant Context
                             │
                             ▼
                       ChatClient
                             │
                             ▼
                         LLM / Ollama
                             │
                             ▼
                          Answer
```

---

#  Current Features

## 1. Authentication & Authorization

The authentication service provides:

* User registration
* User login
* JWT-based authentication
* BCrypt password hashing
* Role-based access
* USER and ADMIN roles

---

## 2. Document Management

Administrators can upload documents through the document service.

Current capabilities include:

* PDF upload
* Document metadata storage
* File storage
* Document status management
* Secure document download
* Integration with the AI service

Example document lifecycle:

```text
UPLOADED
   ↓
PROCESSING
   ↓
PROCESSED
```

---

## 3. PDF Processing

Uploaded PDFs are processed by the AI service.

The processing pipeline is:

```text
PDF
 ↓
PDF Text Extraction
 ↓
Page-level Documents
 ↓
Text Chunking
 ↓
Metadata
 ↓
Embeddings
 ↓
Vector Store
```

Spring AI's PDF document reader is used for document extraction.

---

## 4. Text Chunking

Large documents are divided into smaller chunks before generating embeddings.

This allows the application to retrieve only the relevant portions of a document instead of sending an entire document to the LLM.

The project currently uses Spring AI's `TokenTextSplitter`.

---

## 5. Embeddings

The application converts document chunks into vector representations using an embedding model.

Current local embedding model:

```text
qwen3-embedding:0.6b
```

Embeddings allow the application to perform semantic rather than simple keyword-based search.

---

## 6. Vector Database

The project uses:

**PostgreSQL + pgvector**

The vector store contains:

* Document chunks
* Embeddings
* Metadata
* Page information
* Chunk information

Spring AI's `VectorStore` abstraction is used to interact with the vector database.

---

## 7. Semantic Search

Users' questions are converted into embeddings and compared against stored document vectors.

Example:

```text
Question:
"What is the company's leave policy?"

              ↓

       Query Embedding

              ↓

        pgvector Search

              ↓

Relevant Document Chunks
```

The system uses:

* Top-K retrieval
* Similarity threshold

to control which chunks are passed to the LLM.

---

#  Retrieval-Augmented Generation (RAG)

DevAssist AI uses RAG instead of sending user questions directly to the LLM.

### RAG flow

```text
User Question
      ↓
Generate Query Embedding
      ↓
Semantic Search
      ↓
Retrieve Relevant Chunks
      ↓
Build Context
      ↓
Question + Context
      ↓
ChatClient
      ↓
LLM
      ↓
Generated Answer
```

This allows the application to answer questions using information from the organization's uploaded documents.

If the retrieved context does not contain enough information, the application instructs the LLM not to rely on unrelated knowledge.

---

#  LLM Integration

The project currently uses **Ollama** for local LLM inference.

Current chat model:

```text
qwen3:1.7b
```

Spring AI provides the abstraction layer between the application and the AI model.

Important Spring AI components used in the project:

```text
ChatClient
ChatModel
EmbeddingModel
VectorStore
Document
SearchRequest
```

---

#  Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Spring Security
* JWT
* Maven

### AI / GenAI

* Spring AI
* Large Language Models
* Retrieval-Augmented Generation (RAG)
* Embeddings
* Prompt Engineering
* Semantic Search
* Ollama

### Vector Database

* PostgreSQL
* pgvector

### Messaging

* Apache Kafka *(planned)*

### Frontend

* Angular
* TypeScript *(planned/in development)*

### DevOps

* Docker
* Docker Compose *(planned)*
* Postman

### Testing

* JUnit
* Mockito
* Testcontainers *(planned)*

---

#  Microservices

The application is being developed using a microservices architecture.

```text
devassist-ai/
│
├── auth-service/
├── document-service/
├── ai-service/
├── api-gateway/
├── discovery-service/
├── angular-ui/
├── docker/
├── README.md
└── .gitignore
```

### Auth Service

Responsible for:

* Registration
* Login
* JWT generation
* Authentication
* User roles

### Document Service

Responsible for:

* Document upload
* Document metadata
* File storage
* Document lifecycle
* Document download

### AI Service

Responsible for:

* PDF processing
* Text extraction
* Chunking
* Embeddings
* Vector storage
* Semantic search
* RAG
* LLM interaction

### API Gateway

Planned centralized entry point for client requests.

### Discovery Service

Planned service discovery component for the microservices architecture.

---

# Security

The application uses Spring Security and JWT authentication.

The current authentication flow is:

```text
User
 ↓
Login
 ↓
Auth Service
 ↓
JWT Token
 ↓
Authenticated API Requests
```

The document service validates JWT-based authentication before allowing protected document operations.

---

#  Current Project Status

### Completed

* [x] Spring Boot project setup
* [x] Authentication service
* [x] JWT authentication
* [x] BCrypt password hashing
* [x] Role-based authentication foundation
* [x] Document service
* [x] PostgreSQL integration
* [x] PDF upload
* [x] PDF download
* [x] Document → AI service integration
* [x] PDF text extraction
* [x] Text chunking
* [x] Embedding generation
* [x] pgvector integration
* [x] Semantic search
* [x] RAG pipeline
* [x] Ollama integration
* [x] Spring AI `ChatClient`
* [x] Similarity threshold

### Planned

* [ ] Source citations
* [ ] Improved document metadata
* [ ] Advanced retrieval strategies
* [ ] Hybrid search
* [ ] Reranking
* [ ] Conversation history
* [ ] AI memory
* [ ] Streaming responses
* [ ] Kafka-based asynchronous document processing
* [ ] Angular frontend
* [ ] API Gateway
* [ ] Service discovery
* [ ] Automated testing
* [ ] Docker Compose
* [ ] Deployment
* [ ] Monitoring and observability

---

# 🔄 Future RAG Improvements

The current RAG implementation is intentionally simple and focused on understanding the complete pipeline.

Future improvements include:

```text
Basic RAG
   ↓
Metadata-aware retrieval
   ↓
Source citations
   ↓
Better chunking
   ↓
Hybrid search
   ↓
Reranking
   ↓
Context optimization
   ↓
RAG evaluation
```

The goal is to understand not only how RAG works, but also how retrieval quality affects the final answer.

---

# 📚 Learning Outcomes

Through this project, the following concepts are being explored practically:

### Generative AI

* LLM architecture
* Tokens
* Context windows
* Prompt engineering
* System and user prompts
* Temperature
* Hallucination
* Model inference

### RAG

* Document ingestion
* Chunking
* Embeddings
* Vector databases
* Similarity search
* Top-K retrieval
* Similarity thresholds
* Context construction
* Grounded generation
* Retrieval vs generation quality

### Software Engineering

* Microservices
* REST APIs
* JWT security
* Database design
* Service-to-service communication
* Docker
* Kafka
* Testing
* API design

---

#  Project Goal

DevAssist AI is being developed as a practical demonstration of how **Java backend engineering and Generative AI can be combined to build intelligent enterprise applications**.

The long-term goal is to create an enterprise-style AI assistant capable of answering employee questions using an organization's internal knowledge base while maintaining secure document processing and scalable backend architecture.

---

##  Author

**Tushar Talekar**

Java Backend Developer | Spring Boot | Microservices | Generative AI | RAG

---
