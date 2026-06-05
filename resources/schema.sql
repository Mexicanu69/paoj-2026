DROP TABLE IF EXISTS Imprumuturi;
DROP TABLE IF EXISTS Carti;
DROP TABLE IF EXISTS Clienti;
DROP TABLE IF EXISTS Companii;
DROP TABLE IF EXISTS Autori;

CREATE TABLE Companii (
    nume TEXT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Autori (
    email TEXT PRIMARY KEY,
    nume TEXT NOT NULL,
    biografie TEXT
);

CREATE TABLE Clienti (
    email TEXT PRIMARY KEY,
    nume TEXT NOT NULL,
    nume_companie TEXT,
    FOREIGN KEY (nume_companie) REFERENCES Companii(nume) ON DELETE SET NULL
);

CREATE TABLE Carti (
    titlu TEXT PRIMARY KEY,
    nume_autor TEXT NOT NULL,
    email_autor TEXT,
    biografie_autor TEXT,
    isbn_complet TEXT NOT NULL,
    categorie TEXT,
    exemplare_disponibile INTEGER NOT NULL
);

CREATE TABLE Imprumuturi (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email_client TEXT NOT NULL,
    titlu_carte TEXT NOT NULL,
    data_imprumut TEXT NOT NULL,
    este_returnat INTEGER DEFAULT 0,
    FOREIGN KEY (email_client) REFERENCES Clienti(email) ON DELETE CASCADE,
    FOREIGN KEY (titlu_carte) REFERENCES Carti(titlu) ON DELETE CASCADE
);