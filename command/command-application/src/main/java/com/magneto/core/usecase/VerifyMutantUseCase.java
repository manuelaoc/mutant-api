package com.magneto.core.usecase;

import com.magneto.core.command.MutantCommand;
import com.magneto.core.exception.NoMutantException;
import com.magneto.core.factory.MutantFactory;
import com.magneto.core.service.VerifyMutantService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(noRollbackFor = {NoMutantException.class})
public class VerifyMutantUseCase {
    private final MutantFactory mutantFactory;
    private final VerifyMutantService verifyMutantService;

    public VerifyMutantUseCase(MutantFactory mutantFactory, VerifyMutantService verifyMutantService) {
        this.mutantFactory = mutantFactory;
        this.verifyMutantService = verifyMutantService;
    }

    public Boolean execute(MutantCommand mutantCommand) {
        return verifyMutantService.execute(this.mutantFactory.create(mutantCommand));
    }
}
