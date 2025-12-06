Patient Management – Microservices Architecture

Spring Boot • Spring Security • JWT • API Gateway • Kafka • gRPC • PostgreSQL • Docker • Microservices

Bu proje, hasta yönetimi için oluşturulmuş çok servisli (microservice) bir mimari örneğidir. Amaç; farklı iletişim protokollerini (HTTP, gRPC, Kafka), authentication flow’unu, container orchestration’ı ve service-to-service communication’ı tek bir yapıda göstermek.

Not: Bu proje öğrenme/test amaçlıdır.
API secret’lar, DB şifreleri, Docker config’leri bilinçli olarak repoda açık bırakılmıştır.

📌 Mimarinin Genel Akışı
🔐 Auth Service

Spring Security, JWT kullanır.

Kullanıcı login olur → JWT üretir → API Gateway’e döner.

Diğer microservisler gelen token’ı Auth Service üzerinden doğrular.

🏥 Patient Service

Spring Data JPA + PostgreSQL

CRUD işlemleri

Kafka’ya event publish eder (ör. patient_created).

Diğer microservislerle hem REST (HTTP) hem gRPC üzerinden konuşur.

📊 Analytics Service

Kafka Consumer

Patient Service’ten gelen event’leri dinler ve analiz eder.

API Gateway üzerinden public REST endpoint sunar.

💵 Billing Service

Patient Service ile gRPC üzerinden haberleşir.

.proto dosyası reponun içindedir.

Protobuf kullanarak hafif ve hızlı RPC sağlar.

🛡 API Gateway

Tüm client request’lerinin giriş noktası

JWT’yi doğrular

İlgili microservice’e route eder

Internal service’lere doğrudan dışarıdan erişim yoktur (only via gateway)

🐳 Docker Network

Tüm servisler tek Docker ağı içinde birbirine bağlanır.
PostgreSQL, Kafka, Zookeeper dahil docker-compose dosyasında tanımlıdır.

📂 Proje Yapısı
patient-management/
│
├── api-gateway/
├── auth-service/
├── patient-service/
├── analytics-service/
├── billing-service/
│   └── proto/
│       └── billing_service.proto
│
├── docker-compose.yml
└── README.md

🚀 Kullanılan Teknolojiler
Backend

Java 17

Spring Boot 3

Spring Web

Spring Security (JWT)

Spring Data JPA

Spring Cloud Gateway

Lombok

Messaging

Apache Kafka

Kafka Producer / Consumer

RPC

gRPC

Protocol Buffers (protobuf)

Database

PostgreSQL

Containerization

Docker

Docker Compose

🔄 Microservice Communication
1️⃣ Client → API Gateway → Auth Service

Kullanıcı login olur:

POST localhost:4000/auth/login


Gateway → Auth Service → JWT üretir → geri döner.

2️⃣ Authorized Request → Patient Service
GET localhost:4000/api/patients


Gateway token’ı doğrular → Patient Service’e yönlendirir.

3️⃣ Patient Service → Kafka

Yeni hasta kaydı:

patient_created event → Kafka topic

4️⃣ Analytics Service → Kafka Consumer

Event'i dinler → Analiz eder → kendi DB’sine yazar.

5️⃣ Patient Service → Billing Service (gRPC)
PatientService → BillingService (grpc request)

🐳 Docker ile Çalıştırma
1. Build + Run
   docker compose up --build

2. Tüm container’ları silmek istersen:
   docker compose down -v

3. Servislerin healthcheck durumuna bak:
   docker ps

🧪 Testing

Projede aşağıdakileri kapsayan test örnekleri bulunur:

✔ Unit Tests

Service layer

Token generation

Kafka producer mock testleri

✔ Integration Tests

In-memory DB (H2)

MockMvc ile controller testleri

Auth → Gateway → Patient akışı (mock)

✔ gRPC Tests

Proto üzerinden oluşturulmuş stub’larla service-to-service test

✔ Kafka Tests

Embedded Kafka ile event flow testi

📝 Örnek API’ler
Login
POST /auth/login
{
"username": "test",
"password": "test123"
}

Hasta Listeleme
GET /api/patients
Authorization: Bearer <jwt_token>

Analytics
GET /api/analytics

🛠 Geliştirici Notları

Her microservice kendi Dockerfile’ı ile paketlenir.

Tüm servisler tek docker network üzerinde otomatik iletişim kurar.

.env dosyası yerine environment variables direkt compose içinde kullanıldı (test amaçlı).

JWT secret, database password gibi bilgiler saklanmak için değildir; açıkça docs amaçlıdır.

📄 Lisans

Bu proje eğitim ve demo amaçlıdır. Ticari kullanım amaçlı değildir.
