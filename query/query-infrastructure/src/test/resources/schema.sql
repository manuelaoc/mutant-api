DROP TABLE IF EXISTS mutant;
CREATE TABLE mutant
(
    dna       VARCHAR(300) NOT NULL,
    is_mutant INTEGER(1)   NOT NULL,
    PRIMARY KEY (dna)
);