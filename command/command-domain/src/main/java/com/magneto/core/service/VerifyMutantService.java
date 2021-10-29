package com.magneto.core.service;

import com.magneto.core.exception.NoMutantException;
import com.magneto.core.model.MatrixElement;
import com.magneto.core.model.Mutant;
import com.magneto.core.repository.MutantRepository;

public class VerifyMutantService {

    private final MutantRepository mutantRepository;

    private static final int NUMBER_CHARACTERS_COMPLETE_SEQUENCE = 3;

    public VerifyMutantService(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    public Boolean execute(Mutant mutant) {
        // logic
        MatrixElement[][] matrix = this.generateMatrix(mutant.getDna());
        this.mutantRepository.save(mutant.getDna());
        if (mutant.getIsMutant()) {
            return true;
        } else {
            throw new NoMutantException("El ADN enviado no pertene a un mutante");
        }
    }

    /**
     * Se genera la matriz cuadrada (NxN) en base al arreglo del ADN enviado.
     *
     * @param dna array con las secuencias de adn enviadas
     * @return matriz generada
     */
    private MatrixElement[][] generateMatrix(String[] dna) {
        int lengthArrayDna = dna.length;
        MatrixElement[][] matrixDna = new MatrixElement[lengthArrayDna][lengthArrayDna];
        for (int row = 0; row < lengthArrayDna; row++) {
            String[] rowDna = dna[row].split("");
            for (int column = 0; column < lengthArrayDna; column++) {
                matrixDna[row][column] = getPossibleDirectionCurrentPosition(row, column, lengthArrayDna, rowDna[column]);
            }
        }
        return matrixDna;
    }


    /**
     * Se toma cada una de las posiciones de la matriz (fila,columna) y con base a esos datos y al tamaño
     * del arreglo del adn enviado, determina las posibles direcciones de esa posición actual
     *
     * @param row fila de la matriz
     * @param column columna de la matriz
     * @param lengthArray tamaño del arreglo
     * @param value valor de cada uno de los caracteres de las secuencias del ADN enviado
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


}