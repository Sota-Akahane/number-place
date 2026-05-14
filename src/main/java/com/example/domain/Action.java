package com.example.domain;

/**
 * 次の一手を表す.
 */
public sealed interface Action permits PlaceAction, RemoveCandidateAction {
    Cell cell();
    int number();
}