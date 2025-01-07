package mctg.persistence.repository;

import mctg.persistence.UnitOfWork;

public class BattlesRepositoryImpl implements BattlesRepository {
    private UnitOfWork unitOfWork;

    public BattlesRepositoryImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
}
