# Documentation des Requêtes Postman - StellarSystemApi

## 🌟 URL de Base
```
http://localhost:8080/api
```

## 📌 Endpoints Disponibles

### 1️⃣ ÉTOILES (Stars)

#### Créer une étoile (POST)
- **URL**: `POST http://localhost:8080/api/star`
- **Body**: Utiliser le contenu de `01_Star_Create.json`
- **Header**: `Content-Type: application/json`
- **Réponse**: 201 Created

#### Récupérer toutes les étoiles (GET)
- **URL**: `GET http://localhost:8080/api/star/all`
- **Réponse**: Liste de toutes les étoiles

#### Récupérer une étoile par ID (GET)
- **URL**: `GET http://localhost:8080/api/star/{id}`
- **Exemple**: `GET http://localhost:8080/api/star/1`

#### Mettre à jour une étoile entièrement (PUT)
- **URL**: `PUT http://localhost:8080/api/star/{id}`
- **Body**: Utiliser le contenu de `01_Star_Create.json`
- **Réponse**: 200 OK

#### Mettre à jour partiellement une étoile (PATCH)
- **URL**: `PATCH http://localhost:8080/api/star/{id}`
- **Body**: Seulement les champs à mettre à jour (ex: `{"temperature": 6000}`)
- **Réponse**: 200 OK

---

### 2️⃣ PLANÈTES (Planets)

#### Créer une planète (POST)
- **URL**: `POST http://localhost:8080/api/planet`
- **Body**: Utiliser le contenu de `03_Planet_Create.json`
- **Header**: `Content-Type: application/json`
- **Réponse**: 201 Created

#### Récupérer toutes les planètes (GET)
- **URL**: `GET http://localhost:8080/api/planet/all`
- **Réponse**: Liste de toutes les planètes

#### Récupérer une planète par ID (GET)
- **URL**: `GET http://localhost:8080/api/planet/{id}`
- **Exemple**: `GET http://localhost:8080/api/planet/1`

#### Mettre à jour une planète entièrement (PUT)
- **URL**: `PUT http://localhost:8080/api/planet/{id}`
- **Body**: Utiliser le contenu de `03_Planet_Create.json`
- **Réponse**: 200 OK

#### Mettre à jour partiellement une planète (PATCH)
- **URL**: `PATCH http://localhost:8080/api/planet/{id}`
- **Body**: Seulement les champs à mettre à jour
- **Réponse**: 200 OK

---

### 3️⃣ LUNES (Moons)

#### Créer une lune (POST)
- **URL**: `POST http://localhost:8080/api/moon`
- **Body**: Utiliser le contenu de `05_Moon_Create.json`
- **Header**: `Content-Type: application/json`
- **Réponse**: 201 Created

---

### 4️⃣ SYSTÈMES STELLAIRES (StellarSystem)

#### Créer un système stellaire (POST)
- **URL**: `POST http://localhost:8080/api/StellarSystem`
- **Body**: Utiliser le contenu de `07_StellarSystem_Create.json`
- **Header**: `Content-Type: application/json`
- **Réponse**: 201 Created

#### Récupérer tous les systèmes (GET)
- **URL**: `GET http://localhost:8080/api/StellarSystem/all`
- **Réponse**: Liste de tous les systèmes

#### Récupérer un système par ID (GET)
- **URL**: `GET http://localhost:8080/api/StellarSystem/{id}`
- **Exemple**: `GET http://localhost:8080/api/StellarSystem/1`

---

## 📋 Types d'Étoiles (StarType) - Valeurs Acceptées

```json
NAINE_BRUNE      // Naine brune
NAINE_ROUGE      // Naine rouge
NAINE_JAUNE      // Naine jaune (ex: Soleil)
GEANTE_ROUGE     // Géante rouge
GEANTE_BLEUE     // Géante bleue
SUPERGEANTE_ROUGE // Supergéante rouge
```

---

## ✅ Validation des Données

### Étoiles (Star)
- `name`: Texte obligatoire, minimum 2 caractères
- `mass`: Nombre obligatoire (en masses solaires)
- `starType`: Énumération obligatoire (voir types ci-dessus)
- `temperature`: Nombre obligatoire (en Kelvin)

### Planètes (Planet)
- `name`: Texte obligatoire, minimum 2 caractères
- `mass`: Nombre obligatoire (en masses terrestres)
- `telluric`: Booléen obligatoire (rocheuse ou gazeuse)
- `habitable`: Booléen obligatoire
- `atmosphereComposition`: Texte optionnel (description de l'atmosphère)
- `orbitingRadius`: Nombre obligatoire (en millions de km)
- `revolutionTime`: Nombre obligatoire (en jours terrestres)

### Lunes (Moon)
- `name`: Texte obligatoire
- `mass`: Nombre obligatoire (en masses terrestres)
- `planetId`: Entier obligatoire (ID de la planète orbitante)
- `orbitingRadius`: Nombre obligatoire (en millions de km)
- `revolutionTime`: Nombre obligatoire (en jours)

---

## 🔧 Étapes pour Importer dans Postman

1. **Créer une nouvelle Collection**
   - Allez dans "Collections" → "Create a collection"
   - Nommez-la "StellarSystemApi"

2. **Importer les données**
   - Créez des onglets pour chaque type de requête
   - Copiez les données JSON du fichier correspondant dans le body
   - Définissez les headers appropriés

3. **Test d'une séquence complète**
   ```
   1. POST /api/star          (créer une étoile) → Noter l'ID
   2. POST /api/planet        (créer une planète)
   3. POST /api/moon          (créer une lune)
   4. GET /api/star/all       (vérifier l'étoile)
   5. GET /api/planet/all     (vérifier la planète)
   ```

---

## 💡 Exemples de Tests

### Test 1: Créer le système solaire
1. Créer l'étoile "Soleil" (ID obtenu: 1)
2. Créer la planète "Terre" avec `orbitingRadius: 149.6`
3. Créer la lune "Lune"

### Test 2: Mettre à jour une planète
```json
PUT /api/planet/1
{
  "name": "Super-Terre",
  "mass": 10.0,
  "telluric": true,
  "habitable": true,
  "atmosphereComposition": "N2 75%, O2 18%, Ar 1%, autres 6%",
  "orbitingRadius": 140.0,
  "revolutionTime": 350.0
}
```

### Test 3: Mise à jour partielle
```json
PATCH /api/star/1
{
  "temperature": 6000
}
```

---

## 🔗 Ressources Utiles

- **Fichiers d'exemples**: Consultez les fichiers numérotés `02_`, `04_`, `06_` pour des exemples complets
- **Unités**: Les distances sont en millions de km, les masses en masses terrestres/solaires
- **Validation**: L'API valide les données et retourne des codes d'erreur appropriés
