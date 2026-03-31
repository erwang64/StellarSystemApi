# Explication des DTOs et du Contrôleur User

Une refonte de la gestion des utilisateurs (User) a été apportée pour respecter les bonnes pratiques des API REST sous Spring Boot. L'objectif principal est de ne plus exposer directement l'entité de la base de données (notamment le mot de passe hashé) et de standardiser la gestion des erreurs.

## 1. Création des DTOs (Data Transfer Objects)

L'utilisation de DTOs permet de séparer ce qui circule sur le réseau de ce qui est stocké en base de données. 

* **`UserRequest`** : C'est l'objet attendu lors de la création ou la mise à jour d'un utilisateur (requêtes `POST` et `PUT`).
  * Il hérite de `AbstractRequest` et initialise son `ResourceType` à `USER` (ce qui permet au gestionnaire d'erreurs Global `ErrorHandler` de savoir quel type de ressource a causé l'erreur).
  * Il contient le *login*, le *password* en clair (pour la création) et le *profile*, avec les annotations de validation nécessaires (`@NotBlank`, `@Size`).

* **`UserResponse`** : C'est l'objet renvoyé par l'API au client. L'avantage majeur est qu'**il ne contient pas l'attribut `password`**. Ainsi, les clients récupèrent l'ID, le login et le profil, sans risquer de divulguer des empreintes (hash) de sécurité.

* **`UserMapper`** : Une classe utilitaire simple qui transforme le `UserRequest` (ce qui entre) en `User` (entité JPA pour la base de données), et le `User` sortant en `UserResponse`.

## 2. L'Enum `ResourceType`

L'énumération `ResourceType` située dans le gestionnaire d'erreurs a été enrichie :
```java
public enum ResourceType {
	STELLAR_SYSTEM,
	STAR,
	PLANET,
	MOON,
	USER    // <-- Nouvelle valeur ajoutée
}
```
Cela permet de lever des exceptions du type `EndPointException` standardisées par l'architecture existante avec `ResourceType.USER`.

## 3. Le Contrôleur (`UserController`) et le endpoint de log

Le `UserController` a été refactoré pour utiliser ces DTOs et gérer les erreurs rigoureusement :

1. **Méthodes CRUD classiques** : 
   * `@PostMapping` (création) : Attend un `UserRequest` et retourne un `UserResponse`. Gère les exceptions métiers avec `EndPointException`.
   * `@GetMapping("/all")` : Utilise désormais `UserMapper.toDtoList(users)` pour ne lister que des `UserResponse` en masquant tous les mots de passe.
   * `@GetMapping("/{id}")` et `@GetMapping("/login/{login}")` : Lèvent explicitement une `EndPointException` de type NOT_FOUND (`404`) si l'utilisateur n'est pas présent, au lieu d'une simple erreur neutre.
   * `@PutMapping("/{id}")` : Met à jour dynamiquement et partiellement l'utilisateur via le `UserRequest` reçu et mappe la réponse sans exposer le mot de passe.
   * `@DeleteMapping("/{id}")`.

2. **Le Endpoint `/login`** :
   ```java
   @PostMapping("/login")
   public ResponseEntity<Void> login() {
       // TODO: Implémenter la logique de login ultérieurement (ex: JWT, sessions...)
       return ResponseEntity.ok().build();
   }
   ```
   Ce endpoint a été ajouté avec la méthode HTTP `POST` car un login nécessite généralement l'envoi de credentials cachés dans le corps de la requête. Il ne prend pour le moment aucun corps et retourne un simple code 200 (OK) mais vide (`Void`).

### Bilan

Votre entité `User` est maintenant sécurisée, le code est homogène avec les autres contrôleurs (comme `StarController`), et le modèle d'erreur `EndPointException` est activé de bout en bout pour les Utilisateurs.
