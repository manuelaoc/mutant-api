package com.magneto.core.service;

import com.magneto.core.exception.MutantException;
import com.magneto.core.exception.NoMutantException;
import com.magneto.core.model.MatrixElement;
import com.magneto.core.model.Mutant;
import com.magneto.core.repository.MutantRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerifyMutantService {

    private static final int NUMBER_CHARACTERS_COMPLETE_SEQUENCE = 3;

    private final MutantRepository mutantRepository;

    private int validDnaSequenceCounter;
    private MatrixElement[][] matrixDna;
    private List<String> listPositionsNotReEvaluate;

    public VerifyMutantService(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    public Boolean execute(Mutant mutant) {
        listPositionsNotReEvaluate = new ArrayList<>();
        validDnaSequenceCounter = 0;
        this.validateExistDna(mutant.getDna());
        this.generateMatrix(mutant.getDna());
        mutant.setIsMutant(this.analyzeDnaMatrix());
        this.mutantRepository.save(mutant);
        if (mutant.isMutant()) {
            return true;
        } else {
            throw new NoMutantException("El ADN enviado no pertene a un mutante");
        }
    }

    private void validateExistDna(String[] dna) {
        if (this.mutantRepository.existDna(dna)) {
            throw new MutantException("El ADN enviado ya fue verificado previamente");
        }
    }

    /**
     * Se genera la matriz cuadrada (NxN) en base al arreglo del ADN enviado.
     *
     * @param dna array con las secuencias de adn enviadas
     */
    private void generateMatrix(String[] dna) {
        int lengthArrayDna = dna.length;
        matrixDna = new MatrixElement[lengthArrayDna][lengthArrayDna];
        for (int row = 0; row < lengthArrayDna; row++) {
            String[] rowDna = dna[row].split("");
            for (int column = 0; column < lengthArrayDna; column++) {
                matrixDna[row][column] = getPossibleDirectionCurrentPosition(row, column, lengthArrayDna, rowDna[column]);
            }
        }
    }


    /**
     * Se toma cada una de las posiciones de la matriz (fila,columna) y con base a esos datos y al tamaño
     * del arreglo del adn enviado, determina las posibles direcciones de esa posición actual
     *
     * @param row         fila de la matriz
     * @param column      columna de la matriz
     * @param lengthArray tamaño del arreglo
     * @param value       valor de cada uno de los caracteres de las secuencias del ADN enviado
     * @return elemento de la matriz con las propiedades referentes a las posibles direcciones
     */
    private MatrixElement getPossibleDirectionCurrentPosition(int row, int column, int lengthArray, String value) {
        MatrixElement matrixElement = new MatrixElement();
        matrixElement.setValue(value);
        if (row - NUMBER_CHARACTERS_COMPLETE_SEQUENCE >= 0) matrixElement.setHasLeftHorizontal(true);
        if (row + NUMBER_CHARACTERS_COMPLETE_SEQUENCE < lengthArray) matrixElement.setHasRightHorizontal(true);
        if (column - NUMBER_CHARACTERS_COMPLETE_SEQUENCE >= 0) matrixElement.setHasUpVertical(true);
        if (column + NUMBER_CHARACTERS_COMPLETE_SEQUENCE < lengthArray) matrixElement.setHasDownVertical(true);
        if (row + NUMBER_CHARACTERS_COMPLETE_SEQUENCE < lengthArray && column + NUMBER_CHARACTERS_COMPLETE_SEQUENCE < lengthArray)
            matrixElement.setHasLowerRightDiagonal(true);
        if (row - NUMBER_CHARACTERS_COMPLETE_SEQUENCE >= 0 && column + NUMBER_CHARACTERS_COMPLETE_SEQUENCE < lengthArray)
            matrixElement.setHasLowerLeftDiagonal(true);
        if (row + NUMBER_CHARACTERS_COMPLETE_SEQUENCE < lengthArray && column - NUMBER_CHARACTERS_COMPLETE_SEQUENCE >= 0)
            matrixElement.setHasUpperRightDiagonal(true);
        if (row - NUMBER_CHARACTERS_COMPLETE_SEQUENCE >= 0 && column - NUMBER_CHARACTERS_COMPLETE_SEQUENCE >= 0)
            matrixElement.setHasUpperLeftDiagonal(true);
        return matrixElement;
    }


    private boolean analyzeDnaMatrix() {
        int matrixLength = matrixDna.length;
        for (int row = 0; row < matrixLength; row++) {
            for (int column = 0; column < matrixLength; column++) {
                if (validDnaSequenceCounter > 1) {
                    return true;
                }
                analyzeMatrixElement(matrixDna[row][column], row, column);
            }
        }
        return false;
    }

    private void analyzeMatrixElement(MatrixElement matrixElement, int row, int column) {
        if (matrixElement.isHasLeftHorizontal()) analyzeLeftHorizontal(row, column);
        if (matrixElement.isHasRightHorizontal()) analyzeRightHorizontal(row, column);
        if (matrixElement.isHasUpVertical()) analyzeUpVertical(row, column);
        if (matrixElement.isHasDownVertical()) analyzeDownVertical(row, column);
        if (matrixElement.isHasLowerRightDiagonal()) analyzeLowerRightDiagonal(row, column);
        if (matrixElement.isHasLowerLeftDiagonal()) analyzeLowerLeftDiagonal(row, column);
        if (matrixElement.isHasUpperRightDiagonal()) analyzeUpperRightDiagonal(row, column);
        if (matrixElement.isHasUpperLeftDiagonal()) analyzeUpperLeftDiagonal(row, column);
    }

    private void analyzeLeftHorizontal(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row - 1][column].getValue() + matrixDna[row - 2][column].getValue() + matrixDna[row - 3][column].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, (row - 1) + "" + column, (row - 2) + "" + column, (row - 3) + "" + column);
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeRightHorizontal(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row + 1][column].getValue() + matrixDna[row + 2][column].getValue() + matrixDna[row + 3][column].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, (row + 1) + "" + column, (row + 2) + "" + column, (row + 3) + "" + column);
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeUpVertical(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row][column - 1].getValue() + matrixDna[row][column - 2].getValue() + matrixDna[row][column - 3].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, row + "" + (column - 1), row + "" + (column - 2), row + "" + (column - 3));
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeDownVertical(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row][column + 1].getValue() + matrixDna[row][column + 2].getValue() + matrixDna[row][column + 3].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, row + "" + (column + 1), row + "" + (column + 2), row + "" + (column + 3));
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeLowerRightDiagonal(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row + 1][column + 1].getValue() + matrixDna[row + 2][column + 2].getValue() + matrixDna[row + 3][column + 3].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, (row + 1) + "" + (column + 1), (row + 2) + "" + (column + 2), (row + 3) + "" + (column + 3));
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeLowerLeftDiagonal(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row - 1][column + 1].getValue() + matrixDna[row - 2][column + 2].getValue() + matrixDna[row - 3][column + 3].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, (row - 1) + "" + (column + 1), (row - 2) + "" + (column + 2), (row - 3) + "" + (column + 3));
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeUpperRightDiagonal(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row + 1][column - 1].getValue() + matrixDna[row + 2][column - 2].getValue() + matrixDna[row + 3][column - 3].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, (row + 1) + "" + (column - 1), (row + 2) + "" + (column - 2), (row + 3) + "" + (column - 3));
        this.processSequence(sequence, evaluatedPositions);
    }

    private void analyzeUpperLeftDiagonal(int row, int column) {
        String sequence = matrixDna[row][column].getValue() + matrixDna[row - 1][column - 1].getValue() + matrixDna[row - 2][column - 2].getValue() + matrixDna[row - 3][column - 3].getValue();
        List<String> evaluatedPositions = List.of(row + "" + column, (row - 1) + "" + (column - 1), (row - 2) + "" + (column - 2), (row - 3) + "" + (column - 3));
        this.processSequence(sequence, evaluatedPositions);
    }

    private void processSequence(String sequence, List<String> listPositionsAnalyzed) {
        if (!listPositionsNotReEvaluate.contains(listPositionsAnalyzed.toString())) {
            this.itsMutantDna(sequence);
            this.addPositionsEvaluated(listPositionsAnalyzed);
        }
    }

    private void itsMutantDna(String sequence) {
        if (sequence.matches("([A-C-G-T])\\1{3}")) {
            validDnaSequenceCounter++;
        }
    }

    private void addPositionsEvaluated(List<String> listPositionsAnalyzed) {
        listPositionsNotReEvaluate.add(listPositionsAnalyzed.toString());
        List<String> listReverse = new ArrayList<>(listPositionsAnalyzed);
        Collections.reverse(listReverse);
        listPositionsNotReEvaluate.add(listReverse.toString());
    }
}