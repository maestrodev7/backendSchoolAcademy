# Payloads Postman pour le système de bulletins scolaires

## Base URL
```
http://localhost:8080/api
```

---

## 1. CRÉER LES INFORMATIONS D'UN ÉLÈVE (StudentInfo)

**Endpoint:** `POST /api/student-infos`

**Payload:**
```json
{
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "uniqueIdentifier": "STU-2024-001",
  "birthDate": "2010-05-15",
  "birthPlace": "Douala, Cameroun",
  "gender": "M",
  "isRepeating": false,
  "parentNames": "Jean DUPONT, Marie DUPONT",
  "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
  "photoUrl": "https://example.com/photos/student-001.jpg"
}
```

**Récupérer les infos d'un élève:**
```
GET /api/student-infos/student/{studentId}
```

---

## 2. CRÉER UNE COMPÉTENCE POUR UNE MATIÈRE

**Endpoint:** `POST /api/competences`

**Payload:**
```json
{
  "subjectId": "456e7890-e12b-34d5-a678-901234567890",
  "description": "Use appropriate language resources to listen, speak, read and write about national integration; diversity acceptance.",
  "orderNumber": 1
}
```

**Récupérer les compétences d'une matière:**
```
GET /api/competences/subject/{subjectId}
```

---

## 3. ENREGISTRER UNE NOTE (Grade)

**Endpoint:** `POST /api/grades`

**Payload pour une note trimestrielle (sans séquence):**
```json
{
  "competenceId": "789e0123-e45b-67d8-a901-234567890abc",
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "termId": "abc12345-e67b-89d0-a123-4567890def01",
  "noteN20": 15.5,
  "noteM20": 16.0,
  "coefficient": 3.0,
  "mXCoef": 48.0,
  "cote": 16.0,
  "minScore": 8.0,
  "maxScore": 19.5,
  "appreciation": "Bon travail, continuez ainsi.",
  "teacherId": "def45678-e90b-12c3-a456-789012345abc"
}
```

**Payload pour une note de séquence:**
```json
{
  "competenceId": "789e0123-e45b-67d8-a901-234567890abc",
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "termId": "abc12345-e67b-89d0-a123-4567890def01",
  "sequenceId": "seq12345-e67b-89d0-a123-4567890def02",
  "noteN20": 14.0,
  "noteM20": 15.0,
  "coefficient": 3.0,
  "mXCoef": 45.0,
  "cote": 15.0,
  "minScore": 7.5,
  "maxScore": 18.0,
  "appreciation": "Bien, mais peut mieux faire.",
  "teacherId": "def45678-e90b-12c3-a456-789012345abc"
}
```

**Récupérer les notes d'un élève pour un trimestre:**
```
GET /api/grades/student/{studentId}/term/{termId}
```

**Récupérer les notes d'un élève pour une séquence:**
```
GET /api/grades/student/{studentId}/sequence/{sequenceId}
```

**Récupérer TOUTES les notes d'un élève pour une année académique:**
```
GET /api/grades/student/{studentId}/academic-year/{academicYearId}
```

---

## 4. ENREGISTRER UN RECORD DISCIPLINAIRE

**Endpoint:** `POST /api/discipline-records`

**Payload:**
```json
{
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "termId": "abc12345-e67b-89d0-a123-4567890def01",
  "classRoomId": "class1234-e56b-78c9-a012-34567890def0",
  "unjustifiedAbsencesHours": 5,
  "justifiedAbsencesHours": 2,
  "lateCount": 3,
  "detentionHours": 2,
  "conductWarning": false,
  "conductBlame": false,
  "exclusionDays": 0,
  "permanentExclusion": false
}
```

**Récupérer le record disciplinaire d'un élève pour un trimestre:**
```
GET /api/discipline-records/student/{studentId}/term/{termId}
```

**Récupérer TOUS les records disciplinaires d'un élève pour une année académique:**
```
GET /api/discipline-records/student/{studentId}/academic-year/{academicYearId}
```

**Récupérer TOUS les records disciplinaires d'une année académique:**
```
GET /api/discipline-records/academic-year/{academicYearId}
```

---

## 5. EXEMPLES COMPLETS POUR UN BULLETIN

### Créer plusieurs notes pour un élève (exemple ANGLAIS - 2 compétences)

**Note Compétence 1 - ANGLAIS:**
```json
{
  "competenceId": "comp-ang-001",
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "termId": "abc12345-e67b-89d0-a123-4567890def01",
  "noteN20": 15.0,
  "noteM20": 16.0,
  "coefficient": 3.0,
  "mXCoef": 48.0,
  "cote": 16.0,
  "appreciation": "Excellent travail",
  "teacherId": "def45678-e90b-12c3-a456-789012345abc"
}
```

**Note Compétence 2 - ANGLAIS:**
```json
{
  "competenceId": "comp-ang-002",
  "studentId": "123e4567-e89b-12d3-a456-426614174000",
  "termId": "abc12345-e67b-89d0-a123-4567890def01",
  "noteN20": 14.5,
  "noteM20": 15.5,
  "coefficient": 3.0,
  "mXCoef": 46.5,
  "cote": 15.5,
  "appreciation": "Bon niveau",
  "teacherId": "def45678-e90b-12c3-a456-789012345abc"
}
```

---

## 6. ENDPOINTS GET PAR ANNÉE ACADÉMIQUE

### Notes
- **Toutes les notes d'un élève pour une année académique:**
  ```
  GET /api/grades/student/{studentId}/academic-year/{academicYearId}
  ```

### Records disciplinaires
- **Tous les records disciplinaires d'un élève pour une année académique:**
  ```
  GET /api/discipline-records/student/{studentId}/academic-year/{academicYearId}
  ```

- **Tous les records disciplinaires d'une année académique (tous les élèves):**
  ```
  GET /api/discipline-records/academic-year/{academicYearId}
  ```

---

## 7. ENDPOINTS UTILITAIRES

**Récupérer toutes les notes:**
```
GET /api/grades
```

**Récupérer une note par ID:**
```
GET /api/grades/{id}
```

**Mettre à jour une note:**
```
PUT /api/grades/{id}
```

**Supprimer une note:**
```
DELETE /api/grades/{id}
```

**Récupérer toutes les compétences:**
```
GET /api/competences
```

**Récupérer une compétence par ID:**
```
GET /api/competences/{id}
```

**Mettre à jour une compétence:**
```
PUT /api/competences/{id}
```

**Supprimer une compétence:**
```
DELETE /api/competences/{id}
```

---

## NOTES IMPORTANTES

1. **Remplacez les UUIDs** par des valeurs réelles de votre base de données
2. **Pour obtenir les UUIDs réels**, utilisez ces endpoints :
   - `GET /api/schools` - Liste des écoles
   - `GET /api/academic-years` - Liste des années académiques
   - `GET /api/terms` - Liste des trimestres
   - `GET /api/sequences` - Liste des séquences
   - `GET /api/subjects` - Liste des matières
   - `GET /api/users` - Liste des utilisateurs (élèves, enseignants)

3. **Format des dates** : `YYYY-MM-DD` (ISO 8601)

4. **Les notes sont en BigDecimal** : utilisez des nombres décimaux (ex: `15.5`, `16.0`)

5. **Les coefficients** sont généralement des entiers ou des décimaux simples (ex: `1.0`, `2.0`, `3.0`, `4.0`)

6. **Tous les GET par année académique** permettent de récupérer toutes les données d'un élève ou de tous les élèves pour une année académique complète

