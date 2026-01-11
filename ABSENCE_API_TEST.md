# API Absences - Routes et Payloads de Test

## Base URL
```
http://localhost:8080/api
```

---

## 1. ENREGISTRER LES ABSENCES D'UNE CLASSE POUR UN COURS (POST)

**Endpoint:** `POST /api/absences`

**Description:** 
- Permet à un enseignant d'enregistrer les absences d'une classe pour un cours particulier
- Crée automatiquement une absence pour chaque élève dans la liste `absentStudentIds`
- Utilise l'année académique active de l'école

**Payload:**
```json
{
  "schoolId": "123e4567-e89b-12d3-a456-426614174000",
  "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
  "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
  "date": "2025-12-19",
  "absentStudentIds": [
    "abc12345-e67b-89d0-a123-4567890def01",
    "def45678-e90b-12c3-a456-789012345abc"
  ],
  "numberOfHours": 2.0,
  "scheduleId": "ghi78901-e23b-45c6-a789-012345678def",
  "notes": "Cours de mathématiques - Absences non justifiées"
}
```

**Champs:**
- `schoolId` (requis): ID de l'école
- `classRoomId` (requis): ID de la classe
- `subjectId` (requis): ID de la matière/cours
- `date` (requis): Date du cours (format: YYYY-MM-DD)
- `absentStudentIds` (requis, non vide): Liste des IDs des élèves absents
- `numberOfHours` (requis, > 0): Nombre d'heures d'absence
- `scheduleId` (optionnel): ID du créneau d'emploi du temps
- `notes` (optionnel): Notes additionnelles

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences enregistrées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "schoolId": "123e4567-e89b-12d3-a456-426614174000",
      "schoolName": "École Primaire Centrale",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "academicYearId": "mno34567-e89b-01c2-a345-678901234fgh",
      "academicYearLabel": "2025-09-01 - 2026-06-30",
      "scheduleId": "ghi78901-e23b-45c6-a789-012345678def",
      "date": "2025-12-19",
      "numberOfHours": 2.0,
      "notes": "Cours de mathématiques - Absences non justifiées"
    },
    {
      "id": "pqr45678-e90b-12c3-a456-789012345hij",
      "studentId": "def45678-e90b-12c3-a456-789012345abc",
      "studentName": "Sophie MARTIN",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "schoolId": "123e4567-e89b-12d3-a456-426614174000",
      "schoolName": "École Primaire Centrale",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "academicYearId": "mno34567-e89b-01c2-a345-678901234fgh",
      "academicYearLabel": "2025-09-01 - 2026-06-30",
      "scheduleId": "ghi78901-e23b-45c6-a789-012345678def",
      "date": "2025-12-19",
      "numberOfHours": 2.0,
      "notes": "Cours de mathématiques - Absences non justifiées"
    }
  ]
}
```

**Erreurs possibles:**
- `400 Bad Request`: Champs manquants ou invalides
- `404 Not Found`: École, classe, matière ou élève non trouvé
- `404 Not Found`: Aucune année académique active pour cette école

---

## 2. RÉCUPÉRER UNE ABSENCE PAR ID

**Endpoint:** `GET /api/absences/{id}`

**Exemple:**
```
GET /api/absences/jkl01234-e56b-78c9-a012-345678901efg
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absence récupérée avec succès",
  "data": {
    "id": "jkl01234-e56b-78c9-a012-345678901efg",
    "studentId": "abc12345-e67b-89d0-a123-4567890def01",
    "studentName": "Jean DUPONT",
    "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
    "classRoomLabel": "6ème A",
    "schoolId": "123e4567-e89b-12d3-a456-426614174000",
    "schoolName": "École Primaire Centrale",
    "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
    "subjectName": "Mathématiques",
    "subjectCode": "MATH",
    "academicYearId": "mno34567-e89b-01c2-a345-678901234fgh",
    "academicYearLabel": "2025-09-01 - 2026-06-30",
    "scheduleId": "ghi78901-e23b-45c6-a789-012345678def",
    "date": "2025-12-19",
    "numberOfHours": 2.0,
    "notes": "Cours de mathématiques - Absences non justifiées"
  }
}
```

---

## 3. RÉCUPÉRER TOUTES LES ABSENCES D'UN ÉLÈVE

**Endpoint:** `GET /api/absences/student/{studentId}`

**Exemple:**
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "schoolId": "123e4567-e89b-12d3-a456-426614174000",
      "schoolName": "École Primaire Centrale",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "academicYearId": "mno34567-e89b-01c2-a345-678901234fgh",
      "academicYearLabel": "2025-09-01 - 2026-06-30",
      "scheduleId": "ghi78901-e23b-45c6-a789-012345678def",
      "date": "2025-12-19",
      "numberOfHours": 2.0,
      "notes": "Cours de mathématiques"
    }
  ]
}
```

---

## 4. RÉCUPÉRER LES ABSENCES D'UN ÉLÈVE POUR UNE PÉRIODE

**Endpoint:** `GET /api/absences/student/{studentId}/date-range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

**Exemple:**
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01/date-range?startDate=2025-12-01&endDate=2025-12-31
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0,
      "notes": "Cours de mathématiques"
    }
  ]
}
```

---

## 5. RÉCUPÉRER LE TOTAL D'HEURES D'ABSENCE D'UN ÉLÈVE POUR UNE PÉRIODE

**Endpoint:** `GET /api/absences/student/{studentId}/total-hours?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

**Exemple:**
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01/total-hours?startDate=2025-12-01&endDate=2025-12-31
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Total d'heures d'absence récupéré avec succès",
  "data": 6.0
}
```

---

## 6. RÉCUPÉRER LES ABSENCES D'UNE CLASSE POUR UNE DATE

**Endpoint:** `GET /api/absences/class/{classRoomId}/date/{date}`

**Exemple:**
```
GET /api/absences/class/456e7890-e12b-34d5-a678-901234567890/date/2025-12-19
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0
    }
  ]
}
```

---

## 7. RÉCUPÉRER LES ABSENCES D'UNE CLASSE POUR UNE PÉRIODE

**Endpoint:** `GET /api/absences/class/{classRoomId}/date-range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

**Exemple:**
```
GET /api/absences/class/456e7890-e12b-34d5-a678-901234567890/date-range?startDate=2025-12-01&endDate=2025-12-31
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0
    }
  ]
}
```

---

## 8. RÉCUPÉRER LES ABSENCES D'UN ÉLÈVE DANS UNE CLASSE POUR UNE PÉRIODE

**Endpoint:** `GET /api/absences/student/{studentId}/class/{classRoomId}/date-range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

**Exemple:**
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01/class/456e7890-e12b-34d5-a678-901234567890/date-range?startDate=2025-12-01&endDate=2025-12-31
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0
    }
  ]
}
```

---

## 9. RÉCUPÉRER LES ABSENCES D'UNE CLASSE POUR UNE MATIÈRE ET UNE DATE

**Endpoint:** `GET /api/absences/class/{classRoomId}/subject/{subjectId}/date/{date}`

**Exemple:**
```
GET /api/absences/class/456e7890-e12b-34d5-a678-901234567890/subject/789e0123-e45b-67d8-a901-234567890abc/date/2025-12-19
```

**Description:** Utile pour voir qui était absent lors d'un cours de mathématiques par exemple.

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0
    }
  ]
}
```

---

## 10. RÉCUPÉRER LES ABSENCES D'UN ÉLÈVE POUR UNE ANNÉE ACADÉMIQUE

**Endpoint:** `GET /api/absences/student/{studentId}/academic-year/{academicYearId}`

**Exemple:**
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01/academic-year/mno34567-e89b-01c2-a345-678901234fgh
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0
    }
  ]
}
```

---

## 11. RÉCUPÉRER LES ABSENCES D'UNE CLASSE POUR UNE ANNÉE ACADÉMIQUE

**Endpoint:** `GET /api/absences/class/{classRoomId}/academic-year/{academicYearId}`

**Exemple:**
```
GET /api/absences/class/456e7890-e12b-34d5-a678-901234567890/academic-year/mno34567-e89b-01c2-a345-678901234fgh
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absences récupérées avec succès",
  "data": [
    {
      "id": "jkl01234-e56b-78c9-a012-345678901efg",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
      "subjectName": "Mathématiques",
      "subjectCode": "MATH",
      "date": "2025-12-19",
      "numberOfHours": 2.0
    }
  ]
}
```

---

## 12. SUPPRIMER UNE ABSENCE

**Endpoint:** `DELETE /api/absences/{id}`

**Exemple:**
```
DELETE /api/absences/jkl01234-e56b-78c9-a012-345678901efg
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Absence supprimée avec succès",
  "data": null
}
```

---

## EXEMPLES DE WORKFLOW COMPLET

### Scénario 1: Enregistrer les absences après un cours de mathématiques

**Étape 1:** L'enseignant enregistre les absences
```
POST /api/absences
{
  "schoolId": "123e4567-e89b-12d3-a456-426614174000",
  "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
  "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
  "date": "2025-12-19",
  "absentStudentIds": [
    "abc12345-e67b-89d0-a123-4567890def01",
    "def45678-e90b-12c3-a456-789012345abc"
  ],
  "numberOfHours": 2.0,
  "scheduleId": "ghi78901-e23b-45c6-a789-012345678def",
  "notes": "Cours de mathématiques - Absences non justifiées"
}
```

**Étape 2:** Vérifier les absences enregistrées pour ce cours
```
GET /api/absences/class/456e7890-e12b-34d5-a678-901234567890/subject/789e0123-e45b-67d8-a901-234567890abc/date/2025-12-19
```

### Scénario 2: Consulter les absences d'un élève pour un trimestre

**Étape 1:** Récupérer toutes les absences d'un élève pour une période
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01/date-range?startDate=2025-12-01&endDate=2025-12-31
```

**Étape 2:** Récupérer le total d'heures d'absence
```
GET /api/absences/student/abc12345-e67b-89d0-a123-4567890def01/total-hours?startDate=2025-12-01&endDate=2025-12-31
```

### Scénario 3: Consulter les absences d'une classe pour une période donnée

```
GET /api/absences/class/456e7890-e12b-34d5-a678-901234567890/date-range?startDate=2025-12-01&endDate=2025-12-31
```

---

## NOTES IMPORTANTES

### Format des dates
- Format: `YYYY-MM-DD` (ISO 8601)
- Exemple: `2025-12-19`

### Nombre d'heures
- Type: `Double`
- Doit être positif (> 0)
- Exemple: `2.0`, `1.5`, `3.0`

### Liste des élèves absents
- Type: `List<UUID>`
- Ne peut pas être vide
- Chaque ID doit correspondre à un élève existant

### Année académique
- L'année académique est automatiquement récupérée depuis l'école (année active)
- Si aucune année active n'est trouvée, une erreur 404 est retournée

### Emploi du temps (scheduleId)
- Optionnel
- Permet de lier l'absence à un créneau spécifique de l'emploi du temps
- Utile pour la traçabilité et les rapports

---

## ENDPOINTS UTILITAIRES POUR OBTENIR LES UUIDs

Pour tester, vous aurez besoin des UUIDs réels. Utilisez ces endpoints :

- `GET /api/schools` - Liste des écoles
- `GET /api/class-rooms` - Liste des classes
- `GET /api/subjects` - Liste des matières
- `GET /api/users` - Liste des utilisateurs (élèves)
- `GET /api/academic-years` - Liste des années académiques
- `GET /api/schedules` - Liste des créneaux d'emploi du temps

---

## RÉSUMÉ DES ENDPOINTS

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/absences` | Enregistrer les absences d'une classe pour un cours |
| GET | `/api/absences/{id}` | Récupérer une absence par ID |
| GET | `/api/absences/student/{studentId}` | Toutes les absences d'un élève |
| GET | `/api/absences/student/{studentId}/date-range` | Absences d'un élève pour une période |
| GET | `/api/absences/student/{studentId}/total-hours` | Total d'heures d'absence d'un élève |
| GET | `/api/absences/class/{classRoomId}/date/{date}` | Absences d'une classe pour une date |
| GET | `/api/absences/class/{classRoomId}/date-range` | Absences d'une classe pour une période |
| GET | `/api/absences/student/{studentId}/class/{classRoomId}/date-range` | Absences d'un élève dans une classe pour une période |
| GET | `/api/absences/class/{classRoomId}/subject/{subjectId}/date/{date}` | Absences d'une classe pour une matière et une date |
| GET | `/api/absences/student/{studentId}/academic-year/{academicYearId}` | Absences d'un élève pour une année académique |
| GET | `/api/absences/class/{classRoomId}/academic-year/{academicYearId}` | Absences d'une classe pour une année académique |
| DELETE | `/api/absences/{id}` | Supprimer une absence |
