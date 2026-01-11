# API Report Card (Bulletin Scolaire) - Routes et Payloads de Test

## Base URL
```
http://localhost:8080/api
```

---

## 1. GÉNÉRER LE BULLETIN SCOLAIRE D'UN ÉLÈVE POUR UN TRIMESTRE

**Endpoint:** `GET /api/report-cards/student/{studentId}/class/{classRoomId}/academic-year/{academicYearId}/term/{termId}`

**Description:** 
- Assemble toutes les informations nécessaires pour générer un bulletin scolaire PDF
- Inclut : infos élève, notes, statistiques, record disciplinaire, absences
- Le frontend pourra utiliser ces données pour générer un PDF

**Exemple:**
```
GET /api/report-cards/student/abc12345-e67b-89d0-a123-4567890def01/class/456e7890-e12b-34d5-a678-901234567890/academic-year/mno34567-e89b-01c2-a345-678901234fgh/term/pqr45678-e90b-12c3-a456-789012345hij
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Bulletin scolaire généré avec succès",
  "data": {
    "studentId": "abc12345-e67b-89d0-a123-4567890def01",
    "studentName": "Jean DUPONT",
    "studentUniqueIdentifier": "STU-2025-001",
    "studentBirthDate": "2010-05-15",
    "studentBirthPlace": "Douala, Cameroun",
    "studentGender": "M",
    "studentIsRepeating": false,
    "studentPhotoUrl": "https://example.com/photos/student-001.jpg",
    "parentNames": "Marie DUPONT",
    "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
    "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
    "classRoomLabel": "6ème A",
    "schoolId": "123e4567-e89b-12d3-a456-426614174000",
    "schoolName": "École Primaire Centrale",
    "schoolAddress": "123 Rue Principale, Douala",
    "schoolPhoneNumber": "+237 233 XXX XXX",
    "schoolEmail": "contact@ecole-centrale.cm",
    "academicYearId": "mno34567-e89b-01c2-a345-678901234fgh",
    "academicYearLabel": "2025-09-01 - 2026-06-30",
    "termId": "pqr45678-e90b-12c3-a456-789012345hij",
    "termName": "Trimestre 1",
    "sequenceId": null,
    "sequenceName": null,
    "grades": [
      {
        "id": "stu78901-e23b-45c6-a789-012345678def",
        "competenceId": "ghi78901-e23b-45c6-a789-012345678def",
        "competenceDescription": "Use appropriate language resources to listen, speak, read and write about national integration",
        "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
        "subjectName": "Mathématiques",
        "studentId": "abc12345-e67b-89d0-a123-4567890def01",
        "studentName": "Jean DUPONT",
        "termId": "pqr45678-e90b-12c3-a456-789012345hij",
        "termName": "Trimestre 1",
        "sequenceId": null,
        "sequenceName": null,
        "noteN20": 15.5,
        "noteM20": 16.0,
        "coefficient": 3.0,
        "mXCoef": 48.0,
        "cote": 16.0,
        "minScore": 8.0,
        "maxScore": 19.5,
        "appreciation": "Bon travail, continuez ainsi.",
        "teacherId": "def45678-e90b-12c3-a456-789012345abc",
        "teacherName": "Marie MARTIN"
      }
    ],
    "averageScore": 15.75,
    "totalCoefficient": 15.0,
    "totalGrades": 5,
    "disciplineRecord": {
      "id": "xyz78901-e23b-45c6-a789-012345678abc",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "termId": "pqr45678-e90b-12c3-a456-789012345hij",
      "termName": "Trimestre 1",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "unjustifiedAbsencesHours": 5,
      "justifiedAbsencesHours": 2,
      "lateCount": 3,
      "detentionHours": 2,
      "conductWarning": false,
      "conductBlame": false,
      "exclusionDays": 0,
      "permanentExclusion": false
    },
    "totalAbsenceHours": 7.0,
    "generatedDate": "2025-12-19"
  }
}
```

---

## 2. GÉNÉRER LE BULLETIN SCOLAIRE D'UN ÉLÈVE POUR UNE SÉQUENCE

**Endpoint:** `GET /api/report-cards/student/{studentId}/class/{classRoomId}/academic-year/{academicYearId}/sequence/{sequenceId}`

**Description:** 
- Assemble toutes les informations nécessaires pour générer un bulletin scolaire PDF pour une séquence
- Inclut : infos élève, notes de la séquence, statistiques, record disciplinaire du trimestre associé, absences
- Le frontend pourra utiliser ces données pour générer un PDF

**Exemple:**
```
GET /api/report-cards/student/abc12345-e67b-89d0-a123-4567890def01/class/456e7890-e12b-34d5-a678-901234567890/academic-year/mno34567-e89b-01c2-a345-678901234fgh/sequence/vwx78901-e23b-45c6-a789-012345678ghi
```

**Réponse (200 OK):**
```json
{
  "status": 200,
  "message": "Bulletin scolaire généré avec succès",
  "data": {
    "studentId": "abc12345-e67b-89d0-a123-4567890def01",
    "studentName": "Jean DUPONT",
    "studentUniqueIdentifier": "STU-2025-001",
    "studentBirthDate": "2010-05-15",
    "studentBirthPlace": "Douala, Cameroun",
    "studentGender": "M",
    "studentIsRepeating": false,
    "studentPhotoUrl": "https://example.com/photos/student-001.jpg",
    "parentNames": "Marie DUPONT",
    "parentContacts": "Tel: +237 6XX XXX XXX, Email: dupont@example.com",
    "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
    "classRoomLabel": "6ème A",
    "schoolId": "123e4567-e89b-12d3-a456-426614174000",
    "schoolName": "École Primaire Centrale",
    "schoolAddress": "123 Rue Principale, Douala",
    "schoolPhoneNumber": "+237 233 XXX XXX",
    "schoolEmail": "contact@ecole-centrale.cm",
    "academicYearId": "mno34567-e89b-01c2-a345-678901234fgh",
    "academicYearLabel": "2025-09-01 - 2026-06-30",
    "termId": "pqr45678-e90b-12c3-a456-789012345hij",
    "termName": "Trimestre 1",
    "sequenceId": "vwx78901-e23b-45c6-a789-012345678ghi",
    "sequenceName": "Séquence 1",
    "grades": [
      {
        "id": "stu78901-e23b-45c6-a789-012345678def",
        "competenceId": "ghi78901-e23b-45c6-a789-012345678def",
        "competenceDescription": "Use appropriate language resources to listen, speak, read and write about national integration",
        "subjectId": "789e0123-e45b-67d8-a901-234567890abc",
        "subjectName": "Mathématiques",
        "studentId": "abc12345-e67b-89d0-a123-4567890def01",
        "studentName": "Jean DUPONT",
        "termId": "pqr45678-e90b-12c3-a456-789012345hij",
        "termName": "Trimestre 1",
        "sequenceId": "vwx78901-e23b-45c6-a789-012345678ghi",
        "sequenceName": "Séquence 1",
        "noteN20": 15.5,
        "noteM20": 16.0,
        "coefficient": 3.0,
        "mXCoef": 48.0,
        "cote": 16.0,
        "minScore": 8.0,
        "maxScore": 19.5,
        "appreciation": "Bon travail, continuez ainsi.",
        "teacherId": "def45678-e90b-12c3-a456-789012345abc",
        "teacherName": "Marie MARTIN"
      }
    ],
    "averageScore": 15.75,
    "totalCoefficient": 12.0,
    "totalGrades": 4,
    "disciplineRecord": {
      "id": "xyz78901-e23b-45c6-a789-012345678abc",
      "studentId": "abc12345-e67b-89d0-a123-4567890def01",
      "studentName": "Jean DUPONT",
      "termId": "pqr45678-e90b-12c3-a456-789012345hij",
      "termName": "Trimestre 1",
      "classRoomId": "456e7890-e12b-34d5-a678-901234567890",
      "classRoomLabel": "6ème A",
      "unjustifiedAbsencesHours": 5,
      "justifiedAbsencesHours": 2,
      "lateCount": 3,
      "detentionHours": 2,
      "conductWarning": false,
      "conductBlame": false,
      "exclusionDays": 0,
      "permanentExclusion": false
    },
    "totalAbsenceHours": 3.5,
    "generatedDate": "2025-12-19"
  }
}
```

---

## INFORMATIONS RETOURNÉES DANS LE BULLETIN

### Informations de l'élève
- ID, nom, identifiant unique
- Date et lieu de naissance
- Genre, statut (redoublant ou non)
- Photo URL
- Informations parentales (noms et contacts)

### Informations de la classe et école
- ID et label de la classe
- ID, nom, adresse, téléphone, email de l'école
- Année académique (ID et label)

### Période (trimestre ou séquence)
- Pour un trimestre : `termId`, `termName`, `sequenceId` = null
- Pour une séquence : `sequenceId`, `sequenceName`, `termId` et `termName` (du trimestre associé)

### Notes
- Liste complète des notes (GradeDto)
- Statistiques :
  - `averageScore` : Moyenne générale pondérée
  - `totalCoefficient` : Total des coefficients
  - `totalGrades` : Nombre de notes

### Record disciplinaire
- Record disciplinaire du trimestre (même pour une séquence)
- Absences justifiées/non justifiées, retards, heures de retenue, etc.

### Absences
- `totalAbsenceHours` : Total d'heures d'absence pour la période
  - Pour un trimestre : absences du trimestre
  - Pour une séquence : absences de la séquence

### Date de génération
- `generatedDate` : Date à laquelle le bulletin a été généré

---

## NOTES IMPORTANTES

### Calcul de la moyenne
- La moyenne est calculée selon la formule : **Σ(noteM20 × coefficient) / Σ(coefficient)**
- Arrondie à 2 décimales (mode HALF_UP)

### Record disciplinaire
- Pour un trimestre : record disciplinaire du trimestre
- Pour une séquence : record disciplinaire du trimestre associé à la séquence (les records sont par trimestre, pas par séquence)

### Absences
- Actuellement, le total d'heures d'absence est à 0.0 (TODO: implémenter le calcul)
- Pour l'implémenter, utiliser l'API des absences avec les dates de début/fin de la période

### Vérifications
- L'endpoint vérifie que l'élève est bien inscrit dans la classe pour l'année académique donnée
- Si l'inscription n'existe pas, une erreur 404 est retournée

---

## ENDPOINTS UTILITAIRES POUR OBTENIR LES UUIDs

Pour tester, vous aurez besoin des UUIDs réels. Utilisez ces endpoints :

- `GET /api/users` - Liste des utilisateurs (élèves)
- `GET /api/class-rooms` - Liste des classes
- `GET /api/academic-years` - Liste des années académiques
- `GET /api/terms` - Liste des trimestres
- `GET /api/sequences` - Liste des séquences

---

## RÉSUMÉ DES ENDPOINTS

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/report-cards/student/{studentId}/class/{classRoomId}/academic-year/{academicYearId}/term/{termId}` | Bulletin pour un trimestre |
| GET | `/api/report-cards/student/{studentId}/class/{classRoomId}/academic-year/{academicYearId}/sequence/{sequenceId}` | Bulletin pour une séquence |
