package com.magneto.core.factory;

import com.magneto.core.command.MutantCommand;
import com.magneto.core.model.Mutant;
import org.springframework.stereotype.Component;

@Component
public class MutantFactory {

    public Mutant create(MutantCommand mutantCommand) {
        return new Mutant(mutantCommand.getDna());
    }
}
