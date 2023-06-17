package factory;

import daopattern.IRepository;
import daopattern.OrderRepository;
import enums.RepositoryType;

public class RepositoryFactory {
    public static IRepository createRepositoryInstance(RepositoryType type) {
        if (type == RepositoryType.TABLE) {
            return OrderRepository.getInstance();
        } else return null;
    }
}
