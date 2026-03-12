# 🎯 QuizMaster - Application Spring Boot

Application de Quiz interactive en **Anglais, Mathématiques et Chimie**.

## 🚀 Démarrage rapide

### Prérequis
- Java 17+
- Maven 3.6+

### Lancer l'application

```bash
cd quiz-app
mvn spring-boot:run
```

Ouvrir dans le navigateur : **http://localhost:8080**

### Créer un JAR exécutable

```bash
mvn clean package -DskipTests
java -jar target/quiz-app-1.0.0.jar
```

---

## 📁 Structure du projet

```
quiz-app/
├── src/main/java/com/quiz/
│   ├── QuizApplication.java       # Point d'entrée
│   ├── model/
│   │   ├── Category.java          # Enum: ANGLAIS, MATHS, CHIMIE
│   │   ├── Difficulty.java        # Enum: FACILE, MOYEN, DIFFICILE
│   │   ├── Question.java          # Entité question
│   │   ├── QuizResult.java        # Entité résultat
│   │   └── QuizSession.java       # Session en cours
│   ├── repository/
│   │   ├── QuestionRepository.java
│   │   └── QuizResultRepository.java
│   ├── service/
│   │   └── QuizService.java
│   ├── controller/
│   │   ├── HomeController.java    # Routes principales
│   │   └── AdminController.java  # Panel admin
│   └── config/
│       └── DataInitializer.java  # 40+ questions préchargées
├── src/main/resources/
│   ├── templates/                # Thymeleaf HTML
│   └── application.properties
└── pom.xml
```

---

## ✨ Fonctionnalités

| Feature | Description |
|---------|------------|
| 🇬🇧 Anglais | Grammaire, vocabulaire, littérature - 3 niveaux |
| 🔢 Maths | Algèbre, géométrie, analyse - 3 niveaux |
| ⚗️ Chimie | Éléments, réactions, liaisons - 3 niveaux |
| ⏱️ Timer | 30 secondes par question |
| 🏆 Classement | Top 10 global et par catégorie |
| ⚙️ Admin | CRUD complet des questions |
| 💾 Base de données | H2 persistante (fichier local) |
| 🎓 Notes | A+, A, B, C, D, F selon le score |

---

## 🌐 URLs

| URL | Description |
|-----|------------|
| `/` | Page d'accueil |
| `/quiz/start?category=MATHS` | Démarrer un quiz |
| `/leaderboard` | Classement |
| `/admin` | Panneau d'administration |
| `/h2-console` | Console base de données H2 |

---

## 🗄️ Console H2

URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/quizdb`
- Username: `sa`
- Password: `quiz123`

---

## 📝 Ajouter des questions

Via le panneau Admin (`/admin`) ou directement dans `DataInitializer.java`.

## 🛠️ Technologies

- **Spring Boot 3.2** - Framework backend
- **Thymeleaf** - Templates HTML
- **Spring Data JPA + H2** - Base de données
- **Bootstrap 5.3** - UI/CSS
- **Lombok** - Code reduction
