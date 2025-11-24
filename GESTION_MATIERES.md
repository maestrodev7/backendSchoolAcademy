# Gestion des Matières avec Coefficients par Classe

## Vue d'ensemble

Ce système permet de gérer les matières (subjects) et de les associer à des classes avec un **coefficient spécifique pour chaque classe**. Cela signifie qu'une même matière peut avoir des coefficients différents selon la classe.

## Architecture

### Structure des entités

1. **Subject (Matière)** : Entité principale représentant une matière
   - `id` : Identifiant unique
   - `code` : Code unique de la matière (ex: "MATH", "FR")
   - `name` : Nom de la matière (ex: "Mathématiques", "Français")
   - `description` : Description optionnelle

2. **ClassRoomSubject** : Table de jointure entre Subject et ClassRoom
   - `id` : Identifiant unique
   - `subject` : Référence à la matière
   - `classRoom` : Référence à la classe
   - `coefficient` : **Coefficient de la matière pour cette classe spécifique**

### Exemple concret

- **Matière** : Mathématiques (code: "MATH")
- **Classe A** : Terminale S → Coefficient : 7
- **Classe B** : Terminale ES → Coefficient : 5
- **Classe C** : Première L → Coefficient : 3

La même matière "Mathématiques" a donc des coefficients différents selon la classe.

## API Endpoints

### 1. Créer une matière

**POST** `/api/subjects`

```json
{
  "code": "MATH",
  "name": "Mathématiques",
  "description": "Cours de mathématiques"
}
```

**Réponse :**
```json
{
  "status": 200,
  "message": "Matière créée avec succès",
  "data": {
    "id": "uuid",
    "code": "MATH",
    "name": "Mathématiques",
    "description": "Cours de mathématiques",
    "classRoomSubjects": []
  }
}
```

### 2. Associer une matière à une classe avec un coefficient

**POST** `/api/subjects/associate`

```json
{
  "subjectId": "uuid-de-la-matiere",
  "classRoomId": "uuid-de-la-classe",
  "coefficient": 7.0
}
```

**Réponse :**
```json
{
  "status": 200,
  "message": "Matière associée à la classe avec succès",
  "data": {
    "id": "uuid",
    "code": "MATH",
    "name": "Mathématiques",
    "classRoomSubjects": [
      {
        "id": "uuid",
        "subjectId": "uuid",
        "subjectName": "Mathématiques",
        "subjectCode": "MATH",
        "classRoomId": "uuid",
        "classRoomLabel": "Terminale S",
        "coefficient": 7.0
      }
    ]
  }
}
```

**Note importante :** Si l'association existe déjà, le coefficient sera **mis à jour** au lieu de créer un doublon.

### 3. Récupérer toutes les matières

**GET** `/api/subjects`

### 4. Récupérer une matière par ID

**GET** `/api/subjects/{id}`

### 5. Récupérer les matières d'une classe

**GET** `/api/subjects/classroom/{classroomId}`

Retourne toutes les matières associées à une classe avec leurs coefficients.

### 6. Mettre à jour une matière

**PUT** `/api/subjects/{id}`

```json
{
  "code": "MATH",
  "name": "Mathématiques Avancées",
  "description": "Description mise à jour"
}
```

### 7. Supprimer une matière

**DELETE** `/api/subjects/{id}`

⚠️ **Attention :** La suppression d'une matière supprime également toutes ses associations avec les classes.

## Flux d'utilisation typique

### Scénario : Créer une matière et l'associer à plusieurs classes

1. **Créer la matière**
   ```bash
   POST /api/subjects
   {
     "code": "PHYS",
     "name": "Physique",
     "description": "Cours de physique"
   }
   ```

2. **Associer à la classe Terminale S avec coefficient 6**
   ```bash
   POST /api/subjects/associate
   {
     "subjectId": "uuid-physique",
     "classRoomId": "uuid-terminale-s",
     "coefficient": 6.0
   }
   ```

3. **Associer à la classe Terminale ES avec coefficient 4**
   ```bash
   POST /api/subjects/associate
   {
     "subjectId": "uuid-physique",
     "classRoomId": "uuid-terminale-es",
     "coefficient": 4.0
   }
   ```

4. **Récupérer toutes les matières de la Terminale S**
   ```bash
   GET /api/subjects/classroom/uuid-terminale-s
   ```

## Modèle de données

### Table `subjects`
- `id` (UUID, PK)
- `code` (VARCHAR, UNIQUE, NOT NULL)
- `name` (VARCHAR, NOT NULL)
- `description` (TEXT)

### Table `class_room_subjects` (table de jointure)
- `id` (UUID, PK)
- `subject_id` (UUID, FK → subjects.id)
- `class_room_id` (UUID, FK → class_rooms.id)
- `coefficient` (DOUBLE, NOT NULL)
- **Contrainte unique** : (subject_id, class_room_id) - Une matière ne peut être associée qu'une seule fois à une classe

## Points importants

1. **Coefficient dépendant de la classe** : Le coefficient est stocké dans la table de jointure, permettant à une même matière d'avoir des coefficients différents selon la classe.

2. **Mise à jour automatique** : Si vous associez une matière à une classe qui est déjà associée, le coefficient sera mis à jour au lieu de créer un doublon.

3. **Validation** : 
   - Le code de la matière doit être unique
   - Le coefficient doit être positif
   - La matière et la classe doivent exister avant l'association

4. **Cascade** : La suppression d'une matière supprime automatiquement toutes ses associations avec les classes.

## Exemple de cas d'usage

**Système de notation avec coefficients variables :**

```
Matière : Français
├── Terminale L → Coefficient : 4
├── Terminale ES → Coefficient : 3
└── Terminale S → Coefficient : 2

Matière : Mathématiques
├── Terminale L → Coefficient : 2
├── Terminale ES → Coefficient : 5
└── Terminale S → Coefficient : 7
```

Cela permet de calculer des moyennes pondérées différentes selon la filière, tout en gardant une seule entité "Matière" dans le système.

