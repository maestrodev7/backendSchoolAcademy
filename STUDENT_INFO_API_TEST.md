# API Student Info - Routes et Payloads de Test

## Base URL
```
http://localhost:8080/api
```

---

## 1. CRÉER LES INFORMATIONS D'UN ÉLÈVE (POST)

**Endpoint:** `POST /api/student-infos`

**Description:** 
- Génère automatiquement le `uniqueIdentifier` (format: `STU-YYYY-XXX`)
- Construit automatiquement `parentNames` et `parentContacts` à partir du parent
- Vérifie que l'élève est bien inscrit dans la classe demandée

**Payload:**
```json
{
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "parentId": "456e7890-e12b-34d5-a678-901234567890",
  "classRoomId": "789e0123-e45b-67d8-a901-234567890abc",
  "birthDate": "2010-05-15",
  "birthPlace": "Douala, Cameroun",
  "gender": "M",
  "isRepeating": false,
  "photoUrl": "https://example.com/photos/student-001.jpg"
}
```

**Champs générés automatiquement par le backend:**
- `uniqueIdentifier`: `"STU-2025-001"` (généré automatiquement)
- `parentNames`: Construit à partir de `parent.firstName` + `parent.lastName`
- `parentContacts`: Construit à partir de `parent.phoneNumber` et `parent.email`

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Informations de l'élève créées avec succès",
  "data": {
    "id": "abc12345-e67b-89d0-a123-4567890def01",
    "studentId": "123e4567-e89b-12d3-a456-426614174000",
    "studentName": "Jean DUPONT",
    "uniqueIdentifier": "STU-2025-001",
    "birthDate": "2010-05-15",
    "birthPlace": "Douala, Cameroun",
    "gender": "M",
    "isRepeating": false,
    "parentNames": "Marie DUPONT",
    "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
    "photoUrl": "https://example.com/photos/student-001.jpg"
  }
}
```

**Erreurs possibles:**
- `400 Bad Request`: Champs manquants (studentId, parentId, classRoomId)
- `404 Not Found`: Élève, parent ou classe non trouvé
- `409 Conflict`: Des informations existent déjà pour cet élève
- `400 Business Rule`: L'élève n'est pas inscrit dans cette classe

---

## 2. RÉCUPÉRER LES INFORMATIONS D'UN ÉLÈVE PAR ID

**Endpoint:** `GET /api/student-infos/{id}`

**Exemple:**
```
GET /api/student-infos/abc12345-e67b-89d0-a123-4567890def01
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Informations récupérées avec succès",
  "data": {
    "id": "abc12345-e67b-89d0-a123-4567890def01",
    "studentId": "123e4567-e89b-12d3-a456-426614174000",
    "studentName": "Jean DUPONT",
    "uniqueIdentifier": "STU-2025-001",
    "birthDate": "2010-05-15",
    "birthPlace": "Douala, Cameroun",
    "gender": "M",
    "isRepeating": false,
    "parentNames": "Marie DUPONT",
    "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
    "photoUrl": "https://example.com/photos/student-001.jpg"
  }
}
```

---

## 3. RÉCUPÉRER LES INFORMATIONS D'UN ÉLÈVE PAR STUDENT ID

**Endpoint:** `GET /api/student-infos/student/{studentId}`

**Exemple:**
```
GET /api/student-infos/student/123e4567-e89b-12d3-a456-426614174000
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Informations récupérées avec succès",
  "data": {
    "id": "abc12345-e67b-89d0-a123-4567890def01",
    "studentId": "123e4567-e89b-12d3-a456-426614174000",
    "studentName": "Jean DUPONT",
    "uniqueIdentifier": "STU-2025-001",
    "birthDate": "2010-05-15",
    "birthPlace": "Douala, Cameroun",
    "gender": "M",
    "isRepeating": false,
    "parentNames": "Marie DUPONT",
    "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
    "photoUrl": "https://example.com/photos/student-001.jpg"
  }
}
```

---

## 4. RÉCUPÉRER TOUTES LES INFORMATIONS DES ÉLÈVES D'UNE ANNÉE ACADÉMIQUE

**Endpoint:** `GET /api/student-infos/academic-year/{academicYearId}`

**Description:** Récupère toutes les informations des élèves inscrits pour une année académique donnée.

**Exemple:**
```
GET /api/student-infos/academic-year/def45678-e90b-12c3-a456-789012345abc
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Informations récupérées avec succès",
  "data": [
    {
      "id": "abc12345-e67b-89d0-a123-4567890def01",
      "studentId": "123e4567-e89b-12d3-a456-426614174000",
      "studentName": "Jean DUPONT",
      "uniqueIdentifier": "STU-2025-001",
      "birthDate": "2010-05-15",
      "birthPlace": "Douala, Cameroun",
      "gender": "M",
      "isRepeating": false,
      "parentNames": "Marie DUPONT",
      "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
      "photoUrl": "https://example.com/photos/student-001.jpg"
    },
    {
      "id": "def45678-e90b-12c3-a456-789012345abc",
      "studentId": "234e5678-e90b-12d3-a456-426614174001",
      "studentName": "Sophie MARTIN",
      "uniqueIdentifier": "STU-2025-002",
      "birthDate": "2011-03-20",
      "birthPlace": "Yaoundé, Cameroun",
      "gender": "F",
      "isRepeating": false,
      "parentNames": "Pierre MARTIN",
      "parentContacts": "Tel: +237 6YY YYY YYY, Email: martin@example.com",
      "photoUrl": "https://example.com/photos/student-002.jpg"
    }
  ]
}
```

---

## 5. RÉCUPÉRER LES INFORMATIONS DES ÉLÈVES D'UNE CLASSE POUR UNE ANNÉE ACADÉMIQUE

**Endpoint:** `GET /api/student-infos/class/{classRoomId}/academic-year/{academicYearId}`

**Description:** Récupère les informations des élèves inscrits dans une classe spécifique pour une année académique donnée. Utile pour vérifier les élèves inscrits dans une classe.

**Exemple:**
```
GET /api/student-infos/class/789e0123-e45b-67d8-a901-234567890abc/academic-year/def45678-e90b-12c3-a456-789012345abc
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Informations récupérées avec succès",
  "data": [
    {
      "id": "abc12345-e67b-89d0-a123-4567890def01",
      "studentId": "123e4567-e89b-12d3-a456-426614174000",
      "studentName": "Jean DUPONT",
      "uniqueIdentifier": "STU-2025-001",
      "birthDate": "2010-05-15",
      "birthPlace": "Douala, Cameroun",
      "gender": "M",
      "isRepeating": false,
      "parentNames": "Marie DUPONT",
      "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
      "photoUrl": "https://example.com/photos/student-001.jpg"
    }
  ]
}
```

---

## 6. METTRE À JOUR LES INFORMATIONS D'UN ÉLÈVE (PUT)

**Endpoint:** `PUT /api/student-infos/{id}`

**Description:** 
- `parentId` et `classRoomId` sont optionnels lors de la mise à jour
- Si `parentId` est fourni, les `parentNames` et `parentContacts` seront automatiquement mis à jour

**Payload:**
```json
{
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "parentId": "456e7890-e12b-34d5-a678-901234567890",
  "birthDate": "2010-05-15",
  "birthPlace": "Douala, Cameroun",
  "gender": "M",
  "isRepeating": true,
  "photoUrl": "https://example.com/photos/student-001-updated.jpg"
}
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Informations mises à jour avec succès",
  "data": {
    "id": "abc12345-e67b-89d0-a123-4567890def01",
    "studentId": "123e4567-e89b-12d3-a456-426614174000",
    "studentName": "Jean DUPONT",
    "uniqueIdentifier": "STU-2025-001",
    "birthDate": "2010-05-15",
    "birthPlace": "Douala, Cameroun",
    "gender": "M",
    "isRepeating": true,
    "parentNames": "Marie DUPONT",
    "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
    "photoUrl": "https://example.com/photos/student-001-updated.jpg"
  }
}
```

---

## NOTES IMPORTANTES

### Génération de l'identifiant unique
- Format: `STU-{ANNÉE}-{NUMÉRO}` (ex: `STU-2025-001`, `STU-2025-002`)
- Généré automatiquement par le backend
- Le numéro est incrémenté automatiquement pour chaque nouvelle création dans l'année
- Si collision (très rare), le système réessaie jusqu'à 10 fois

### Construction des informations parent
- `parentNames`: `{firstName} {lastName}` du parent
- `parentContacts`: `Tel: {phoneNumber}, Email: {email}` du parent
- Construit automatiquement à partir des données du User parent

### Vérification d'inscription
- Le système vérifie que l'élève est bien inscrit dans la classe demandée via `StudentRegistration`
- Si l'élève n'est pas inscrit, une erreur `400 Business Rule` est retournée

### Filtrage par année académique
- Les endpoints GET par année académique utilisent les `StudentRegistration` pour trouver les élèves
- Seuls les élèves ayant une inscription pour l'année académique donnée sont retournés

---

## EXEMPLES DE WORKFLOW COMPLET

### 1. Créer les informations d'un élève

**Étape 1:** S'assurer que l'élève est inscrit dans une classe
```
POST /api/student-registrations
{
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "classRoomId": "789e0123-e45b-67d8-a901-234567890abc",
  "academicYearId": "def45678-e90b-12c3-a456-789012345abc"
}
```

**Étape 2:** Créer les informations de l'élève
```
POST /api/student-infos
{
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "parentId": "456e7890-e12b-34d5-a678-901234567890",
  "classRoomId": "789e0123-e45b-67d8-a901-234567890abc",
  "birthDate": "2010-05-15",
  "birthPlace": "Douala, Cameroun",
  "gender": "M",
  "isRepeating": false
}
```

### 2. Vérifier les élèves inscrits dans une classe

```
GET /api/student-infos/class/789e0123-e45b-67d8-a901-234567890abc/academic-year/def45678-e90b-12c3-a456-789012345abc
```

Cette route permet au frontend de vérifier quels élèves sont inscrits dans une classe pour une année académique donnée, ce qui est utile avant de créer de nouvelles informations d'élève.

---

## ENDPOINTS UTILITAIRES POUR OBTENIR LES UUIDs

Pour tester, vous aurez besoin des UUIDs réels. Utilisez ces endpoints :

- `GET /api/users` - Liste des utilisateurs (élèves, parents)
- `GET /api/class-rooms` - Liste des classes
- `GET /api/academic-years` - Liste des années académiques
- `GET /api/student-registrations` - Liste des inscriptions
