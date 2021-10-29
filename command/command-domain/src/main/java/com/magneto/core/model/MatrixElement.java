package com.magneto.core.model;

public class MatrixElement {

    private String value;
    private boolean hasLeftHorizontal;
    private boolean hasRightHorizontal;
    private boolean hasUpVertical;
    private boolean hasDownVertical;
    private boolean hasLowerRightDiagonal;
    private boolean hasLowerLeftDiagonal;
    private boolean hasUpperRightDiagonal;
    private boolean hasUpperLeftDiagonal;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isHasLeftHorizontal() {
        return hasLeftHorizontal;
    }

    public void setHasLeftHorizontal(boolean hasLeftHorizontal) {
        this.hasLeftHorizontal = hasLeftHorizontal;
    }

    public boolean isHasRightHorizontal() {
        return hasRightHorizontal;
    }

    public void setHasRightHorizontal(boolean hasRightHorizontal) {
        this.hasRightHorizontal = hasRightHorizontal;
    }

    public boolean isHasUpVertical() {
        return hasUpVertical;
    }

    public void setHasUpVertical(boolean hasUpVertical) {
        this.hasUpVertical = hasUpVertical;
    }

    public boolean isHasDownVertical() {
        return hasDownVertical;
    }

    public void setHasDownVertical(boolean hasDownVertical) {
        this.hasDownVertical = hasDownVertical;
    }

    public boolean isHasLowerRightDiagonal() {
        return hasLowerRightDiagonal;
    }

    public void setHasLowerRightDiagonal(boolean hasLowerRightDiagonal) {
        this.hasLowerRightDiagonal = hasLowerRightDiagonal;
    }

    public boolean isHasLowerLeftDiagonal() {
        return hasLowerLeftDiagonal;
    }

    public void setHasLowerLeftDiagonal(boolean hasLowerLeftDiagonal) {
        this.hasLowerLeftDiagonal = hasLowerLeftDiagonal;
    }

    public boolean isHasUpperRightDiagonal() {
        return hasUpperRightDiagonal;
    }

    public void setHasUpperRightDiagonal(boolean hasUpperRightDiagonal) {
        this.hasUpperRightDiagonal = hasUpperRightDiagonal;
    }

    public boolean isHasUpperLeftDiagonal() {
        return hasUpperLeftDiagonal;
    }

    public void setHasUpperLeftDiagonal(boolean hasUpperLeftDiagonal) {
        this.hasUpperLeftDiagonal = hasUpperLeftDiagonal;
    }
}
