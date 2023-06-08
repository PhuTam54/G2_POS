package factory;

import daopattern.IRepository;
import daopattern.TableRepository;
import enums.RepositoryType;

public class RepositoryFactory {
    public static IRepository createRepositoryInstance(RepositoryType type) {
        if (type == RepositoryType.TABLE) {
            return TableRepository.getInstance();
        } else return null;
    }
}
