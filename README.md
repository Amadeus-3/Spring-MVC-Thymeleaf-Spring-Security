# Gestion des Produits - Spring MVC + Thymeleaf + Spring Security

## 📋 Description

Application web de gestion de produits développée avec Spring Boot, intégrant Spring MVC pour la couche présentation, Thymeleaf comme moteur de templates, et Spring Security pour la gestion de l'authentification et des autorisations.

## 🚀 Technologies Utilisées

- **Spring Boot 3.3.4** - Framework principal
- **Spring MVC** - Architecture web MVC
- **Spring Security** - Sécurité et authentification
- **Spring Data JPA** - Couche de persistance
- **Thymeleaf** - Moteur de templates
- **Bootstrap 5.3.2** - Framework CSS
- **H2 Database** - Base de données en mémoire (par défaut)
- **MySQL** - Base de données de production (optionnel)
- **Lombok** - Réduction du code boilerplate
- **Maven** - Gestion des dépendances

## 📂 Structure du Projet

```
src/
├── main/
│   ├── java/net/omar/
│   │   ├── Main.java                    # Classe principale Spring Boot
│   │   ├── config/
│   │   │   ├── SecurityConfig.java      # Configuration de Spring Security
│   │   │   └── DataInitializer.java     # Initialisation des données
│   │   ├── controller/
│   │   │   ├── HomeController.java      # Contrôleur principal
│   │   │   └── ProductController.java   # Contrôleur des produits
│   │   ├── entity/
│   │   │   └── Product.java             # Entité produit
│   │   └── repository/
│   │       └── ProductRepository.java   # Repository JPA
│   └── resources/
│       ├── templates/                    # Templates Thymeleaf
│       │   ├── layout.html              # Layout principal
│       │   ├── login.html               # Page de connexion
│       │   ├── dashboard.html           # Tableau de bord
│       │   ├── profile.html             # Profil utilisateur
│       │   ├── admin.html               # Panneau d'administration
│       │   └── products/                # Templates produits
│       └── application.properties        # Configuration principale
```

## 🔧 Installation et Configuration

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- MySQL (optionnel)

### Installation

1. **Cloner le repository**
   ```bash
   git clone https://github.com/[votre-username]/spring-mvc-thymeleaf-security.git
   cd spring-mvc-thymeleaf-security
   ```

2. **Compiler le projet**
   ```bash
   mvn clean install
   ```

3. **Lancer l'application**
   ```bash
   mvn spring-boot:run
   ```

### Configuration

#### Base de données H2 (par défaut)
L'application utilise H2 en mémoire par défaut. Aucune configuration n'est nécessaire.

#### Base de données MySQL (optionnel)
Pour utiliser MySQL, modifier `application.properties` :
```properties
spring.profiles.active=mysql
```

Puis configurer `application-mysql.properties` avec vos paramètres :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/votre_base
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

## 👤 Utilisateurs par Défaut

L'application crée automatiquement deux utilisateurs :

| Utilisateur | Mot de passe | Rôles |
|-------------|--------------|-------|
| user        | password     | USER  |
| admin       | admin123     | USER, ADMIN |

## 🌐 Endpoints Principaux

### Public
- `/` - Redirection vers la liste des produits
- `/login` - Page de connexion
- `/products` - Liste des produits
- `/products/search` - Recherche de produits

### Authentifié
- `/profile` - Profil de l'utilisateur connecté
- `/dashboard` - Tableau de bord
- `/products/new` - Créer un nouveau produit
- `/products/{id}` - Voir un produit
- `/products/{id}/edit` - Modifier un produit
- `/products/{id}/delete` - Supprimer un produit

### Administrateur uniquement
- `/admin` - Panneau d'administration
- `/h2-console` - Console H2 (développement)

## 🛠️ Fonctionnalités

### Gestion des Produits
- ✅ Liste des produits avec pagination
- ✅ Recherche de produits
- ✅ Création de nouveaux produits
- ✅ Modification des produits existants
- ✅ Suppression de produits
- ✅ Visualisation détaillée

### Sécurité
- ✅ Authentification par formulaire
- ✅ Gestion des rôles (USER, ADMIN)
- ✅ Protection CSRF
- ✅ Sessions sécurisées
- ✅ Déconnexion

### Interface Utilisateur
- ✅ Design responsive avec Bootstrap 5
- ✅ Navigation intuitive
- ✅ Messages d'alerte (succès, erreur, avertissement)
- ✅ Icônes Bootstrap Icons
- ✅ Layout réutilisable avec Thymeleaf Layout Dialect

## 🧪 Tests

Exécuter les tests unitaires :
```bash
mvn test
```

## 📝 Développement

### Structure des Templates Thymeleaf

Les templates utilisent le Thymeleaf Layout Dialect pour une structure modulaire :
- `layout.html` : Template principal avec navigation et footer
- Les pages étendent ce layout avec `layout:decorate="~{layout}"`
- Fragments réutilisables dans le dossier `fragments/`

### Ajout de Nouvelles Fonctionnalités

1. **Nouveau Contrôleur** : Créer dans `controller/`
2. **Nouvelle Entité** : Créer dans `entity/` avec annotations JPA
3. **Nouveau Repository** : Créer interface dans `repository/` étendant `JpaRepository`
4. **Nouveau Template** : Créer dans `templates/` en étendant le layout

### Configuration de Sécurité

La sécurité est gérée dans `SecurityConfig.java`. Pour modifier les permissions :
```java
.requestMatchers("/nouvelle-route").hasRole("ROLE_NAME")
```

## 🚢 Déploiement

### Packaging
```bash
mvn clean package
```

### Exécution du JAR
```bash
java -jar target/S_MVC_Thymeleaf_SS-1.0-SNAPSHOT.jar
```

### Profils Spring
- `default` : H2 en mémoire
- `mysql` : Base MySQL
- `test` : Configuration de test

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👥 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📧 Contact

Omar - [votre-email@example.com]

Lien du projet : [https://github.com/[votre-username]/spring-mvc-thymeleaf-security]